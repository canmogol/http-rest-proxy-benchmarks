import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JettyApplication {

    private final HttpClient httpClient = HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build();

    public static void main(String[] args) throws Exception {
        final JettyApplication application = new JettyApplication();
        application.start();
    }

    private void start() throws Exception {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                0,
                Integer.MAX_VALUE,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()
        );
        final ThreadFactory threadFactory = Thread.ofVirtual().factory();
        threadPoolExecutor.setThreadFactory(threadFactory);
        final ExecutorThreadPool threadPool = new ExecutorThreadPool(threadPoolExecutor);
        final Server server = new Server(threadPool);
        final ServerConnector connector = new ServerConnector(server);
        connector.setHost("0.0.0.0");
        connector.setPort(8080);
        server.setConnectors(new Connector[]{connector});
        server.setHandler(new DefaultHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                baseRequest.setHandled(true);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType(MimeTypes.Type.TEXT_HTML_UTF_8.toString());
                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                     OutputStreamWriter writer = new OutputStreamWriter(outputStream, UTF_8)) {
                    final HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(URI.create("http://192.168.68.118:9090/test.txt")).build();
                    final HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    String body = httpResponse.body();
                    writer.append(body);
                    writer.flush();
                    byte[] content = outputStream.toByteArray();
                    response.setContentLength(content.length);
                    try (OutputStream out = response.getOutputStream()) {
                        out.write(content);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
        server.start();
    }

}
