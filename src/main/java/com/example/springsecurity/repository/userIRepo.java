package com.example.springsecurity.repository;

import com.example.springsecurity.model.User;

import java.util.Optional;

public interface userIRepo {
    int create(User user);
    int update(User user);
    User findById(int id);
    Optional<User> findBylogin(String login);
    int deleteById(int id);
    int deleteAll();



}
