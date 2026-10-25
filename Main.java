import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/health", ex -> send(ex, "{\"status\":\"ok\"}"));

        server.createContext("/products", ex -> send(ex,
            "[{\"id\":1,\"name\":\"T-shirt\",\"price\":990}," +
            "{\"id\":2,\"name\":\"Mug\",\"price\":450}," +
            "{\"id\":3,\"name\":\"Notebook\",\"price\":300}]"));

        server.start();
        System.out.println("Mini-shop API started on port 8080");
    }

    static void send(HttpExchange ex, String body) throws java.io.IOException {
        System.out.println(ex.getRequestMethod() + " " + ex.getRequestURI());
        byte[] bytes = body.getBytes();
        ex.getResponseHeaders().add("Content-Type", "application/json");
        ex.sendResponseHeaders(200, bytes.length);
        OutputStream os = ex.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
