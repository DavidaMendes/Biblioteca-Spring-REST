package biblioteca.spring.repository;

import biblioteca.spring.model.Librarian;
import biblioteca.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
    @Query("SELECT e FROM Librarian e JOIN FETCH e.roles WHERE e.username= (:username)")
    public Librarian findByUsername(@Param("username") String username);
}
