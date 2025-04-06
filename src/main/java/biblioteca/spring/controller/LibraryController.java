package biblioteca.spring.controller;

import biblioteca.spring.model.Book;
import biblioteca.spring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblioteca")
public class LibraryController {
    @Autowired
    private BookService bookService;

    @GetMapping("/livros")
    public List<Book> listBooks(){
        return bookService.listBooks();
    }

    @PostMapping("/alugar-livro/{id}")
    public ResponseEntity<Book> rentBook(@PathVariable Long id){
        bookService.rentBook(id);
        Book book = bookService.findBookById(id);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Book> searchBook(@PathVariable Long id){
        Book book = bookService.findBookById(id);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("/adicionar-livro")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        bookService.createBook(book);
        return ResponseEntity.ok().body(book);
    }


}
