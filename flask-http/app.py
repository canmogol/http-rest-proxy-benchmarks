import http.client
from flask import Flask

app = Flask(__name__)


@app.route('/')
def get():
    connection = http.client.HTTPConnection("172.17.0.1:9090")
    connection.request("GET", "/test.txt")
    response = connection.getresponse()
    body = response.read().decode()
    connection.close()
    return body


if __name__ == '__main__':
    app.run()
