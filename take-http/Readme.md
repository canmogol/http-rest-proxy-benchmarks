# Take Server Example Application

Uses Take Server.

# Installation

The following command you can build and run the application.

```shell
# build project
mvn clean install

# you can run the following command to generate native-image related configurations
java --enable-preview -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar target/TakeApplication.jar

# create native image from executable Jar
native-image --enable-preview --no-fallback -H:+ReportExceptionStackTraces -jar target/TakeApplication.jar

# build docker image
docker build -t take-http:0.0.1 .

# run docker image
docker run --rm -it --memory="32MB" --cpus="0.5" -p 8080:5000 --name take-http take-http:0.0.1

# you can follow the CPU and Memory usage of the container
docker stats

# you can run load test with k6
k6 run --no-usage-report --vus 100 --duration 60s --summary-export out.json script.js
```

k6 load test results

```
  "avg": 2450.1040578908883,
  "min": 16.959373,
  "med": 2506.577063,
  "max": 7373.397436
  "p(90)": 3706.2132669000002,
  "p(95)": 4114.267093199999,
```

Number of requests `2502`.
