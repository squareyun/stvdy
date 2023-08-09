package com.ssafy.ssap.util;

import com.ssafy.ssap.exception.S3Exception;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Service
public class S3Util {
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
        while(li.hasNext()){
            MultipartFile file = li.next();
            prefix = pattern.substring(0,2); // "al" / "pr" / "ro" ...
            currentFileName = file.getOriginalFilename(); // "filename.jpg"
            if(currentFileName == null) throw new NullPointerException("file Original Name 불러오기 실패");
            String fileExtension = currentFileName.substring(currentFileName.lastIndexOf('.')); //".jpg"
            if(fileExtension.length()>4) throw new S3Exception("File Extension Wrong");

            sb = new StringBuilder();
            sb.append("image/");
            sb.append(pattern);
            sb.append("/");
            switch(prefix){
                case "al", "pr", "ro" -> sb.append(prefix);
                default -> throw new S3Exception("unoccurable ERROR. Just for double check");
            }
            sb.append(id);
            sb.append(fileExtension);

            resultString.add(sb.toString());
        }
        return resultString;
    }
}
