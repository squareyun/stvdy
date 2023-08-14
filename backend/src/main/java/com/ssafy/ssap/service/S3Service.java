package com.ssafy.ssap.service;

import com.ssafy.ssap.controller.RoomController;

import com.ssafy.ssap.domain.qna.ArticleImage;
import com.ssafy.ssap.exception.S3Exception;
import com.ssafy.ssap.repository.*;
import com.ssafy.ssap.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class S3Service {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final S3Util s3Util;
    private final AlarmRepository alarmRepository;
    private final ArticleImageRepository articleImageRepository;

    @Transactional
    public HttpStatus uploadFile(String pattern, List<MultipartFile> files, Integer id) {
        if(pattern.equals("question")){
            if(articleImageRepository.findAllByQuestionId(id)!=null){
                articleImageRepository.deleteByQuestionId(id);
            }
        } else if(pattern.equals("answer")){
            if(articleImageRepository.findAllByAnswerId(id)!=null){
                articleImageRepository.deleteByAnswerId(id);
            }
        }

        List<String> fileNameList = s3Util.createFileName(files, pattern, id);

        Object entity = s3Util.checkPresentAndGetEntity(pattern, id);

        int counter=0;
        for (MultipartFile file : files) {
            //각 파일 업로드에 대해 아마존에 별도 요청
            try {
                s3Util.uploadSingleFileToS3(file, fileNameList.get(counter++));
            } catch (IOException e) {
                logger.error("파일 업로드 중 에러");
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        //업로드 성공 시 DB에 경로 저장.
        s3Util.updateDatabase(entity, fileNameList);
        return HttpStatus.OK;
    }

    public Map<String, Object> getUrl(String pattern, Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        String url;
        try{
            switch(pattern){
                case "profile", "room" -> throw new S3Exception("User 객체에서 얻기 바람");
                case "alarm" -> url = alarmRepository.findById(id).orElse(null).getImagePath();
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

    public Map<String, Object> getUrlList(String pattern, Integer id) {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> urlList = new ArrayList<>();
        try{
            switch(pattern){
                case "question" -> {
                    for(ArticleImage ai : articleImageRepository.findAllByQuestionId(id))
                        urlList.add(ai.getImagePath());
                }
                case "answer" -> {
                    for(ArticleImage ai : articleImageRepository.findAllByAnswerId(id))
                        urlList.add(ai.getImagePath());
                }
                default -> throw new NullPointerException();
            }
            resultMap.put("urlList",urlList);
            return resultMap;
        }catch(NullPointerException e){
            logger.error("getMultiUrl failed");
            resultMap.put("message","getSingleUrl failed");
            throw e;
        }
    }
}
