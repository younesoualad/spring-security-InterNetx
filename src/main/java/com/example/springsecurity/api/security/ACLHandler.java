package com.example.springsecurity.api.security;


import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.RoleRepo;
import com.example.springsecurity.repository.UserRepo;
import com.example.springsecurity.service.AuthenticatedUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UserAccess")
public class ACLHandler {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public boolean canReadUser(int id) {
        return isUserPrincipal(id);
    }

    public boolean canUpdateRoles(int id) {
        return isUserPrincipal(id);
    }

    public boolean isOwner(int id) {
        return isUserPrincipal(id);
    }
    public boolean canDeleteUser(int id) {
        return isUserPrincipal(id);
    }


    public boolean rolesNotChanged(User user){
        Role oldRole = roleRepo.read(user.getId());
        Role newRole = roleRepo.getRolesAsObject(user.getRoles());
        return oldRole.equals(newRole);
    }


    private boolean isUserPrincipal(int id) {
        User user = authenticatedUserService.getAuthenticatedUser();
        User _user = userRepo.findById(id);

        if(_user !=null && user!=null)
            return (_user.getLogin().equals(user.getLogin()));

        return false;
    }
}
