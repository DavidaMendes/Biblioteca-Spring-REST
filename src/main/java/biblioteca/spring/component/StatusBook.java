package biblioteca.spring.component;

import biblioteca.spring.model.Book;
import biblioteca.spring.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusBook {
    @Autowired
    private BookRepository bookRepository;

    public boolean tradeStatusBook(Long id){
        Book book = bookRepository.findById(id).get();
        book.setStatusRent(!book.getStatusRent());
        return book.getStatusRent();
    }

}
