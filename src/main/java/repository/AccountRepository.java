package repository;

import model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountRepository {

    private static final String JDBC_URL = "jdbc:h2:mem:testes;DB_CLOSE_DELAY=-1";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";
    private static final Logger logger = LoggerFactory.getLogger(AccountRepository.class);

    public AccountRepository() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Account (" +
                    "accountId VARCHAR(255) PRIMARY KEY, " +
                    "foodBalance DOUBLE, " +
                    "mealBalance DOUBLE, " +
                    "cashBalance DOUBLE, " +
                    "totalBalance DOUBLE)");
        } catch (SQLException e) {
            logger.error("Error creating table: " + e.getMessage());
        }
    }

    public int createAccount(Account account) {
        String sql = "INSERT INTO Account (accountId, foodBalance, mealBalance, cashBalance, totalBalance) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, account.getAccountId());
            preparedStatement.setDouble(2, account.getFoodBalance());
            preparedStatement.setDouble(3, account.getMealBalance());
            preparedStatement.setDouble(4, account.getCashBalance());
            preparedStatement.setDouble(5, account.getTotalBalance());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error inserting account: " + e.getMessage());
            return 0;
        }
    }

    public Account getAccount(String accountId) {
        String sql = "SELECT * FROM Account WHERE accountId = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Account account = new Account();
                account.setAccountId(resultSet.getString("accountId"));
                account.setFoodBalance(resultSet.getDouble("foodBalance"));
                account.setMealBalance(resultSet.getDouble("mealBalance"));
                account.setCashBalance(resultSet.getDouble("cashBalance"));
                account.setTotalBalance(resultSet.getDouble("totalBalance"));

                return account;
            }
        } catch (SQLException e) {
            logger.error("Error getting account: " + e.getMessage());
        }

        return null;
    }

    public int updateAccount(Account account) {
        String sql = "UPDATE Account SET foodBalance = ?, mealBalance = ?, cashBalance = ?, totalBalance = ? WHERE accountId = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, account.getFoodBalance());
            preparedStatement.setDouble(2, account.getMealBalance());
            preparedStatement.setDouble(3, account.getCashBalance());
            preparedStatement.setDouble(4, account.getTotalBalance());
            preparedStatement.setString(5, account.getAccountId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating account: " + e.getMessage());
            return 0;
        }
    }

    public int deleteAccount(String accountId) {
        String sql = "DELETE FROM Account WHERE accountId = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting account: " + e.getMessage());
            return 0;
        }
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Account";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountId(resultSet.getString("accountId"));
                account.setFoodBalance(resultSet.getDouble("foodBalance"));
                account.setMealBalance(resultSet.getDouble("mealBalance"));
                account.setCashBalance(resultSet.getDouble("cashBalance"));
                account.setTotalBalance(resultSet.getDouble("totalBalance"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            logger.error("Error getting all accounts: " + e.getMessage());
        }

        return accounts;
    }
}
