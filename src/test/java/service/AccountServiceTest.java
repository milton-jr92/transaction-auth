package test.java.service;

import model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.AccountRepository;
import service.AccountService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountService(accountRepository);
    }

    @Test
    public void testCreateAccount() {
        Account account = new Account();
        when(accountRepository.createAccount(account)).thenReturn(1);

        int result = accountService.createAccount(account);

        assertEquals(1, result);
    }

    @Test
    public void testGetAccount() {
        Account account = new Account();
        when(accountRepository.getAccount("12345")).thenReturn(account);

        Account result = accountService.getAccount("12345");

        assertEquals(account, result);
        verify(accountRepository, times(1)).getAccount("12345");
    }

    @Test
    public void testGetAllAccounts() {
        List<Account> accounts = Arrays.asList(new Account(), new Account());
        when(accountRepository.getAllAccounts()).thenReturn(accounts);

        List<Account> result = accountService.getAllAccounts();

        assertEquals(accounts, result);
        verify(accountRepository, times(1)).getAllAccounts();
    }

    @Test
    public void testUpdateAccount() {
        Account account = new Account();
        when(accountRepository.updateAccount(account)).thenReturn(1);

        int result = accountService.updateAccount(account);

        assertEquals(1, result);
        verify(accountRepository, times(1)).updateAccount(account);
    }

    @Test
    public void testDeleteAccount() {
        when(accountRepository.deleteAccount("12345")).thenReturn(1);

        int result = accountService.deleteAccount("12345");

        assertEquals(1, result);
        verify(accountRepository, times(1)).deleteAccount("12345");
    }
}