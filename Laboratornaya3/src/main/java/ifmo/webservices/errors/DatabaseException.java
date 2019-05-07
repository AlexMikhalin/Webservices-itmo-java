package ifmo.webservices.errors;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "ifmo.webservices.errors.ArticleServiceFault")
public class DatabaseException extends Exception {
    private final ArticleServiceFault fault;

    public DatabaseException(String message, ArticleServiceFault fault) {
        super(message);
        this.fault = fault;
    }

    public DatabaseException(String message, ArticleServiceFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public ArticleServiceFault getFaultInfo() {
        return fault;
    }
}