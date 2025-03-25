package com.techmania.onebankafrica.Models;

public class NotificationsModel {
    String header;
    String message;
    String date;

    public NotificationsModel() {}

    public NotificationsModel(String header, String message, String date) {
        this.header = header;
        this.message = message;
        this.date = date;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
