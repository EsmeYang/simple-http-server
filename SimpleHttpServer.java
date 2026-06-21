import java.io.*;
import java.net.*;
public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started on port 8080");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            // STEP 1: read the request line

            //println -> logforj
            //封装httpRequest, httpResponse

            String requestLine = reader.readLine();
            System.out.println("Request: " + requestLine);
            // STEP 2: read headers until empty line
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                System.out.println("Header: " + line);
            }
            // STEP 3: write the response
            String message;
            String status;
            String contentType;
            String[] parts = requestLine.split(" ");
            //
            if(parts[1].equals("/")){
                message = "Hello World";
                status = "HTTP/1.1 200 OK";
                contentType = "Content-Type: text/plain";
            }else if(parts[1].equals("/hello")){
                message = "Hello Servlet";
                status = "HTTP/1.1 200 OK";
                contentType = "Content-Type: text/plain";
            }else if(parts[1].equals("/user")){
                message = "{\"id\":1,\"name\":\"Esme\",\"email\":\"esme@example.com\"}";
                status = "HTTP/1.1 200 OK";
                contentType = "Content-Type: application/json";
            }else if(parts[1].equals("/dishes")){
                message = "Dish list here";
                status = "HTTP/1.1 200 OK";
                contentType = "Content-Type: text/plain";
            }else if (parts[1].equals("/orders")) {
                message = "Orders list here";
                status = "HTTP/1.1 200 OK";
                contentType = "Content-Type: text/plain";
            }else{
                message = "404 Not Found";
                status = "HTTP/1.1 404 Not Found";
                contentType = "Content-Type: text/plain";
            }
            writer.println(status);
            writer.println(contentType);
            writer.println("Content-Length: " + message.length());
            writer.println();
            writer.print(message);
            writer.flush();
            // STEP 4: close the connection
            clientSocket.close();
        }
    }
}