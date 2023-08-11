package com.example.batch.demo;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	Job job;

	@Autowired
	JobLauncher jobLauncher;

	@Test
	void testJobRun() throws Exception {
		//Every instance of a job must have uniquely-identifying job parameters.
		//Examples: the timestamp of the job, a filename that changes daily, or a year+month combination.
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("timestamp", System.currentTimeMillis())
				.toJobParameters();

		JobExecution jobExecution = jobLauncher.run(job, jobParameters);

		//This assertion is only testing whether the job completed
		Assert.assertEquals(ExitStatus.COMPLETED.getExitCode(), jobExecution.getExitStatus().getExitCode());
	}

}
