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


running (1m02.4s), 000/100 VUs, 899 complete and 0 interrupted iterations
default ✓ [======================================] 100 VUs  1m0s

     data_received..................: 121 kB 1.9 kB/s
     data_sent......................: 72 kB  1.2 kB/s
     http_req_blocked...............: avg=135.17µs min=1.31µs   med=5.34µs  max=10.65ms  p(90)=129.97µs p(95)=804.59µs
     http_req_connecting............: avg=111.53µs min=0s       med=0s      max=10.6ms   p(90)=78.5µs   p(95)=677.92µs
     http_req_duration..............: avg=6.82s    min=819.83µs med=2.37s   max=32.99s   p(90)=17.3s    p(95)=23.08s
       { expected_response:true }...: avg=6.82s    min=819.83µs med=2.37s   max=32.99s   p(90)=17.3s    p(95)=23.08s
     http_req_failed................: 0.00%  ✓ 0         ✗ 899
     http_req_receiving.............: avg=138.08µs min=19.06µs  med=85.05µs max=2.42ms   p(90)=278.13µs p(95)=360.32µs
     http_req_sending...............: avg=68.64µs  min=6.07µs   med=26.34µs max=740.29µs p(90)=141.31µs p(95)=355.22µs
     http_req_tls_handshaking.......: avg=0s       min=0s       med=0s      max=0s       p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=6.82s    min=756.57µs med=2.37s   max=32.99s   p(90)=17.3s    p(95)=23.08s
     http_reqs......................: 899    14.402987/s
     iteration_duration.............: avg=6.82s    min=895.58µs med=2.37s   max=32.99s   p(90)=17.3s    p(95)=23.08s
     iterations.....................: 899    14.402987/s
     vus............................: 22     min=22      max=100
     vus_max........................: 100    min=100     max=100
     
     
  "avg": 6828.253567398218,
  "min": 0.819836,
  "med": 2371.334305,
  "max": 32995.596396,
  "p(90)": 17309.757713600004,
  "p(95)": 23081.0880862
```
