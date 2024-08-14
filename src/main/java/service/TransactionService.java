package service;

import model.Account;
import model.MccType;
import model.Transaction;
import repository.AccountRepository;

public class TransactionService {

    private final AccountRepository accountRepository;
    private final MerchantMccMapper merchantMccMapper;

    public TransactionService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.merchantMccMapper = new MerchantMccMapper();
    }

    public String processTransaction(Transaction transaction) {
        try {
            Account account = accountRepository.getAccount(transaction.getAccountId());

            MccType mccType = merchantMccMapper.getMccType(transaction.getMerchant(), transaction.getMcc());
            double newBalance;

            switch (mccType) {
                case FOOD:
                    if (account.getFoodBalance() >= transaction.getAmount()) {
                        newBalance = account.getFoodBalance() - transaction.getAmount();
                        account.setFoodBalance(newBalance);
                    } else if (account.getCashBalance() >= transaction.getAmount()) {
                        newBalance = account.getCashBalance() - transaction.getAmount();
                        account.setMealBalance(newBalance);
                    } else {
                        return "51";
                    }
                    break;
                case MEAL:
                    if (account.getMealBalance() >= transaction.getAmount()) {
                        newBalance = account.getMealBalance() - transaction.getAmount();
                        account.setMealBalance(newBalance);
                    } else if (account.getCashBalance() >= transaction.getAmount()) {
                        newBalance = account.getCashBalance() - transaction.getAmount();
                        account.setFoodBalance(newBalance);
                    } else {
                        return "51";
                    }
                    break;
                default:
                    if (account.getCashBalance() >= transaction.getAmount()) {
                        newBalance = account.getCashBalance() - transaction.getAmount();
                        account.setCashBalance(newBalance);
                    } else {
                        return "51";
                    }
                    break;
            }

            account.setTotalBalance(account.getTotalBalance() - transaction.getAmount());
            accountRepository.updateAccount(account);
            return "00";
        } catch (Exception e) {
            return "07";
        }
    }
}
