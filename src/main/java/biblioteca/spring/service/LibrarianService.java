package biblioteca.spring.service;

import biblioteca.spring.exception.user.UserNoIDException;
import biblioteca.spring.exception.user.UserNullException;
import biblioteca.spring.model.Librarian;
import biblioteca.spring.repository.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LibrarianService {

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void validateLibrarian(Librarian librarian) {
        if (librarian.getCpf() == null || librarian.getCpf().length() != 11) {
            throw new UserNullException("Verifique o cpf");
        }
        if (librarian.getPassword() == null || librarian.getPassword().length() > 100) {
            throw new UserNullException("Verifique o password");
        }
        if (librarian.getEmail() == null || librarian.getEmail().length() > 50) {
            throw new UserNullException("Verifique o email");
        }
        if (librarian.getUsername() == null || librarian.getUsername().length() > 50) {
            throw new UserNullException("Verifique o name");
        }
        if (librarian.getInstitution() == null || librarian.getInstitution().length() > 30) {
            throw new UserNullException("Verifique a instituição");
        }
    }

    public void createLibrarian(Librarian librarian) throws UserNullException {
        validateLibrarian(librarian);

        String encryptedPassword = passwordEncoder.encode(librarian.getPassword());
        librarian.setPassword(encryptedPassword);

        librarianRepository.save(librarian);
    }

    public void removeLibrarian(Librarian librarian) throws UserNoIDException {
        if (librarian == null) {
            throw new UserNoIDException("Librarian " + librarian.getId() + " não encontrado");
        }
        librarianRepository.delete(librarian);
    }
}