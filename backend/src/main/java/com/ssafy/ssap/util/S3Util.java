package com.ssafy.ssap.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.ssafy.ssap.controller.RoomController;
import com.ssafy.ssap.domain.alarm.Alarm;
import com.ssafy.ssap.domain.qna.Answer;
import com.ssafy.ssap.domain.qna.ArticleImage;
import com.ssafy.ssap.domain.qna.Question;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.exception.S3Exception;
import com.ssafy.ssap.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


@RequiredArgsConstructor
@Service
public class S3Util {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final AmazonS3Client amazonS3;
    private final ArticleImageRepository articleImageRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final AlarmRepository alarmRepository;
    private final QuestionRepository questionRepository;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> createFileName(List<MultipartFile> files, String pattern, Integer id) {
        //alarm -> alarmId -> [al1], [al2], [al3], ...
        //profile -> userId -> [pr1], [pr2], [pr3], ...
        //room -> userId -> ro1, ro2, ro3, ...
        //question -> questionId -> [qu1_1, qu1_2, ...], [qu2_1, qu2_2, ...]
        List<String> resultString = new ArrayList<>(files.size());
        ListIterator<MultipartFile> li = files.listIterator();
        StringBuilder sb;
        String prefix;
        String currentFileName;
        int counter = 1;
        while (li.hasNext()) {
            MultipartFile file = li.next();
            prefix = pattern.substring(0, 2); // "al" / "pr" / "ro" ...
            logger.debug("prefix:"+prefix);
            currentFileName = file.getOriginalFilename(); // "filename.jpg"
            if (currentFileName == null) throw new NullPointerException("file Original Name 불러오기 실패");
            String fileExtension = currentFileName.substring(currentFileName.lastIndexOf('.')); //".jpg"
            if (fileExtension.length() > 4) throw new S3Exception("File Extension Wrong");

            sb = new StringBuilder();
            sb.append("image/");
            sb.append(pattern);
            sb.append("/");
            switch (prefix) {
                case "al", "pr", "ro" -> {
                    sb.append(prefix);
                    sb.append(id)
;                }
                case "qu", "an" -> {
                    sb.append(prefix);
                    sb.append(id);
                    sb.append("_");
                    sb.append(counter++);
                }
                default -> throw new S3Exception("unoccurable ERROR. Just for double check");
            }
            sb.append(fileExtension);

            resultString.add(sb.toString());
        }
        return resultString;
    }

    @Transactional
    public void uploadSingleFileToS3(MultipartFile file, String fileName) throws IOException {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            PutObjectResult pr = amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
            logger.debug("S3 업로드 완료" + pr.getETag() + "/" + pr.getVersionId());
        }catch(AmazonClientException | IOException e){
            logger.error("S3 업로드 중 문제 발생"+e);
            throw e;
        }
    }

    @Transactional
    public void updateDatabase(Object entity, List<String> fileNameList) {
        String basePath = "https://ssapbucket.s3.ap-northeast-2.amazonaws.com/";

        JpaRepository<?, Integer> repository = getRepository(entity);
        List<ArticleImage> articleImagesToUpdate = new ArrayList<>();

        for (String fileName : fileNameList) {
            String filePath = basePath + fileName;

            //다중 업로드인 경우(question, answer)
            if (entity instanceof Question || entity instanceof Answer) {
                ArticleImage articleImage = new ArticleImage();
                if (entity instanceof Question) {
                    articleImage.setQuestion((Question) entity);
                } else{
                    articleImage.setAnswer((Answer) entity);
                }
                articleImage.setImagePath(filePath);
                articleImagesToUpdate.add(articleImage);
            } else { //단일파일 업로드인 경우(profile, room, alarm)
                try {
                    String name;
                    String tmp = fileNameList.get(0);
                    name = tmp.contains("pr") ? "profileImagePath": tmp.contains("ro") ? "roomImagePath" : "imagePath";
                    //question, answer이 아닌 경우(user(profile, room), alarm) 이미 객체가 있으니 객체의 imagePath 속성을 업데이트한다.
                    logger.debug("S3업로드 디버깅중"+tmp+"/"+entity+"/"+name+"/"+filePath);
                    BeanUtils.setProperty(entity, name, filePath);
                    repository.flush();
                } catch (IllegalAccessException | InvocationTargetException | NullPointerException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // Bulk update all article images if applicable
        if (!articleImagesToUpdate.isEmpty()) {
            articleImageRepository.saveAll(articleImagesToUpdate);
        }
    }

    public Object checkPresentAndGetEntity(String pattern, Integer id) {
        JpaRepository<?, Integer> repository;
        Object entity;

        repository = getRepository(pattern);

        entity = repository.findById(id).orElse(null);
        if(entity == null){
            logger.error(pattern+"에 "+id+" 존재하지 않음");
            throw new NullPointerException(pattern+"에 "+id+" 존재하지 않음");
        }
        return entity;
    }

    private JpaRepository<?, Integer> getRepository(String pattern) {
        return switch (pattern) {
            case "alarm" -> alarmRepository;
            case "room", "profile" -> userRepository;
            case "question", "answer" -> pattern.equals("question") ? questionRepository : answerRepository;
            default -> throw new IllegalStateException("Unexpected value: " + pattern);
        };
    }

    private JpaRepository<?, Integer> getRepository(Object entity) {
        if (entity instanceof Alarm) {
            return alarmRepository;
        } else if (entity instanceof User) {
            return userRepository;
        } else if (entity instanceof Question) {
            return questionRepository;
        } else if (entity instanceof Answer) {
            return answerRepository;
        } else {
            throw new IllegalStateException("Unexpected value: " + entity.getClass());
        }
    }
}