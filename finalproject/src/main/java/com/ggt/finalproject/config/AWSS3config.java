package com.ggt.finalproject.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSS3config {

    // AWS 와 연결하는 부분
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

//    이 코드는 "아마존 S3 클라이언트"라는 이름의 스프링 빈 메서드를 정의합니다.
//    이 메서드는 Java용 AWS SDK를 사용하여 Amazon S3 Client의 인스턴스를 생성합니다.
//    @Bean 주석은 Spring 프레임워크에 이 방법을 빈 정의로 처리하도록 지시하여 결과 개체를
//    응용 프로그램의 다른 부분에서 종속성 주입에 사용할 수 있게 한다.
//    메소드는 먼저 Basic을 생성합니다accessKey 및 secretKey 문자열이 제공된 AWS 자격 증명 개체입니다.
//    그런 다음 원하는 AWS 영역이 있는 AmazonS3ClientBuilder와 aWSCreds 개체를 래핑하는 이전에
//    생성된 AWSStaticCredentialsProvider 인스턴스를 사용하여 AmazonS3Client 인스턴스를 구축합니다.
//    그런 다음 결과 클라이언트가 메소드에서 반환됩니다
    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}
