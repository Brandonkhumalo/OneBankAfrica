package com.techmania.onebankafrica.Models;

public class User {
    public String userId;
    public String userEmail;
    public String userName;
    public String userSurname;
    public String userNumber;
    public String userIDNumber;

    public User(){
        //required empty Constructor
    }

    public User(String userId, String userName, String userSurname, String userEmail, String userNumber, String userIDNumber) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.userNumber = userNumber;
        this.userIDNumber = userIDNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }
}
