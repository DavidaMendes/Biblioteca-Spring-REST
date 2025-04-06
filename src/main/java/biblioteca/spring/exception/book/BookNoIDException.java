package biblioteca.spring.exception.book;

public class BookNoIDException extends RuntimeException {
    public BookNoIDException(String message) {
        super(message);
    }

}
