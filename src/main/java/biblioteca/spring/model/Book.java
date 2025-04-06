package biblioteca.spring.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Calendar;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 50, nullable = false)
    private String author;
    @Column(length = 50, nullable = false)
    private String publisher;
    @Column(length = 4, nullable = false)
    private int publicationYear;
    @Column(length = 200, nullable = false)
    private String sinopse;
    private boolean statusRent;
    private LocalDate dataExpiration;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User userRent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getpublicationYear() {
        return publicationYear;
    }

    public void setYpublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public boolean isStatusRent() {
        return statusRent;
    }

    public void setStatusRent(boolean statusRent) {
        this.statusRent = statusRent;
    }

    public boolean getStatusRent() {
        return statusRent;
    }

    public LocalDate getDataExpiration() {
        return dataExpiration;
    }

    public void setDataExpiration(LocalDate dataExpiration) {
        this.dataExpiration = dataExpiration;
    }

//    public User getUserRent() {
//        return userRent;
//    }
//
//    public void setUserRent(User userRent) {
//        this.userRent = userRent;
//    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year=" + publicationYear +
                ", sinopse='" + sinopse + '\'' +
                ", statusRent=" + statusRent +
                ", dataExpiration=" + dataExpiration +
                '}';
    }
}
