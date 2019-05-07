package ifmo.webservices.errors;

public class IllegalYearException extends Exception {
    public static IllegalYearException DEFAULT_INSTANCE = new
            IllegalYearException("Article year cannot be null or negative");

    public IllegalYearException(String message) {
        super(message);
    }
}