package com.techmania.onebankafrica.Models;

public class UserRecipients {
    String recipientName;
    String recipientAccountType;
    String recipientBank;
    String recipientAccountNumber;

    public UserRecipients(){}

    public UserRecipients(String recipientName,String recipientBank, String recipientAccountNumber, String recipientAccountType) {
        this.recipientName = recipientName;
        this.recipientAccountType = recipientAccountType;
        this.recipientBank = recipientBank;
        this.recipientAccountNumber = recipientAccountNumber;
    }

    public String getRecipientBank() {
        return recipientBank;
    }

    public String getRecipientAccountType() {
        return recipientAccountType;
    }

    public String getRecipientAccountNumber() {
        return recipientAccountNumber;
    }

    public String getRecipientName() {
        return recipientName;
    }
}
