package service;

import model.Transaction;

public class TransactionService {
    public String processTransaction(Transaction transaction) {
        if (transaction.getAmount() >= 10.00) {
            return "00";
        } else if (transaction.getAmount() < 10.00) {
            return "51";
        } else {
            return "07";
        }
    }
}
