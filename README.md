# Jobs Test Task

### Table of contents

1. [ Project description ](#project-description)
2. [Jobs information update](#jobs-information-update)
3. [ Access to functionality ](#access-to-functionality)


### Project description
This is a web application that allows user to access jobs' information from arbeitnow.com.

### Jobs information update
Database contains 500 jobs from arbeitnow.com and automatically updates itself every week. 
Jobs quantity and update frequency could be changed upon request.

### Access to functionality
Application is deployed to AWS Elastic Beanstalk, you can try it out with links:
- [all endpoints in swagger](http://jobs-test-task-env.eba-vw5dmcz4.eu-north-1.elasticbeanstalk.com/swagger-ui/index.html)
- [get all jobs](http://jobs-test-task-env.eba-vw5dmcz4.eu-north-1.elasticbeanstalk.com/jobs)
- [get top ten jobs](http://jobs-test-task-env.eba-vw5dmcz4.eu-north-1.elasticbeanstalk.com/jobs/top-ten)
- [get locations statistics](http://jobs-test-task-env.eba-vw5dmcz4.eu-north-1.elasticbeanstalk.com/jobs/locations-statistics)