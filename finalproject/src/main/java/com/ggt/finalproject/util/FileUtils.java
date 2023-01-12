package com.ggt.finalproject.util;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    private static final Tika tika = new Tika();


    // 이미지 파일인지 아닌지 확인하는 유틸이며 이미지 확장자면 True 아니라면 false를 반환
    public static boolean validImgFile(MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            List<String> notValidTypeList = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/bmp");

            String mimeType = tika.detect(inputStream);
            System.out.println("MimeType : " + mimeType);

            boolean isValid = notValidTypeList.stream().anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));

            return isValid;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}