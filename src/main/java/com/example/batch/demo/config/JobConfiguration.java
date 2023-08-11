package com.example.batch.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.batch.demo.tasklet.DemoS3OperationTasklet;
import com.example.batch.demo.util.AWSS3Accessor;

@Configuration
public class JobConfiguration {
    Logger logger = LoggerFactory.getLogger(JobConfiguration.class);
    
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    @Qualifier("awsS3Accessor") 
    AWSS3Accessor awsS3Accessor;

    @Value("${demo.batch.s3.bucket}")
	String bucketName;	
	
	@Value("${demo.batch.s3.outputFile}")
	String outputFileName;

    @Bean(name = "DemoS3BatchJob")
    public Job demoS3BatchJob() {
        return jobBuilderFactory
                .get("DemoS3BatchJob")
                .start(demoTaskletStep())
                .build();
    }
    
    @Bean
    public Step demoTaskletStep() {
        return stepBuilderFactory.get("DemoTasklet")
                .tasklet(demoTasklet())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet demoTasklet(){
        return new DemoS3OperationTasklet(awsS3Accessor, bucketName, outputFileName);
    }
}
