package biblioteca.spring.component;

import biblioteca.spring.model.Book;
import biblioteca.spring.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataExpirationBook {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StatusBook statusBook;

    private LocalDate generateDataExpiration() {
        return LocalDate.now().plusDays(60);
    }

    public LocalDate defineDataExpiration(long id) {
        Book book = bookRepository.findById(id).get();
        LocalDate expirationDate = generateDataExpiration();
        book.setDataExpiration(expirationDate);
        bookRepository.save(book);
        return expirationDate;
    }

    public void checkExpiration() {
        LocalDate today = LocalDate.now();

        bookRepository.findAll().forEach(book -> {
            if (book.getDataExpiration() != null
                    && book.getDataExpiration().isBefore(today)
                    && book.getStatusRent()) {
                book.setStatusRent(false);
                book.setDataExpiration(null);
                bookRepository.save(book);
            }
        });
    }
}
