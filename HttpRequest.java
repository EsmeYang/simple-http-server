import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    private String protocol;
    private Map<String, String> headers;

    public String getMethod() { return method; }
    public String getPath() { return path; }
    public String getProtocol() { return protocol; }
    public Map<String, String> getHeaders() { return headers; }
    public HttpRequest(String method, String path, String protocol, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.headers = headers;
    }
}