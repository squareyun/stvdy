package com.ssafy.ssap.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class S3Service {

    public HttpStatus uploadSingleFile(String pattern, MultipartFile file, Integer id) {
        return null;
    }

    public HttpStatus uploadMultiFile(String pattern, MultipartFile file, Integer id) {
        return null;
    }

    public Map<String, Object> getSingleUrl(String pattern, Integer id) {
        return null;
    }

    public Map<String, Object> getMultiUrl(String pattern, Integer id) {
        return null;
    }
}
