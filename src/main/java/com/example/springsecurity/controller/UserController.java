package com.example.springsecurity.controller;

import com.example.springsecurity.api.dto.UserViewDTO;
import com.example.springsecurity.api.dto.NewUserDTO;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createUser(@RequestBody NewUserDTO user) {
        try {
            userService.create(user);
            return new ResponseEntity<>("User was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN') or @UserAccess.canReadUser(#id)")
    public ResponseEntity<UserViewDTO> getUserById(@PathVariable("id") int id) {
        UserViewDTO userView = userService.read(id);

        if (userView  != null) {
            return new ResponseEntity<>(userView , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN') or @UserAccess.isOwner(#id)")
    public ResponseEntity<String> updateUser(@PathVariable("id") int id, @RequestBody NewUserDTO user) {
        UserViewDTO userView = userService.update(id, user);

        if (userView  != null) {
            return new ResponseEntity<>("User was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find User with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN') and !@UserAccess.canDeleteUser(#id)")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        try {
            UserViewDTO userView = userService.delete(id);

            return new ResponseEntity<>("User was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete User.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
