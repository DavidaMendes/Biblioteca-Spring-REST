package biblioteca.spring.component;

import biblioteca.spring.model.Book;
import biblioteca.spring.model.User;
import biblioteca.spring.repository.BookRepository;
import biblioteca.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserBookRental {
    private static final Logger logger = LoggerFactory.getLogger(UserBookRental.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Obtém o usuário autenticado na sessão atual
     * @return User - usuário autenticado
     */
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            logger.error("Nenhuma autenticação encontrada no contexto de segurança");
            throw new RuntimeException("Usuário não autenticado");
        }

        String username = authentication.getName();
        logger.info("Tentando obter usuário autenticado: {}", username);

        if (username.equals("anonymousUser")) {
            logger.error("Usuário anônimo detectado, não é possível obter usuário autenticado");
            throw new RuntimeException("Usuário não autenticado");
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            logger.error("Usuário não encontrado no banco de dados: {}", username);
            throw new RuntimeException("Usuário não encontrado: " + username);
        }

        logger.info("Usuário autenticado encontrado: {}", user.getUsername());
        return user;
    }

    /**
     * Associa um livro ao usuário autenticado
     * @param book - livro a ser associado
     * @return Book - livro com usuário associado
     */
    public Book associateBookToUser(Book book) {
        try {
            User authenticatedUser = getAuthenticatedUser();
            logger.info("Associando livro ID {} ao usuário {}", book.getId(), authenticatedUser.getUsername());

            book.setUserRent(authenticatedUser);

            if (authenticatedUser.getListBooksRent() == null) {
                authenticatedUser.setListBooksRent(new ArrayList<>());
            }

            boolean bookAlreadyInList = false;
            for (Book rentedBook : authenticatedUser.getListBooksRent()) {
                if (rentedBook.getId().equals(book.getId())) {
                    bookAlreadyInList = true;
                    break;
                }
            }

            if (!bookAlreadyInList) {
                authenticatedUser.getListBooksRent().add(book);
            }

            userRepository.save(authenticatedUser);
            Book savedBook = bookRepository.save(book);
            logger.info("Livro associado com sucesso ao usuário");
            return savedBook;
        } catch (Exception e) {
            logger.error("Erro ao associar livro ao usuário: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao associar livro ao usuário: " + e.getMessage());
        }
    }

    public int countUserRentedBooks() {
        User authenticatedUser = getAuthenticatedUser();
        List<Book> userBooks = authenticatedUser.getListBooksRent();
        return userBooks != null ? userBooks.size() : 0;
    }

    public List<Book> getUserRentedBooks() {
        User authenticatedUser = getAuthenticatedUser();
        return authenticatedUser.getListBooksRent() != null ?
                authenticatedUser.getListBooksRent() :
                new ArrayList<>();
    }
}