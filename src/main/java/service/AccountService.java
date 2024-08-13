package service;

import model.Account;
import repository.AccountRepository;
import java.util.List;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    public int createAccount(Account account) {
        return accountRepository.createAccount(account);
    }

    public Account getAccount(String accountId) {
        return accountRepository.getAccount(accountId);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public int updateAccount(Account account) {
        return accountRepository.updateAccount(account);
    }

    public int deleteAccount(String accountId) {
        return accountRepository.deleteAccount(accountId);
    }
}
