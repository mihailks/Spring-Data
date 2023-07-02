package bg.sofaccounts.services;

import bg.sofaccounts.repositories.AccountRepository;

import java.math.BigDecimal;

public interface AccountServices {



    void withdrawMoney(BigDecimal money, Long id);
    void depositMoney(BigDecimal money, Long id);


}
