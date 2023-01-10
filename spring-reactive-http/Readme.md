# Spring Web Example Application

A web application that uses Spring's Reactive Controller and Reactive HTTP Client.

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
docker build -t spring-reactive-http:0.0.1 .

# run docker image
docker run --rm -it --memory="32MB" --cpus="0.5" -p 8080:8080 --name spring-reactive-http spring-reactive-http:0.0.1

# you can follow the CPU and Memory usage of the container
docker stats

# you can run k6 as an alternative load test
k6 run --no-usage-report --vus 100 --duration 60s --summary-export out.json script.js

```

Here are the load test results.
```
-> % k6 run --no-usage-report --vus 100 --duration 60s --summary-export out.json script.js

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


running (1m00.5s), 000/100 VUs, 9324 complete and 0 interrupted iterations
default ✓ [======================================] 100 VUs  1m0s

     data_received..................: 1.0 MB 17 kB/s
     data_sent......................: 746 kB 12 kB/s
     http_req_blocked...............: avg=235.87µs min=1.25µs  med=14.76µs  max=49.82ms  p(90)=20.98µs  p(95)=30.86µs
     http_req_connecting............: avg=209.98µs min=0s      med=0s       max=49.77ms  p(90)=0s       p(95)=0s
     http_req_duration..............: avg=645.93ms min=3.94ms  med=689.38ms max=1.8s     p(90)=979.56ms p(95)=1.08s
       { expected_response:true }...: avg=645.93ms min=3.94ms  med=689.38ms max=1.8s     p(90)=979.56ms p(95)=1.08s
     http_req_failed................: 0.00%  ✓ 0          ✗ 9324
     http_req_receiving.............: avg=61.11ms  min=16.87µs med=21.16ms  max=489.85ms p(90)=155.33ms p(95)=201.87ms
     http_req_sending...............: avg=208.46µs min=5.59µs  med=68.83µs  max=34.65ms  p(90)=127.58µs p(95)=196.18µs
     http_req_tls_handshaking.......: avg=0s       min=0s      med=0s       max=0s       p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=584.61ms min=3.9ms   med=599.8ms  max=1.71s    p(90)=895.4ms  p(95)=993.83ms
     http_reqs......................: 9324   154.199901/s
     iteration_duration.............: avg=646.61ms min=3.98ms  med=689.8ms  max=1.8s     p(90)=980.06ms p(95)=1.08s
     iterations.....................: 9324   154.199901/s
     vus............................: 100    min=100      max=100
     vus_max........................: 100    min=100      max=100



  "min": 3.944317,
  "med": 689.3801515,
  "max": 1809.360494,
  "p(90)": 979.5628662,
  "p(95)": 1081.7710402500002,
  "avg": 645.9342324832683
```
