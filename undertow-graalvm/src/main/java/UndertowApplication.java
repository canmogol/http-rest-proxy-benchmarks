import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UndertowApplication implements HttpHandler {
    private final HttpClient httpClient = HttpClient
            .newBuilder()
            .build();

    public static void main(String[] args) {
        final UndertowApplication app = new UndertowApplication();
        app.start();
    }

    private void start() {
        final Undertow server = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(this)
                .build();
        server.start();
        log("Server started.");
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        final HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(URI.create("http://192.168.68.118:9090/test.txt")).build();
        final HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send(httpResponse.body().replace("\n", ""));
        exchange.getResponseSender().close();
    }

    private static void log(String message) {
        System.out.println(message);
    }

}
