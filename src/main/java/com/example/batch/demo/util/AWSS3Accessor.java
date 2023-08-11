package com.example.batch.demo.util;

import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;

public class AWSS3Accessor {
    private AmazonS3Client amazonS3Client;
	
	public AWSS3Accessor(AmazonS3Client amazonS3Client) {
		 this.amazonS3Client = amazonS3Client;
	}
	
	public S3Object getFileFromS3(String bucketName, String fileName) throws IOException {
		return amazonS3Client.getObject(bucketName, fileName);
	}
	
	public void writeFileToS3(String bucketName, String fileName, String fileContent) throws IOException {
		amazonS3Client.putObject(bucketName, fileName, fileContent);
		
	}    
}
