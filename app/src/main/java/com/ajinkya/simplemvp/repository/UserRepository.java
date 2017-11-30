package com.ajinkya.simplemvp.repository;


import com.ajinkya.simplemvp.model.User;

public interface UserRepository {
    User getUser(int id);

    void save(User u);
}
