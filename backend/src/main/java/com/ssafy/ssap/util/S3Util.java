package com.ssafy.ssap.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.ssap.domain.qna.Answer;
import com.ssafy.ssap.domain.qna.ArticleImage;
import com.ssafy.ssap.domain.qna.Question;
import com.ssafy.ssap.exception.S3Exception;
import com.ssafy.ssap.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class S3Util {
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
        while (li.hasNext()) {
            MultipartFile file = li.next();
            prefix = pattern.substring(0, 2); // "al" / "pr" / "ro" ...
            currentFileName = file.getOriginalFilename(); // "filename.jpg"
            if (currentFileName == null) throw new NullPointerException("file Original Name 불러오기 실패");
            String fileExtension = currentFileName.substring(currentFileName.lastIndexOf('.')); //".jpg"
            if (fileExtension.length() > 4) throw new S3Exception("File Extension Wrong");

            sb = new StringBuilder();
            sb.append("image/");
            sb.append(pattern);
            sb.append("/");
            switch (prefix) {
                case "al", "pr", "ro" -> sb.append(prefix);
                default -> throw new S3Exception("unoccurable ERROR. Just for double check");
            }
            sb.append(id);
            sb.append(fileExtension);

            resultString.add(sb.toString());
        }
        return resultString;
    }

    public void uploadSingleFileToS3(MultipartFile file, String fileName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);
    }

    @Transactional
    public void updateDatabase(String pattern, Integer id, List<String> fileNameList) {
        String basePath = "https://s3.amazonaws.com/ssapbucket/";

        List<ArticleImage> articleImagesToUpdate = new ArrayList<>();
        JpaRepository<?, Integer> repository;

        switch (pattern) {
            case "alarm" -> repository = alarmRepository;
            case "room", "profile" -> repository = userRepository;
            case "question", "answer" -> repository = pattern.equals("question") ? questionRepository : answerRepository;
            default -> throw new IllegalStateException("Unexpected value: " + pattern);
        }

        for (String fileName : fileNameList) {
            String filePath = basePath + fileName;

            Optional<?> entityOptional = repository.findById(id);
            entityOptional.ifPresent(entity -> {
                if (pattern.equals("question") || pattern.equals("answer")) {
                    ArticleImage articleImage = new ArticleImage();
                    if (entity instanceof Question) {
                        articleImage.setQuestion((Question) entity);
                    } else if (entity instanceof Answer) {
                        articleImage.setAnswer((Answer) entity);
                    }
                    articleImage.setPath(filePath);
                    articleImagesToUpdate.add(articleImage);
                } else {
                    try {
                        BeanUtils.setProperty(entity, "imagePath", filePath);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        // Bulk update all article images if applicable
        if (!articleImagesToUpdate.isEmpty()) {
            articleImageRepository.saveAll(articleImagesToUpdate);
        }

        // Bulk update entities with image paths
        repository.flush();
    }
}

/*
//        for(int i=0;i<fileNameList.size();i++) {
//            String filePath = basePath+fileNameList.get(i);
//            switch (pattern) {
//                case "alarm" -> {
//                    Alarm alarm = alarmRepository.findById(id).orElse(null);
//                    if (alarm != null) alarm.setImagePath(filePath);
//                }
//                case "room", "profile" -> {
//                    User user = userRepository.findById(id).orElse(null);
//                    if (user != null) user.setImagePath(filePath);
//                }
//                case "question" -> {
//                    Question question = questionRepository.findById(id).orElse(null);
//                    if (question != null) {
//                        ArticleImage articleImage = ArticleImage.builder()
//                                .question(question)
//                                .path(filePath)
//                                .build();
//                        articleImageRepository.save(articleImage);
//                    }
//                }
//                case "answer" -> {
//                    Answer answer = answerRepository.findById(id).orElse(null);
//                    if (answer != null) {
//                        ArticleImage articleImage = ArticleImage.builder()
//                                .answer(answer)
//                                .path(filePath)
//                                .build();
//                        articleImageRepository.save(articleImage);
//                    }
//                }
//                default -> throw new IllegalStateException("Unexpected value: " + pattern);
//            }
//        }
//    }
 */