package com.ssafy.ssap.controller;

import com.ssafy.ssap.exception.S3Exception;
import com.ssafy.ssap.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class S3Controller {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final S3Service s3Service;

    @PostMapping("/upload/{pattern}/{id}")
    public ResponseEntity<?> uploadByPattern(@PathVariable String pattern, @PathVariable Integer id, @RequestParam List<MultipartFile> file){
        logger.trace("upload Controller 호출. "+pattern+"/"+id+"/"+file+"/"+file.size());
        HttpStatus status;
        status = s3Service.uploadFile(pattern, file, id);
        return new ResponseEntity<>(status);
    }

    @GetMapping("/get/{pattern}/{id}")
    public ResponseEntity<?> getUrl(@PathVariable String pattern, @PathVariable Integer id){
        logger.debug("");
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> resultMap;
        try {
            switch (pattern) {
                case "profile", "alarm", "room" -> resultMap = s3Service.getUrl(pattern, id);
                case "question", "answer" -> resultMap = s3Service.getUrlList(pattern, id);
                default -> throw new S3Exception(pattern + " 스펠링 확인");
            }
        }catch(Exception e){
            resultMap = new HashMap<>();
            resultMap.put("message", "getUrl 실패");
        }
        return new ResponseEntity<>(resultMap, status);
    }

//    @Deprecated
//    @GetMapping("/{fileName}")
//    public ResponseEntity<UrlResource> downloadImage(@PathVariable String fileName) {
//        System.out.println(fileName);
//        UrlResource urlResource = new UrlResource(amazonS3.getUrl(bucket, fileName));
//
//        String contentDisposition = "attachment; filename=\"" +  fileName + "\"";
//
//        // header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .body(urlResource);
//    }



}