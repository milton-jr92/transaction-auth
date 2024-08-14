package test.java.service;

import model.Account;
import model.Transaction;
import model.MccType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.AccountRepository;
import service.TransactionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessTransactionApproved() {
        Transaction transaction = new Transaction();
        transaction.setAccountId("12345");
        transaction.setAmount(20.00);
        transaction.setMcc("5811");
        transaction.setMerchant("PADARIA DO ZE               SAO PAULO BR");

        Account account = new Account();
        account.setAccountId("12345");
        account.setFoodBalance(100.00);
        account.setMealBalance(100.00);
        account.setCashBalance(100.00);
        account.setTotalBalance(300.00);

        when(accountRepository.getAccount(transaction.getAccountId())).thenReturn(account);
        double expectedBalance = account.getMealBalance() - transaction.getAmount();

        String response = transactionService.processTransaction(transaction);

        assertEquals(expectedBalance, account.getMealBalance());
        assertEquals("00", response);

        verify(accountRepository, times(1)).getAccount(transaction.getAccountId());
        verify(accountRepository, times(1)).updateAccount(account);
    }

    @Test
    public void testProcessTransactionRejected() {
        Transaction transaction = new Transaction();
        transaction.setAccountId("12345");
        transaction.setAmount(60.00);
        transaction.setMcc("5811");
        transaction.setMerchant("PADARIA DO ZE               SAO PAULO BR");

        Account account = new Account();
        account.setAccountId("12345");
        account.setFoodBalance(100.00);
        account.setMealBalance(50.00);
        account.setCashBalance(30.00);
        account.setTotalBalance(300.00);

        when(accountRepository.getAccount(transaction.getAccountId())).thenReturn(account);

        String response = transactionService.processTransaction(transaction);

        assertEquals("51", response);

        verify(accountRepository, times(1)).getAccount(transaction.getAccountId());
        verify(accountRepository, times(0)).updateAccount(account);
    }

    @Test
    public void testProcessTransactionError() {
        Transaction transaction = new Transaction();
        transaction.setAccountId("12345");
        transaction.setAmount(60.00);
        transaction.setMcc("5811");
        transaction.setMerchant("PADARIA DO ZE               SAO PAULO BR");

        when(accountRepository.getAccount(transaction.getAccountId())).thenThrow(new RuntimeException());

        String response = transactionService.processTransaction(transaction);

        assertEquals("07", response);

        verify(accountRepository, times(1)).getAccount(transaction.getAccountId());
        verify(accountRepository, times(0)).updateAccount(any());
    }
}
