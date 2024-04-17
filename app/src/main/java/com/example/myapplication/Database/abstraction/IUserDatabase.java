package com.example.myapplication.Database.abstraction;

import com.example.myapplication.Model.User;

import java.util.UUID;

public interface IUserDatabase {
    public User getUserByEmail(String email);

}
