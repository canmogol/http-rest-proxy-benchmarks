# Spring Web Virtual Threads Example Application

A web application that uses Virtual Threads for Spring Web/Rest and HttpClient with Virtual Threads.

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
docker build -t spring-virtual-threads:0.0.1 .

# run docker image
docker run --rm -it --memory="32MB" --cpus="0.5" -p 8080:8080 --name spring-virtual-threads spring-virtual-threads:0.0.1

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


running (1m00.5s), 000/100 VUs, 6561 complete and 0 interrupted iterations
default ✓ [======================================] 100 VUs  1m0s

     data_received..................: 886 kB 15 kB/s
     data_sent......................: 525 kB 8.7 kB/s
     http_req_blocked...............: avg=52.77µs  min=1.13µs  med=13.52µs  max=13.65ms p(90)=20.36µs  p(95)=31.18µs
     http_req_connecting............: avg=32.3µs   min=0s      med=0s       max=4.9ms   p(90)=0s       p(95)=0s
     http_req_duration..............: avg=918.9ms  min=4.42ms  med=829.26ms max=2.89s   p(90)=1.8s     p(95)=2.03s
       { expected_response:true }...: avg=918.9ms  min=4.42ms  med=829.26ms max=2.89s   p(90)=1.8s     p(95)=2.03s
     http_req_failed................: 0.00%  ✓ 0          ✗ 6561
     http_req_receiving.............: avg=236.43µs min=14.77µs med=172.15µs max=15.14ms p(90)=358.11µs p(95)=545.45µs
     http_req_sending...............: avg=89.81µs  min=5.06µs  med=63.83µs  max=12.17ms p(90)=129.58µs p(95)=191.23µs
     http_req_tls_handshaking.......: avg=0s       min=0s      med=0s       max=0s      p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=918.58ms min=4.38ms  med=828.99ms max=2.89s   p(90)=1.8s     p(95)=2.03s
     http_reqs......................: 6561   108.381948/s
     iteration_duration.............: avg=919.31ms min=4.5ms   med=829.89ms max=2.89s   p(90)=1.8s     p(95)=2.03s
     iterations.....................: 6561   108.381948/s
     vus............................: 100    min=100      max=100
     vus_max........................: 100    min=100      max=100
     
     
  "avg": 918.9077645514384,
  "min": 4.423268,
  "med": 829.26362,
  "max": 2890.260925,
  "p(90)": 1801.756002,
  "p(95)": 2032.815134

```
