package model;

public enum MccType {
    FOOD("foodBalance"),
    MEAL("mealBalance"),
    CASH("cashBalance");

    private final String balanceType;

    MccType(String balanceType) {
        this.balanceType = balanceType;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public static MccType fromMcc(String mcc) {
        return switch (mcc) {
            case "5411", "5412" -> FOOD;
            case "5811", "5812" -> MEAL;
            default -> CASH;
        };
    }
}
