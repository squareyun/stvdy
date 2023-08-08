package com.ssafy.ssap.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.ssap.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class S3Controller {

    private final AmazonS3Client amazonS3;
    private final S3Service s3Service;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping("/upload/{pattern}/{id}")
    public ResponseEntity<?> uploadByPattern(@PathVariable String pattern, @PathVariable Integer id, @RequestParam MultipartFile file){
        HttpStatus status;
        switch(pattern){
            case "profile", "alarm", "room" -> status = s3Service.uploadSingleFile(pattern, file, id);
            case "question", "answer" -> status = s3Service.uploadMultiFile(pattern, file, id);
            default -> status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    @GetMapping("/get/{pattern}/{id}")
    public ResponseEntity<?> getUrl(@PathVariable String pattern, @PathVariable Integer id){
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> resultMap;
        switch(pattern){
            case "profile", "alarm", "room" -> resultMap = s3Service.getSingleUrl(pattern, id);
            case "question", "answer" -> resultMap = s3Service.getMultiUrl(pattern, id);
            default -> {
                resultMap = new HashMap<>();
                resultMap.put("message", pattern+" 스펠링 확인");
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @Deprecated
    @PostMapping("/upload/SingleImage")
    public ResponseEntity<String> uploadSingleImage(@RequestParam("file") MultipartFile file) {
        System.out.println("upload 진입");
        HttpStatus status;
//        Map<String, Object> resultMap;
        try {
            String originalFilename = file.getOriginalFilename();

//            String fileUrl= "https://" + bucket + "/test" +originalFilename;
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(bucket,"image/"+originalFilename, file.getInputStream(), metadata);
            status = HttpStatus.OK;
        } catch (IOException e) {
//            resultMap = new HashMap<>();
//            res,ultMap.put("message","file upload failed");
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(status);
    }

    @Deprecated
    @GetMapping("/{fileName}")
    public ResponseEntity<UrlResource> downloadImage(@PathVariable String fileName) {
        System.out.println(fileName);
        UrlResource urlResource = new UrlResource(amazonS3.getUrl(bucket, fileName));

        String contentDisposition = "attachment; filename=\"" +  fileName + "\"";

        // header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }


}