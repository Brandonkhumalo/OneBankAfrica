package com.techmania.onebankafrica.Models;

public class StatementsModel {
    private String month;
    private String dates;

    public StatementsModel(String month, String dates) {
        this.month = month;
        this.dates = dates;
    }

    public String getMonth() {
        return month;
    }

    public String getDate() {
        return dates;
    }
}
