package com.example.batch.demo.tasklet;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.example.batch.demo.util.AWSS3Accessor;

public class DemoS3OperationTasklet implements Tasklet {
    private static Logger logger = LoggerFactory.getLogger(DemoS3OperationTasklet.class);

    private AWSS3Accessor awsS3Accessor;
	private String bucketName;
	private String outputFileName;

    private Boolean isRunningTest = null;

	public DemoS3OperationTasklet(AWSS3Accessor awsS3Accessor, String bucketName, String outputFileName) {
        this.awsS3Accessor = awsS3Accessor;
		this.bucketName = bucketName;
		this.outputFileName = outputFileName;
    }

    @Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		String name = chunkContext.getStepContext().getJobParameters().getOrDefault("name", "").toString();		
		if(name.isEmpty()) {
            logger.info("SampleS3OperationTasklet is activated.");
        } else {
            logger.info("[{}] Tasklet is activated.", name);
        }
		
        Date currentDate = new Date();
		String fileName = String.format(outputFileName, String.valueOf(currentDate.getTime()));
		
		logger.info("Writing Contents to S3 bucket with name:: {}", fileName);
		
		if (!isRunningTest()) 
			awsS3Accessor.writeFileToS3(bucketName, fileName, "Demo Spring Batch Application::" + currentDate.toString());
        
        logger.info("Task is completed.");
		
		return RepeatStatus.FINISHED;
	}

    public boolean isRunningTest() {
	    if (isRunningTest == null) {
	        isRunningTest = true;
	        try {
	            Class.forName("org.junit.Test");
	        } catch (ClassNotFoundException e) {
	            isRunningTest = false;
	        }
	    }
	    return isRunningTest;
	}
}
