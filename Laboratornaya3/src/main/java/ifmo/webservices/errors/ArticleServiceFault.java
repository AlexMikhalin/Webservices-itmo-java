package ifmo.webservices.errors;

public class ArticleServiceFault {
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ArticleServiceFault defaultInstance() {
        ArticleServiceFault fault = new ArticleServiceFault();
        return fault;
    }
}