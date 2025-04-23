package biblioteca.spring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import biblioteca.spring.model.Book;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String username;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 100, nullable = false)
    private String password;
    @Column(length = 11, nullable = false)
    private String cpf;
    @Column(length = 11, nullable = false)
    private String phone;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "table_user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_id")
    private List<String> roles = new ArrayList<>();
    @OneToMany(mappedBy = "userRent")
    @JsonManagedReference
    private List<Book> listBooksRent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<Book> getListBooksRent() {
        return listBooksRent;
    }

    public void setListBooksRent(List<Book> listBooksRent) {
        this.listBooksRent = listBooksRent;
    }

    public List<String> getBookTitles() {
        List<String> titles = new ArrayList<>();
        if (listBooksRent != null) {
            for (Book book : listBooksRent) {
                titles.add(book.getTitle());
            }
        }
        return titles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cpf='" + cpf + '\'' +
                ", phone='" + phone + '\'' +
                ", roles=" + roles +
                ", bookCount=" + (listBooksRent != null ? listBooksRent.size() : 0) +
                '}';
    }

}
