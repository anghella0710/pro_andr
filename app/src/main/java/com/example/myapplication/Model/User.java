package com.example.myapplication.Model;

public class User {
    private String email;
    private String password;
    private String imageUrl;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String imageUrl) {
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
