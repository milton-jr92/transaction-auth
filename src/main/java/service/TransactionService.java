package service;

import model.Account;
import model.MccType;
import model.Transaction;
import repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;

public class TransactionService {

    private final AccountRepository accountRepository;
    private final Map<String, MccType> merchantMccMap;

    public TransactionService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.merchantMccMap = new HashMap<>();
        initializeMerchantMccMap();
    }

    private void initializeMerchantMccMap() {
        merchantMccMap.put("UBER TRIP                   SAO PAULO BR", MccType.CASH);
        merchantMccMap.put("PICPAY*BILHETEUNICO           GOIANIA BR", MccType.CASH);
        merchantMccMap.put("UBER EATS                   SAO PAULO BR", MccType.MEAL);
        merchantMccMap.put("PAG*JoseDaSilva          RIO DE JANEI BR", MccType.FOOD);
    }

    private MccType getMccType(Transaction transaction) {
        return merchantMccMap.getOrDefault(transaction.getMerchant(), MccType.fromMcc(transaction.getMcc()));
    }

    public String processTransaction(Transaction transaction) {
        try {
            Account account = accountRepository.getAccount(transaction.getAccountId());

            MccType mccType = getMccType(transaction);
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
