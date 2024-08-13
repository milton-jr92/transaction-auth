package model;

public class Account {
    private String accountId;
    private double foodBalance;
    private double mealBalance;
    private double cashBalance;
    private double totalBalance;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getFoodBalance() {
        return foodBalance;
    }

    public void setFoodBalance(double foodBalance) {
        this.foodBalance = foodBalance;
    }

    public double getMealBalance() {
        return mealBalance;
    }

    public void setMealBalance(double mealBalance) {
        this.mealBalance = mealBalance;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(double cashBalance) {
        this.cashBalance = cashBalance;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }
}