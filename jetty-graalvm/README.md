# Jetty Example Application

Uses Jetty HTTP server.

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
java --enable-preview -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar target/JettyApplication.jar

# create native image from executable Jar
native-image --enable-preview --no-fallback -H:+ReportExceptionStackTraces -jar target/JettyApplication.jar

# build docker image
docker build -t jetty-grl-app:0.0.1 .

# run docker image
docker run --rm -it --memory="32MB" --cpus="0.5" -p 8080:8080 --name jetty-grl-app jetty-grl-app:0.0.1

# you can follow the CPU and Memory usage of the container
docker stats

# eventually run load test
for i in {1..100}; do ab -n 1000 -c 100  http://localhost:8080/ ; sleep 1; done

# you can run k6 as an alternative load test
k6 run --no-usage-report --vus 100 --duration 60s --summary-export out.json script.js

```


k6 load test results
```
          /\      |‾‾| /‾‾/   /‾‾/
     /\  /  \     |  |/  /   /  /
    /  \/    \    |     (   /   ‾‾\
   /          \   |  |\  \ |  (‾)  |
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: script.js
     output: -

  scenarios: (100.00%) 1 scenario, 100 max VUs, 1m30s max duration (incl. graceful stop):
           * default: 100 looping VUs for 1m0s (gracefulStop: 30s)


running (1m03.0s), 000/100 VUs, 1735 complete and 0 interrupted iterations
default ✓ [======================================] 100 VUs  1m0s

     data_received..................: 278 kB 4.4 kB/s
     data_sent......................: 139 kB 2.2 kB/s
     http_req_blocked...............: avg=64.28µs  min=1.33µs  med=5.75µs  max=6.42ms p(90)=20.51µs  p(95)=130.07µs
     http_req_connecting............: avg=42.01µs  min=0s      med=0s      max=6.35ms p(90)=0s       p(95)=68.8µs
     http_req_duration..............: avg=3.55s    min=19.12ms med=3.29s   max=11.06s p(90)=5.68s    p(95)=6.69s
       { expected_response:true }...: avg=3.55s    min=19.12ms med=3.29s   max=11.06s p(90)=5.68s    p(95)=6.69s
     http_req_failed................: 0.00%  ✓ 0         ✗ 1735
     http_req_receiving.............: avg=149.23µs min=19.28µs med=93µs    max=4.34ms p(90)=288.34µs p(95)=346.68µs
     http_req_sending...............: avg=59.2µs   min=6.05µs  med=27.91µs max=1.23ms p(90)=111.61µs p(95)=180.13µs
     http_req_tls_handshaking.......: avg=0s       min=0s      med=0s      max=0s     p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=3.55s    min=18.08ms med=3.29s   max=11.06s p(90)=5.68s    p(95)=6.69s
     http_reqs......................: 1735   27.544996/s
     iteration_duration.............: avg=3.55s    min=20.85ms med=3.29s   max=11.06s p(90)=5.68s    p(95)=6.69s
     iterations.....................: 1735   27.544996/s
     vus............................: 1      min=1       max=100
     vus_max........................: 100    min=100     max=100



-> % cat out.json| jq -r '.metrics."http_req_duration{expected_response:true}"'
{
  "avg": 3554.41467414697,
  "min": 19.124624,
  "med": 3296.591948,
  "max": 11064.801049,
  "p(90)": 5683.230346400001
  "p(95)": 6694.852608599997,
}
```
