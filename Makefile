AWS_ACCOUNT = [AWS_ACCOUNT_ID]
AWS_REGION = us-west-2

demo.batch.build:
	mvn clean install dependency:copy-dependencies
	docker build --platform linux/amd64 -f Dockerfile -t demo-lambda-spring-batch:1.0 .

demo.batch.run:
	docker run --rm --name demo-batch-container demo-lambda-spring-batch:1.0

demo.batch.console:
	docker run --rm -it --entrypoint bash demo-lambda-spring-batch:1.0

demo.batch.ecr.push:
	aws ecr get-login-password --region $(AWS_REGION) | docker login --username AWS --password-stdin $(AWS_ACCOUNT).dkr.ecr.$(AWS_REGION).amazonaws.com
	docker tag demo-lambda-spring-batch:1.0 $(AWS_ACCOUNT).dkr.ecr.$(AWS_REGION).amazonaws.com/demo-lambda-spring-batch:1.0
	docker push $(AWS_ACCOUNT).dkr.ecr.$(AWS_REGION).amazonaws.com/demo-lambda-spring-batch:1.0

demo.batch.ecr.run:
	docker run --rm --name demo-batch-container --platform linux/amd64 $(AWS_ACCOUNT).dkr.ecr.$(AWS_REGION).amazonaws.com/demo-lambda-spring-batch:1.0
