package com.example.batch.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.example.batch.demo.util.AWSS3Accessor;

@Configuration
public class AWSConfiguration {
    @Value("${aws.region}")
    private String region;
    
    public AmazonS3Client amazonS3Client() {
        AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard().withRegion(region).build();
        return amazonS3Client;
    }
    
    @Bean(name="awsS3Accessor")
    public AWSS3Accessor awsS3Accessor() {
    	AWSS3Accessor s3Accessor = new AWSS3Accessor(amazonS3Client());
    	return s3Accessor;    	
    }
}
