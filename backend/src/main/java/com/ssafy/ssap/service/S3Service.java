package com.ssafy.ssap.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.ssap.controller.RoomController;
import com.ssafy.ssap.domain.user.User;
import com.ssafy.ssap.repository.UserRepository;
import com.ssafy.ssap.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class S3Service {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final AmazonS3Client amazonS3;
    private final S3Util s3Util;
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public HttpStatus uploadSingleFile(String pattern, MultipartFile file, Integer id) {
        try {
            String fileName = s3Util.createFileName(file.getName(),pattern,id);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucket, fileName, file.getInputStream(), metadata);

            //업로드 성공 시 DB에 경로 저장
            String filePath = "https://s3.amazonaws.com/ssapbucket/"+fileName;
            Object object = switch(pattern){
                case "alarm" -> alarmRepository.findById(id).orElse(null);
                case "room", "profile" -> userRepository.findById(id).orElse(null);
            };
            if(object instanceof Alarm){
                ((Alarm)object).setImagePath(filePath);
            }else if(object instanceof User){
                ((User)object).setImagePath(filePath);
            }


            return HttpStatus.OK;
        }catch(IOException e){
            logger.error("파일 업로드 중 IO에러 발생");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }catch(AmazonS3Exception e){
            logger.error("S3에 업로드 중 에러 발생");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public HttpStatus uploadMultiFile(String pattern, MultipartFile file, Integer id) {
        return null;
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
