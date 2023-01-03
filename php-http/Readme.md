# PHP Server Example

Uses internal server.

```
docker build -t php-http:0.0.1 .

docker run --rm -it --memory="32MB" --cpus="0.5" -p 8080:8080 --name  php-http  php-http:0.0.1
```
