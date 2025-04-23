package biblioteca.spring.init;

import biblioteca.spring.component.DataExpirationBook;
import biblioteca.spring.model.Book;
import biblioteca.spring.model.Librarian;
import biblioteca.spring.model.User;
import biblioteca.spring.repository.LibrarianRepository;
import biblioteca.spring.repository.UserRepository;
import biblioteca.spring.service.BookService;
import biblioteca.spring.service.LibrarianService;
import biblioteca.spring.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartApp implements CommandLineRunner {
    @Autowired
    private DataExpirationBook dataExpirationBook;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private LibrarianService librarianService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Book book = new Book();
        book.setTitle("book title");
        book.setAuthor("book author");
        book.setPublisher("book publisher");
        book.setYpublicationYear(1999);
        book.setSinopse("dasdasd");
        bookService.createBook(book);

        dataExpirationBook.checkExpiration();
        Librarian librarianDefault = librarianRepository.findByUsername("librarian");
        if (librarianDefault == null) {
            librarianDefault = new Librarian();
            librarianDefault.setUsername("librarian");
            librarianDefault.setPassword("master123");
            librarianDefault.setEmail("admin@biblioteca.com");
            librarianDefault.setCpf("00000000000");
            librarianDefault.setInstitution("Biblioteca Central");
            librarianDefault.getRoles().add("MANAGERS");
            librarianService.createLibrarian(librarianDefault);
        }
        User userDefault = userRepository.findByUsername("user");
        if (userDefault == null) {
            userDefault = new User();
            userDefault.setUsername("user");
            userDefault.setPassword("user123");
            userDefault.setEmail("user@biblioteca.com");
            userDefault.setCpf("00000000000");
            userDefault.setPhone("81999999999");
            userDefault.getRoles().add("USERS");
            userService.createUser(userDefault);
        }
    }


}
