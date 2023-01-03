# Flask Example Application

Uses Flask internal HTTP server.

# Installation

The following command you can build and run the application.

```shell
# build docker image
docker build -t flash-http:0.0.1 .

# run docker image
docker run --rm -it --memory="32MB" --cpus="0.5" -p 8080:5000 --name flask-http flask-http:0.0.1

# you can follow the CPU and Memory usage of the container
docker stats

# you can run load test with k6
k6 run --no-usage-report --vus 100 --duration 60s --summary-export out.json script.js
```

k6 load test results

```
  "avg": 2072.3973075735476,
  "min": 16.702628
  "med": 2206.237762,
  "max": 2972.97819,
  "p(90)": 2800.105697,
  "p(95)": 2812.400944,
```
