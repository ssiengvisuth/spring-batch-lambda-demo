package com.example.batch.demo.lambda;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.batch.demo.DemoApplication;

public class LambdaHandler {
    public static final String JOB_NAME = "DemoS3BatchJob";

    private static ConfigurableApplicationContext applicationContext;
    static {
        applicationContext = SpringApplication.run(DemoApplication.class);
    }

    public String handleRequest(Context context) {
        String status = ExitStatus.FAILED.getExitCode();

        try {
            Job batchJob = (Job)applicationContext.getBean(JOB_NAME);
            JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
            
            JobParameters jobParameters = new JobParametersBuilder()
			    .addString("name", JOB_NAME)	
                .addLong("timestamp", System.currentTimeMillis())				
				.toJobParameters();

            JobExecution jobExecution = jobLauncher.run(batchJob, jobParameters);
            status = jobExecution.getExitStatus().getExitCode();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return status;
    }
}
