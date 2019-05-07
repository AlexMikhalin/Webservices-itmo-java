package ifmo.webservices.errors;

public class ArticleNotFoundException extends Exception {
    public static ArticleNotFoundException DEFAULT_INSTANCE = new
            ArticleNotFoundException("Article with such id was not found");

    public ArticleNotFoundException(String message) {
        super(message);
    }
}