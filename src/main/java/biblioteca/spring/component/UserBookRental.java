package biblioteca.spring.component;

import biblioteca.spring.model.Book;
import biblioteca.spring.model.User;
import biblioteca.spring.repository.BookRepository;
import biblioteca.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserBookRental {

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
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        return user;
    }

    /**
     * Associa um livro ao usuário autenticado
     * @param book - livro a ser associado
     * @return Book - livro com usuário associado
     */
    public Book associateBookToUser(Book book) {
        User authenticatedUser = getAuthenticatedUser();

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
        return bookRepository.save(book);
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