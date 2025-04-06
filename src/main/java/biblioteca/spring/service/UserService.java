package biblioteca.spring.service;

import biblioteca.spring.exception.user.UserNoIDException;
import biblioteca.spring.exception.user.UserNullException;
import biblioteca.spring.model.User;
import biblioteca.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private void validateUser(User user) {
        if (user.getCpf() == null || user.getCpf().length() != 11) {
            throw new UserNullException("Verifique o cpf");
        }
        if (user.getPhone() == null || user.getPhone().length() != 11) {
            throw new UserNullException("Verifique o phone");
        }
        if (user.getPassword() == null || user.getPassword().length() > 100) {
            throw new UserNullException("Verifique o password");
        }
        if (user.getEmail() == null || user.getEmail().length() > 50) {
            throw new UserNullException("Verifique o email");
        }
        if (user.getName() == null || user.getName().length() > 50) {
            throw new UserNullException("Verifique o name");
        }
    }


    public void createUser(User user) throws UserNullException {
        validateUser(user);
        userRepository.save(user);
    }

    public void removeUser(User user) throws UserNoIDException{
        if(user == null){
            throw new UserNoIDException("Usuário " + user.getId() + " não encontrado");
        }
        userRepository.delete(user);
    }

}
