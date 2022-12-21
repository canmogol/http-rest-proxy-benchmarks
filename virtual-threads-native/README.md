# Virtual Threads Example Application

Uses Java's internal HTTP server, HTTP client and virtual threads for both of them.

# Installation

The following steps explain, build, compile to native, create docker image, and run load test.

```shell
# use Graalvm Java 19 version
sdk install java 22.3.r19-grl

# install native image command line tool
gu install native-image

# build project
mvn clean install

# you can run the following command to generate native-image related configurations
java --enable-preview -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar target/VirtualThreadApplication.jar

# create native image from executable Jar
native-image --enable-preview --no-fallback -H:+ReportExceptionStackTraces -jar target/VirtualThreadApplication.jar

# build docker image
docker build -t vt-grl-app:0.0.1 .

# download and run docker-slim command line tool against the image
# this will create a new image 'vt-grl-app.slim:latest'  
curl -L -o ds.tar.gz https://downloads.dockerslim.com/releases/1.39.1/dist_linux.tar.gz
tar xfvz ds.tar.gz
./dist_linux/docker-slim build vt-grl-app:0.0.1

# run docker image
docker run --rm -it --memory="32MB" --cpus="0.5" -p 8080:8080 --name vt-grl vt-grl-app.slim:latest

# you can follow the CPU and Memory usage of the container
docker stats

# eventually run load test
for i in {1..100}; do ab -n 1000 -c 100  http://localhost:8080/test ; sleep 1; done

# you can also further reduce the executable image size using upx 
# install upx if not installed already, run upx to compress the executable
# after that you'll need to rebuild the docker image with the new executable. 
upx -v -7 -k VirtualThreadApplication

# you can run k6 as an alternative load test
k6 run --no-usage-report --vus 100 --duration 60s --summary-export out.json script.js

```
