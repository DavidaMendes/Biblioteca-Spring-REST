package biblioteca.spring.controller;

import biblioteca.spring.dto.Login;
import biblioteca.spring.dto.Sessao;
import biblioteca.spring.model.Librarian;
import biblioteca.spring.model.User;
import biblioteca.spring.repository.LibrarianRepository;
import biblioteca.spring.repository.UserRepository;
import biblioteca.spring.security.SecurityConfig;
import biblioteca.spring.security.jwt.JWTCreator;
import biblioteca.spring.security.jwt.JWTObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LibrarianRepository librarianRepository;

    @PostMapping("/login-user")
    public Sessao logar(@RequestBody Login login){
        User user = userRepository.findByUsername(login.getUsername());
        if(user!=null) {
            boolean passwordOk =  encoder.matches(login.getPassword(), user.getPassword());
            if (!passwordOk) {
                throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
            }
            //Estamos enviando um objeto Sessão para retornar mais informações do usuário
            Sessao sessao = new Sessao();
            sessao.setLogin(user.getUsername());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setSubject(user.getUsername());
            jwtObject.setIssueedAT(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(user.getRoles());
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
            return sessao;
        }else {
            throw new RuntimeException("Erro ao tentar fazer login");
        }
    }

    @PostMapping("/login-librarian")
    public Sessao logarManager(@RequestBody Login login){
        Librarian librarian = librarianRepository.findByUsername(login.getUsername());
        if(librarian!=null) {
            boolean passwordOk =  encoder.matches(login.getPassword(), librarian.getPassword());
            if (!passwordOk) {
                throw new RuntimeException("Senha inválida para o login: " + login.getUsername());
            }
            //Estamos enviando um objeto Sessão para retornar mais informações do usuário
            Sessao sessao = new Sessao();
            sessao.setLogin(librarian.getUsername());

            JWTObject jwtObject = new JWTObject();
            jwtObject.setSubject(librarian.getUsername());
            jwtObject.setIssueedAT(new Date(System.currentTimeMillis()));
            jwtObject.setExpiration((new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION)));
            jwtObject.setRoles(librarian.getRoles());
            sessao.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
            return sessao;
        }else {
            throw new RuntimeException("Erro ao tentar fazer login");
        }
    }
}
