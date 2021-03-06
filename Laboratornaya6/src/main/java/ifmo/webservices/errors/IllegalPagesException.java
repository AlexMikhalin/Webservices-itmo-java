package ifmo.webservices.errors;

public class IllegalPagesException extends Exception {
    public static IllegalPagesException DEFAULT_INSTANCE = new
            IllegalPagesException("Article pages cannot be null or negative");

    public IllegalPagesException(String message) {
        super(message);
    }
}