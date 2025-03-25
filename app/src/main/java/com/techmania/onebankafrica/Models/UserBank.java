package com.techmania.onebankafrica.Models;

public class UserBank {
    String accountNumber;
    String bankBalance;
    String currentBalance;

    public UserBank(){
        //Required
    }

    public UserBank(String accountNumber, String bankBalance, String currentBalance) {
        this.accountNumber = accountNumber;
        this.bankBalance = bankBalance;
        this.currentBalance = currentBalance;
    }

    public String getaccountNumber() {
        return accountNumber;
    }

    public String getbankBalance() {
        return bankBalance;
    }

    public String getcurrentBalance() {
        return currentBalance;
    }
}
