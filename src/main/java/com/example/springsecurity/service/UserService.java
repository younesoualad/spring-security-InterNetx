package com.example.springsecurity.service;

import com.example.springsecurity.api.dto.UserViewDTO;
import com.example.springsecurity.api.dto.NewUserDTO;
import com.example.springsecurity.enums.ERole;
import com.example.springsecurity.api.mapper.UserViewMapper;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.RoleRepo;
import com.example.springsecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  UserRepo userRepo;

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private UserViewMapper userViewMapper;


    @Transactional
    public UserViewDTO create(NewUserDTO newUserDTO) {
        User user = userViewMapper.toUser(newUserDTO);
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        userRepo.create(user);

        return userViewMapper.toUserView(user);
    }

    @Transactional
    public UserViewDTO update(int id, NewUserDTO newUserDTO) {
        User _user = userRepo.findById(id);

        if(_user!=null){
            User user = userViewMapper.toUser(newUserDTO);
            user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
            user.setId(_user.getId());
            userRepo.update(user);

            return userViewMapper.toUserView(user);
        }else{
            return null;
        }
    }

    @Transactional
    public UserViewDTO delete(int id) {
        User user = userRepo.findById(id);

       if (user != null){
            userRepo.deleteById(id);
           return userViewMapper.toUserView(user);
        }else{
           return null;
       }

    }

    public UserViewDTO read (int id){
        User user = userRepo.findById(id);
        if (user != null){
            return userViewMapper.toUserView(user);
        }else{
            return null;
        }
    }



    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepo
                .findBylogin(login)
                .orElseThrow(
                        () -> new UsernameNotFoundException(format("User with username - %s, not found", login))
                );
    }
}
