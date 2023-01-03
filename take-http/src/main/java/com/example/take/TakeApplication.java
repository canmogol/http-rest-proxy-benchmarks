package com.example.take;

import org.takes.Take;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.rs.RsText;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

public class TakeApplication {

    private final HttpClient httpClient = HttpClient
            .newBuilder()
            .executor(Executors.newVirtualThreadPerTaskExecutor())
            .build();
    private final HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(URI.create("http://192.168.68.118:9090/test.txt")).build();
    private int port = 8080;

    public static void main(final String[] args) throws IOException {
        new TakeApplication().start();
    }

    private void start() throws IOException {
        System.out.println("Application will start on port " + port);
        new FtParallel(
                new TkFork(new FkRegex("/", (Take) req -> {
                    final HttpResponse<InputStream> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
                    final InputStream inputStream = httpResponse.body();
                    return new RsText(inputStream);
                })), port
        ).start(Exit.NEVER);
    }

}
