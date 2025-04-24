package biblioteca.spring.security;

import biblioteca.spring.security.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private SecurityDatabaseService securityService;

    @Autowired
    private ConfigEncoder encoder;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(encoder.passwordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                )
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // Habilita CORS sem configurações específicas
                .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login-user","/login-librarian", "/cadastro").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/biblioteca/livros").hasAnyRole("USERS", "MANAGERS")
                        .requestMatchers("/biblioteca/alugar-livro/**").hasRole("USERS")
                        .requestMatchers("/biblioteca/meus-livros").hasRole("USERS")
                        .requestMatchers("/biblioteca/adicionar-livro").hasRole("MANAGERS")
                        .requestMatchers("/biblioteca/**").hasAnyRole("USERS", "MANAGERS")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                //.httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
