import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started on port 8080");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            // STEP 1: read the request line
            String requestLine = reader.readLine();
            String[] parts = requestLine.split(" ");
            Map<String, String> headers = new HashMap<>();
            // STEP 2: read headers until empty line
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] headerParts = line.split(": ");
                headers.put(headerParts[0], headerParts[1]);
            }
            HttpRequest httpRequest= new HttpRequest(parts[0], parts[1], parts[2], headers);

            //println -> logforj
            //封装httpRequest, httpResponse

            // STEP 3: write the response
            String message;
            String status;
            String contentType;
            if (httpRequest.getPath().equals("/")) {
                message = "Hello World";
                status = "HTTP/1.1 200 OK";
                contentType = "Content-Type: text/plain";
            } else if(httpRequest.getPath().equals("/hello")) {
                message = "Hello Servlet";
                status = "HTTP/1.1 200 OK";
                contentType = "Content-Type: text/plain";
            } else if(httpRequest.getPath().equals("/user")) {
                message = "{\"id\":1,\"name\":\"Esme\",\"email\":\"esme@example.com\"}";
                status = "HTTP/1.1 200 OK";
                contentType = "Content-Type: application/json";
            } else if(httpRequest.getPath().equals("/dishes")) {
                message = "[{\"id\":1,\"name\":\"pizza\",\"price\":28.00},{\"id\":2,\"name\":\"hamburger\",\"price\":15.00}]";
                status = "HTTP/1.1 200 OK";
                contentType = "Content-Type: application/json";
            } else if(httpRequest.getPath().equals("/orders")) {
                message = "Orders list here";
                status = "HTTP/1.1 200 OK";
                contentType = "Content-Type: text/plain";
            } else {
                message = "404 Not Found";
                status = "HTTP/1.1 404 Not Found";
                contentType = "Content-Type: text/plain";
            }
            HttpResponse httpResponse = new HttpResponse(message, contentType, status);
            writer.println(httpResponse.getStatus());
            writer.println(httpResponse.getContentType());
            writer.println("Content-Length: " + httpResponse.getMessage().length());
            writer.println();
            writer.print(httpResponse.getMessage());
            writer.flush();
            // STEP 4: close the connection
            clientSocket.close();
        }
    }
}