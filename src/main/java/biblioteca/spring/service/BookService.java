package biblioteca.spring.service;

import biblioteca.spring.component.DataExpirationBook;
import biblioteca.spring.component.StatusBook;
import biblioteca.spring.component.UserBookRental;
import biblioteca.spring.exception.book.BookListNullException;
import biblioteca.spring.exception.book.BookNoIDException;
import biblioteca.spring.exception.book.BookNullException;
import biblioteca.spring.exception.book.BookRentTrueException;
import biblioteca.spring.exception.user.UserNullException;
import biblioteca.spring.model.Book;
import biblioteca.spring.model.User;
import biblioteca.spring.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StatusBook statusBook;

    @Autowired
    private DataExpirationBook dataExpirationBook;

    @Autowired
    private UserBookRental userBookRental;

    private void validadeBook(Book book) {
        LocalDate date = LocalDate.now();

        if (book.getTitle() == null || book.getTitle().length() > 100) {
            throw new BookNullException("Verifique o nome do titulo");
        }
        if (book.getAuthor() == null || book.getAuthor().length() > 50) {
            throw new BookNullException("Verifique o nome do author");
        }
        if (book.getPublisher() == null || book.getPublisher().length() > 50) {
            throw new BookNullException("Verifique o nome do publisher");
        }
        if (book.getpublicationYear() == 0 || book.getpublicationYear() > date.getYear()) {
            throw new BookNullException("Verifique o ano de publication");
        }
        if (book.getSinopse() == null || book.getSinopse().length() > 200) {
            throw new BookNullException("Verifique a sinopse");
        }

    }

    public List<Book> listBooks () throws BookListNullException {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()) {
            throw new BookListNullException("Nenhum livro encontrado");
        }
        return books;
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNoIDException("Livro com ID " + id + " não encontrado."));
    }


    public void createBook(Book book) throws BookNullException {
        validadeBook(book);
        bookRepository.save(book);
    }

    public void rentBook(Long id) throws BookNoIDException, BookRentTrueException {
        Book book = findBookById(id);
        if(book.getStatusRent()){
            throw new BookRentTrueException("Livro já alugado");
        }
        if(book!=null && !book.getStatusRent() && book.getDataExpiration() == null){
            book.setStatusRent(statusBook.tradeStatusBook(id));
            book.setDataExpiration(dataExpirationBook.defineDataExpiration(id));
            userBookRental.associateBookToUser(book);
        }
    }


}
