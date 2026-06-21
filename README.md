# Simple HTTP Server (Built From Scratch)

A minimal HTTP/1.1 server written in pure Java — no Spring Boot, no servlet container, no frameworks. Built to understand what Tomcat and Spring's `DispatcherServlet` actually do under the hood before relying on them as abstractions.

## Why

Frameworks like Spring Boot hide HTTP parsing, routing, and response formatting behind annotations like `@GetMapping` and `ResponseEntity`. This project rebuilds that pipeline manually using raw TCP sockets, to understand:

- How a server listens for and accepts TCP connections
- How an HTTP request is structured as plain text (request line, headers, blank line, body)
- How routing works when there's no framework to do it for you
- How an HTTP response must be formatted byte-for-byte to be valid
- Why `Content-Length` must exactly match the body size
- The difference between buffered and flushed output streams

## Features

- Parses the HTTP request line (method, path, protocol) and headers into a `Map`
- Routes requests by path:
  - `GET /` → plain text response
  - `GET /hello` → plain text response
  - `GET /user` → JSON object
  - `GET /dishes` → JSON array
  - `GET /orders` → plain text response
  - Any other path → `404 Not Found`
- Encapsulates parsed request data in an `HttpRequest` class and response data in an `HttpResponse` class, mirroring the design of `HttpServletRequest` / `HttpServletResponse` in the Servlet API that Tomcat implements

## Tech

Java (`ServerSocket`, `Socket`, `BufferedReader`, `PrintWriter`) — no external dependencies.

## Run it

\`\`\`bash
javac SimpleHttpServer.java HttpRequest.java HttpResponse.java
java SimpleHttpServer
\`\`\`

In another terminal:

\`\`\`bash
curl http://localhost:8080/
curl http://localhost:8080/user
curl http://localhost:8080/dishes
curl http://localhost:8080/doesnotexist
\`\`\`

## What this maps to in Spring Boot

| This project | Spring Boot equivalent |
|---|---|
| `ServerSocket` + `accept()` loop | Embedded Tomcat |
| Parsing the request line | `DispatcherServlet` |
| `if/else` on path | `@GetMapping("/path")` |
| Manually building JSON strings | Jackson serializing Java objects |
| `HttpRequest` / `HttpResponse` classes | `HttpServletRequest` / `ResponseEntity` |

See also: [campus-food-delivery](https://github.com/EsmeYang/campus-food-delivery) — a full Spring Boot backend built on top of these same concepts.