package biblioteca.spring.exception.book;

public class BookListNullException extends RuntimeException {
    public BookListNullException(String message) {
        super(message);
    }
}
