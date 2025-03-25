package com.techmania.onebankafrica.Models;

public class HomeActivityModel {
    private String imageName;
    private String categoryName;

    public HomeActivityModel(String imageName, String categoryName) {
        this.imageName = imageName;
        this.categoryName = categoryName;
    }

    public String getImageName() {
        return imageName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
