package ifmo.webservices.errors;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "ifmo.webservices.errors.ArticleServiceFault")
public class ArticleNotFoundException extends Exception {
    private final ArticleServiceFault fault;

    public ArticleNotFoundException(String message, ArticleServiceFault fault) {
        super(message);
        this.fault = fault;
    }

    public ArticleNotFoundException(String message, ArticleServiceFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public ArticleServiceFault getFaultInfo() {
        return fault;
    }
}