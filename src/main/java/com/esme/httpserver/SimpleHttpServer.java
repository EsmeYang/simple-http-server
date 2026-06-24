package com.esme.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.esme.Handler.HandlerFactory;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started on port 8080");
        while (true) {
            Socket clientSocket = serverSocket.accept();//多线程
            new Thread(() -> {
                try {
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
                    // STEP 3: write the response
                    HttpResponse httpResponse = new HttpResponse();
                    BusinessLogic bl= HandlerFactory.getHandler(httpRequest.getPath());
                    bl.service(httpRequest, httpResponse);
                    writer.println(httpResponse.getStatus());
                    writer.println(httpResponse.getContentType());
                    writer.println("Content-Length: " + httpResponse.getMessage().length());
                    writer.println();
                    writer.print(httpResponse.getMessage());
                    writer.flush();
                    // STEP 4: close the connection
                    clientSocket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                }
            }).start();
        }
    }
}