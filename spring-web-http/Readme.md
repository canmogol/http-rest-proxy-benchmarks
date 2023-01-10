# Spring Web Example Application

A web application that uses Spring's RestController and RestTemplate.

# Installation

The following steps explain, build, compile to native, create docker image, and run load test.

```shell
# use Graalvm Java 19 version
sdk install java 22.3.r19-grl

# install native image command line tool
gu install native-image

# build project
mvn clean install

# create native image 
./mvnw native:compile -Pnative

# build docker image
docker build -t spring-web-http:0.0.1 .

# run docker image
docker run --rm -it --memory="32MB" --cpus="0.5" -p 8080:8080 --name spring-web-http spring-web-http:0.0.1

# you can follow the CPU and Memory usage of the container
docker stats

# you can run k6 as an alternative load test
k6 run --no-usage-report --vus 100 --duration 60s --summary-export out.json script.js

```
