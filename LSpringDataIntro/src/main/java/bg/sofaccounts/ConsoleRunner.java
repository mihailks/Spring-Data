package bg.sofaccounts;

import bg.sofaccounts.models.User;
import bg.sofaccounts.services.AccountServices;
import bg.sofaccounts.services.UserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private AccountServices accountServices;
    private UserServices userServices;

    public ConsoleRunner(AccountServices accountServices, UserServices userServices) {
        this.accountServices = accountServices;
        this.userServices = userServices;
    }

    @Override
    public void run(String... args) throws Exception {
//        User someUser = new User("SomeUser", 20);
//        userServices.register(someUser);

//        accountServices.depositMoney(BigDecimal.TEN, 1L);
        accountServices.withdrawMoney(BigDecimal.valueOf(20), 1L);


    }
}
