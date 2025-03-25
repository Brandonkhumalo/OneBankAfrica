package com.techmania.onebankafrica.Models;

public class Transactions {
    String transactionType;
    String transactionDate;
    String transactionAmount;
    String transactionStatus;

    public Transactions(){}

    public Transactions(String transactionType, String transactionDate, String transactionAmount, String transactionStatus) {
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }
}
