package test.java.service;

import model.Transaction;
import org.testng.annotations.Test;
import service.TransactionService;

import static org.testng.AssertJUnit.assertEquals;

public class TransactionServiceTest {
    @Test
    public void testProcessTransactionApproved() {
        TransactionService transactionService = new TransactionService();
        Transaction transaction = new Transaction();
        transaction.setAmount(100.00);
        String response = transactionService.processTransaction(transaction);
        assertEquals("00", response);
    }

    @Test
    public void testProcessTransactionRejected() {
        TransactionService transactionService = new TransactionService();
        Transaction transaction = new Transaction();
        transaction.setAmount(1.00);
        String response = transactionService.processTransaction(transaction);
        assertEquals("51", response);
    }

    @Test
    public void testProcessTransactionError() {
        TransactionService transactionService = new TransactionService();
        Transaction transaction = new Transaction();
        transaction.setMerchant("");
        String response = transactionService.processTransaction(transaction);
        assertEquals("07", response);
    }
}
