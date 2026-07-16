package com.esme.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.esme.factory.HandlerFactory;

public class SimpleHttpServer {
    private static final Logger logger = LogManager.getLogger(SimpleHttpServer.class);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        logger.info("Server started on port 8080");
        /**ThreadPoolExecutor threadPool =  new ThreadPoolExecutor(
            3,                    // corePoolSize
            3,                    // maximumPoolSize
            0L,                    // keepAliveTime
            TimeUnit.MILLISECONDS, // time unit
            new LinkedBlockingQueue<>() // queue
        );**/
        //自己实现thread pool
        MyThreadPool threadPool = new MyThreadPool(3);

        //控制线程数
        while (true) {
            Socket clientSocket = serverSocket.accept();//多线程
            threadPool.submit(() -> {
                try {
                    logger.info("线程启动: {}", Thread.currentThread().getName());
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

                    String body;
                    if (headers.containsKey("Content-Length")) {
                        int contentLength = Integer.parseInt(headers.get("Content-Length"));
                        char[] bodyChars = new char[contentLength];
                        reader.read(bodyChars, 0, contentLength);
                        body = new String(bodyChars);
                    } else {
                        body = "";
                    }
                    httpRequest.setBody(body);
                    // STEP 3: write the response
                    HttpResponse httpResponse = new HttpResponse();

                    // 2. Loop through all methods, find the one with @ServiceMethod
                    HandlerFactory.invoke(httpRequest, httpResponse);
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
            });
        }
        
    }
}