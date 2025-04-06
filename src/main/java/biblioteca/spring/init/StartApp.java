package biblioteca.spring.init;

import biblioteca.spring.component.DataExpirationBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartApp implements CommandLineRunner {
    @Autowired
    private DataExpirationBook dataExpirationBook;

    @Override
    public void run(String... args) throws Exception {
        dataExpirationBook.checkExpiration();
    }
}
