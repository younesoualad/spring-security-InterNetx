package com.example.springsecurity.repository;

import com.example.springsecurity.enums.ERole;
import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;

import java.util.Set;


public interface roleIRepo {
    int create(User user);
    int update(User user);
    Set<ERole> findById(int id);
}
