package com.ssafy.ssap.util;

import com.ssafy.ssap.exception.S3Exception;
import org.springframework.stereotype.Service;

@Service
public class S3Util {
    public String createFileName(String originalName, String pattern, Integer id) {
        //alarm -> alarmId -> a1, a2, a3, ...
        //profile -> userId -> p1, p2, p3, ...
        //room -> userId -> r1, r2, r3, ...
        String tmp;
        tmp = pattern.substring(0,2);
        String fileExtension = originalName.substring(originalName.lastIndexOf('.'));
        if(fileExtension.length()>4) throw new S3Exception("File Extension Wrong");

        StringBuilder sb = new StringBuilder();
        sb.append("image/");
        sb.append(pattern);
        sb.append("/");
        switch(tmp){
            case "al", "pr", "ro" -> sb.append(tmp);
            default -> throw new S3Exception();
        }
        sb.append(id);
        sb.append(fileExtension);
        return sb.toString();
    }
}
