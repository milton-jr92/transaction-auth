package model;

public enum TransactionStatus {
    TRANSACTION_APPROVED("00"),
    TRANSACTION_REJECTED("51"),
    TRANSACTION_ERROR("07");

    private final String code;

    TransactionStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}