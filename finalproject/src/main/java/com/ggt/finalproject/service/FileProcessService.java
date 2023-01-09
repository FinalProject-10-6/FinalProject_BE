package com.ggt.finalproject.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileProcessService {
    private final AwsS3Service amazonS3Service;

    public String uploadFile(MultipartFile file){
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        try(InputStream inputStream = file.getInputStream()){
            amazonS3Service.uploadFile(inputStream, objectMetadata, fileName);
        }catch (IOException e){
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생했습니다. (%s)", file.getOriginalFilename()));
        }
        return amazonS3Service.getFileUrl(fileName);
    }
    //유니크한파일의 이름을 생성하는로직
    private String createFileName(String originalFilename) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFilename));
    }

    //파일 확장자명 겟
    private String getFileExtension(String fileName) {
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        }catch(StringIndexOutOfBoundsException e){
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s)입니다.",fileName));
        }
    }

    public void deleteFile(String url) {
        amazonS3Service.deleteFile(url);
    }


}