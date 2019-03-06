import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.codecool.webrouteannotation.site.Routes;
import com.codecool.webrouteannotation.site.WebRoute;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class Test {

    public static void main(String[] args) throws Exception {

        Map<String, String> routes = new HashMap<String, String>();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        for (Method m : Routes.class.getMethods()) {
            if (m.isAnnotationPresent(WebRoute.class)) {
            /*
            Here comes your logic.
            If the given path from the HttpExchange method is the SAME like the WebRoute annotation's path, you should INVOKE this method.
            */
                String route = m.getAnnotation(WebRoute.class).path();
                String response = (String) m.invoke(new Routes());
                    server.createContext(route, new MyHandler(response));
                routes.put(route, response);

                System.out.println(routes);
            }
        }


        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        private String response;

        public MyHandler(String response) {
            this.response = response;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}