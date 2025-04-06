package biblioteca.spring.exception.user;

public class UserNoIDException extends RuntimeException {
    public UserNoIDException(String mensage) {
        super(mensage);
    }
}
