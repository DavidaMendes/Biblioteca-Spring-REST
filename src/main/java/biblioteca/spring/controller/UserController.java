package biblioteca.spring.controller;

import biblioteca.spring.model.Book;
import biblioteca.spring.model.User;
import biblioteca.spring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginUser(){
        return "login";
    }

    @PostMapping("/cadastro")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/biblioteca/meus-livros")
    public ResponseEntity<List<Book>> countMyBooks() {
        List<Book> books = userService.getUserRentedBooks();
        return ResponseEntity.ok().body(books);
    }
}
