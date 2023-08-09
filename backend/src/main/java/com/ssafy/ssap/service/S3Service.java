package com.ssafy.ssap.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.ssap.controller.RoomController;
import com.ssafy.ssap.domain.alarm.Alarm;
import com.ssafy.ssap.domain.qna.Answer;
import com.ssafy.ssap.domain.qna.ArticleImage;
import com.ssafy.ssap.domain.qna.Question;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.repository.*;
import com.ssafy.ssap.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class S3Service {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final AmazonS3Client amazonS3;
    private final S3Util s3Util;
    private final ArticleImageRepository articleImageRepository;
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public HttpStatus uploadFile(String pattern, List<MultipartFile> files, Integer id) {
        List<String> fileNameList;
        ObjectMetadata metadata;
        String filePath;
        int counter=0;
        fileNameList = s3Util.createFileName(files, pattern, id);

        for (MultipartFile file : files) {
            //각 파일에 대해 아마존에 별도 요청
            metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            try {
                amazonS3.putObject(bucket, fileNameList.get(counter++), file.getInputStream(), metadata);
            } catch (IOException e) {
                e.printStackTrace();
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

            //업로드 성공 시 DB에 경로 저장. 최적화 가능(아마존 업로드 완료시 일괄 DB반영)
            filePath = "https://s3.amazonaws.com/ssapbucket/"+fileNameList;
            switch(pattern){
                case "alarm" -> {
                    Alarm alarm = alarmRepository.findById(id).orElse(null);
                    if(alarm!=null) alarm.setImagePath(filePath);
                }
                case "room", "profile" -> {
                    User user = userRepository.findById(id).orElse(null);
                    if(user!=null) user.setImagePath(filePath);
                }
                case "question" -> {
                    Question question = questionRepository.findById(id).orElse(null);
                    if(question!=null) {
                        ArticleImage articleImage = ArticleImage.builder()
                                .question(question)
                                .path(filePath)
                                .build();
                        articleImageRepository.save(articleImage);
                    }
                }
                case "answer" -> {
                    Answer answer = answerRepository.findById(id).orElse(null);
                    if(answer!=null) {
                        ArticleImage articleImage = ArticleImage.builder()
                                .answer(answer)
                                .path(filePath)
                                .build();
                        articleImageRepository.save(articleImage);
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + pattern);
            }
        }
        return HttpStatus.OK;
    }

    public Map<String, Object> getSingleUrl(String pattern, Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        String url;
        try{
            switch(pattern){
                case "profile", "room" -> url = userRepository.findImagePathById(id);
                case "alarm" -> url = alarmRepository.findImagePathById(id);
                default -> throw new NullPointerException();
            }
            resultMap.put("url",url);
            return resultMap;
        }catch(NullPointerException e){
            logger.error("getSingleUrl failed");
            resultMap.put("message","getSingleUrl failed");
            throw e;
        }
    }

    public Map<String, Object> getMultiUrl(String pattern, Integer id) {
        return null;
    }
}
