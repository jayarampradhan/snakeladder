Credit Score
==============

[![Build Status](https://jenkins.mint.com/buildStatus/icon?job=CI_CreditScore)](https://jenkins.mint.com/view/All/job/CI_CreditScore/)

[![Deployment Status](https://jenkins.mint.com/buildStatus/icon?job=CD_CreditScore)](https://jenkins.mint.com/view/All/job/CD_CreditScore/)

## What it helps

* Provides Rest API for the credit score services.

## What are the service Endpoints

- Do see the hosted swagger docs, or if you are installing this service in local, access the swagger doc at host:prt/doc/swagger.json

## Technology Stack
### Programming Language

- jdk 1.8
- Spring 4.3.4.RELEASE
- Spring Reactor 2.5.0
- Jersey 2.24.1
- Spring Boot 1.4.2.RELEASE
- Hibernate 5.2.1.Final

### Data base/Cache

- Mysql with sharding
- Elastic Cache (Redis as provider)

### Build Tool

- maven 3.2.5

### Server

- Tomcat 8.5.6
- Nginx

### Virtual Box

- Vagrant 1.8.1

### OS

- Installable OS (any linux favor)
- Mac for standalone run

## Developer/Contributors

- Glad you will be maintaining or adding new features to this service, Please keep *DO* and *DONT* in mind before pushing anything.
- Please don't override/ dismiss any editor config settings, its configured what was intended. [More Details](http://editorconfig.org/)
- A normal JLS standard specific check style has been in place, which is bind to build process. [More Details](https://github.intuit.com/consumer-finance/credit-score/tree/master/src/checkstyle)
- When in confusion please don't hesitate to raise a PR.   
- PR validation check has been enabled [More Details](https://jenkins.mint.com/job/CS_PR_Validation/) Wish, *to enable Docker Validation post the test case validation.*
- There is a integration test suite job [More Deatils](https://jenkins.mint.com/job/CS_PR_Validation/) which runs post stage and e2e build, which can be triggered on demand.

### Prerequisites:

- Should have `jdk 1.8`
- Should have `Maven 3.2.5`
- Nice to have [Vagrant](http://vagrant.com)

### First Time Developer machine Set Up

- clone this repo `git clone https://github.intuit.com/jpradhan/credit-score`
- Navigate to the checkout folder and do `./script/install-git-hooks.sh` 
(This will help to install all the hooks needed as per the code policy and guide line)
- If you are using any IDE, move to [StartApp](https://github.intuit.com/jpradhan/credit-score/blob/master/creditscoreapi/src/main/java/com/intuit/finance/credit/api/StartApp.java)
  Run as Java Application and api will be available at http://127.0.0.1:8181/api and swagger docs are available at http://127.0.0.1:8181/doc/swagger.json

#### Have Vagrant?

- Navigate to the Vagrant folder and do `vagrant up cs_local`
  Wait till machine to be up.
  
```bash
vagrant ssh
sudo -s
[root@layer201 docker]# cd /dev/workspace/docker/
[root@layer201 docker]# ./docker-app-builder.sh -f -n #here `f` is for force build and `n ` is for all new containers

After this you will see something like below

Docker Is started check status by 
 CONTAINER ID        NAMES               STATUS
5a2e11d8ff0b        cs_pe_app_1         Up Less than a second
dc6d26911d59        cs_web              Up 1 second
120bf289d81d        cs_app_1            Up 1 second
c1926914237f        cs_activemq         Up 2 seconds
65a101ae54b9        cs_app_cache        Up 3 seconds
be97ed78f91d        cs_app_db           Up 3 seconds

Cool, you made it right till now, now go ahead and check logs 

[root@layer201 docker]# docer logs 120bf289d81d --follow

or wait till app is up

Useful Links:

Actual API : http://localhost:8087/v1
Active MQ: http://localhost:8161/
Mysql: localhost:3307
Redis: 6380

At any point you want to debug application, no worries enable remote debuging by listnening to the port :
8000 for API debug
8001 for process engine debug
8002 for config server debug

In case a config server needs to be deployed in local, please create your password file in docker/git_password.txt
Also modify the application-docker.json to point your user ID.

Given these two things are in place, you are all set for local config server
 
CONFIG Server: http://localhost:8282/  
       
```
  
- Vagrant is a actually replica of the any other deployment in your local.  
  

```bash 
python cloudstacks.py create sameer -tcreditscoreapi --pod 8 --env stage #to create a new instance
python cloudstacks.py list sameer -tcreditscoreapi -v #use `-c` option to get the stack info from cache
python cloudstacks.py delete sameer 2016-04-23T14-41-31-556769 #to delete a existing instance
python cloudstacks.py delete sameer 2016-04-23T14-41-31-556769 #to delete a existing instance
```

### Running Test

- This has various set of integration tests to run against any enviornment such as dev, docker, stage and e2e (currently prod test cases are not part of as no test data available)
- To run api test have a look under test/groovy folder, or simply invoke the below command
```bash
mvn clean test -pl api -DskipTests -DrunITest=true -Dendpoint=local
here endpoint can be any environment.
```
above will only run the integration test skipping the unit tests.

- Desire is to have production test data along with other module such as PE, admin api etc.
  
### Practice to follow

- On the day of starting the sprint, please create a new version by below command
```bash   
    mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion}-SNAPSHOT versions:commit
```   
  Above will set a next development snapshot version and you can get started with the development.
   
- On the day of the final release set the version by below command
```bash
 mvn build-helper:parse-version versions:set -DnewVersion=\${parsedVersion.majorVersion}.\${parsedVersion.minorVersion}.\${parsedVersion.nextIncrementalVersion} versions:commit
``` 
 Above will assign the release version. 

### Common Commands

```bash
  mvn clean install -DgenerateRPM=true -DuploadRPM=true -DdeploymentArtifact=true -DuploadDist=true
  #Here `generateRPM` specifies this will generate RPM
  # `uploadRPM` Specifies, rpm will be uploaded to `yum repo`
  # `deploymentArtifact` Specifies to generate artifcats for the deployment
  # `uploadDist` Specifies whether to generate deployment artifacts or not
  # `-U` Specifies to use updated SNAPSHOT version of libraries
```



## Operations

Most of the OPS work has been automated, to deploy a version, download the deployment artifacts and follow the readme section.

### OPS stack

- Chef
- Python
- Jinja
- Shell
- Supported OS: RHEL 7.x, Centos 7.x

### Start/Stop/Status

```bash
systemctl start creditscoreapi #To start the service
systemctl stop creditscoreapi #To stop the service
systemctl status creditscoreapi #To know the status of the service
systemctl restart creditscoreapi #To know restart the service
```

### Configurations

Application supports multiple ways of configuration

While starting the application create environment specific [application.json](https://github.intuit.com/jpradhan/credit-score/blob/master/creditscoreapi/src/main/resources/application.json)
and place this either in /dev/shm or pass the argument `-Dapp.conf=classpath:file.json` or `-Dapp.conf=file:file.json`
   
`/etc/sysconfig/creditscoreapiconfig` is the primary source
`/opt/creditscoreapi/conf` has various configuration for logging and application property for each enviornment.

### Development Using Chef

This Application has defined a cook book which is again depend on some other third party cook book, For managing dependency
It Uses `Berks`

This cook book expects to present in `/tmp/creditscoreapi/chef/`, and all the chef related command to be executed from `/tmp/creditscoreapi/chef/creditscoreapi`

Steps are mentioned below:

1. Grab creditscoreapi-deployment-#version.tar.gz from s3 //TODO add location
2. UnTar to `/tmp/`, this will have a read me file along with chef contents inside `/tmp/creditscoreapi/` 
3. First resolve dependency using `berks vendor vendor/cookbooks`
4. Run The final Chef client to configure and start the application `chef-client -z -c client-<env>.rb`, here env can be of any `[stage,e2e,prod]`

This cook book is associated with the appropriate application rpm, in case any modification needed, navigate to the `environments/` and change the appropriate 
environment file under app->version

Have fun with application :)

### Logs

```bash
tail -f /var/log/creditscoreapi/application/application.log
tail -f /var/log/creditscoreapi/tomcat/access.log
tail -f /var/log/creditscoreapi/nginx/access.log
```

### Jenkins Job information

This section talk about CICD job and pipeline for credit score

### CI job detail:
https://jenkins.mint.com/view/CreditScore/job/CI_CreditScore

## CI job does the following:
```
1. Git checkout master branch
2. Compile and build
3. Run unit tests
4. Get Coverage using jacoco
5. Send email notification to the team
6. Create rpm and war and upload it to s3
```

### CD job detail:
https://jenkins.mint.com/view/CreditScore/job/CD_CreditScore

##
CD job does the following:
```
1. Git checkout master branch
2. run the build.sh script which takes two argument :
    Endpoint
    Environment
   Both value should get pass from jenkins.

This script does the following:
# Get the latest artifact version by parsing the POM
# Check the artifact version zip is available in s3 location
# If available then download it from s3 location and deploy to stage
# If not available then first create the artifact and push it to s3 location then deploy
```

Build Pipeline also available to get the information of both the job.
https://jenkins.mint.com/view/CreditScore/


### Miscellaneous

Do you know, you can change log configuration on the fly, simply navigate to `/opt/creditscoreapi/conf/logback-external.xml`
for tomcat log change `/opt/creditscoreapi/conf/logback-tom-access.xml` and apply the changes. Normaly it takes 300 second to apply the changes

Do you know while testing you need not to generate an rpm, you could attach another rpm to your build process via `-Dapp.version=some_version-some_release`
```bash
mvn clean install -DdeploymentArtifact=true -Dapp.version=some_version-some_release
```
