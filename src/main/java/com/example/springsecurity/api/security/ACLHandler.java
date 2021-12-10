package com.example.springsecurity.api.security;


import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.UserRepo;
import com.example.springsecurity.service.AuthenticatedUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UserAccess")
public class ACLHandler {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public boolean canReadUser(int id) {
        return isUserPrincipal(id);
    }

    public boolean canUpdateRoles(int id) {
        return isUserPrincipal(id);
    }

    public boolean canDeleteUser(int id) {
        return isUserPrincipal(id);
    }


    private boolean isUserPrincipal(int id) {
        User user = authenticatedUserService.getAuthenticatedUser();
        User _user = userRepo.findById(id);
        return (_user.getLogin().equals(user.getLogin()));
    }
}
