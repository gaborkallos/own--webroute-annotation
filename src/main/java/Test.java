import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import com.codecool.webrouteannotation.site.Routes;
import com.codecool.webrouteannotation.site.WebRoute;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = null;
            int statusCode = 200;
            for (Method m : Routes.class.getMethods()) {
                if (m.isAnnotationPresent(WebRoute.class)) {
                    String path = m.getAnnotation(WebRoute.class).path();
                    if (path.equals(httpExchange.getRequestURI().getPath())) {
                        try {
                            String method = String.valueOf(m.getAnnotation(WebRoute.class).method());
                            if(httpExchange.getRequestMethod().equals(method)) {
                                response = (String) m.invoke(new Routes());
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            statusCode = 500;
                            response = "Internal server error";
                        }
                    }
                }
            }
            if(response == null){
                statusCode = 404;
                response = "Page not found";
            }

            httpExchange.sendResponseHeaders(statusCode, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
