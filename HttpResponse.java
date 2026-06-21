public class HttpResponse{
    private String message;
    private String contentType;
    private String status;

    public String getMessage() { return message; }
    public String getContentType() { return contentType; }
    public String getStatus() { return status; }
    public HttpResponse(String message, String contentType, String status) {
        this.message = message;
        this.contentType = contentType;
        this.status = status;
    }
}