package biblioteca.spring.exception.book;

public class BookRentTrueException extends NullPointerException {
    public BookRentTrueException(String message) {
        super(message);
    }
}
