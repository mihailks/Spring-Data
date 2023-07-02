package bg.sofaccounts.services;

import bg.sofaccounts.models.Account;
import bg.sofaccounts.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServicesImpl implements AccountServices {
    private AccountRepository accountRepository;

    public AccountServicesImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Optional<Account> accountOpt = accountRepository.findById(id);

        if (accountOpt.isEmpty()) {
            throw new IllegalArgumentException("Account is missing");
        }

        Account account = accountOpt.get();
        BigDecimal newAmount = account.getBalance().subtract(money);
        account.setBalance(newAmount);

        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Not enough money");
        }

        accountRepository.save(account);
    }

    @Override
    public void depositMoney(BigDecimal money, Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account is missing"));


        if (money.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cannot deposit negative money");
        }

        BigDecimal newBalance = account.getBalance().add(money);
        account.setBalance(newBalance);

        accountRepository.save(account);

    }
}
