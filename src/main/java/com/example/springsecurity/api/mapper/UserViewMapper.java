package com.example.springsecurity.api.mapper;

import com.example.springsecurity.api.dto.UserViewDTO;
import com.example.springsecurity.api.dto.NewUserDTO;
import com.example.springsecurity.enums.ERole;
import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.UserRepo;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.springsecurity.enums.ERole.*;

@Mapper(componentModel = "spring")
@Component
public class UserViewMapper {

    public User toUser(NewUserDTO newUserDTO) {
        User user = new User();
        user.setLogin(newUserDTO.getLogin());
        user.setEmail(newUserDTO.getEmail());
        user.setFname(newUserDTO.getFname());
        user.setLname(newUserDTO.getLname());


        Set<String> rqRoles = newUserDTO.getRoles();


        Set<ERole> roles = this.getRolesAsERoles(rqRoles);


        user.setRoles(roles);

        return user;
    }
    public UserViewDTO toUserView(User user){
        UserViewDTO userView = new UserViewDTO();
        userView.setFullName(user.getFullName());
        userView.setUsername(user.getLogin());
        userView.setRoles(user.getRoles().stream()
                .map(role -> role.name())
                .collect(Collectors.toSet()));

        return userView;
    }

    private Set<ERole> getRolesAsERoles(Set<String> roles){

        Set<ERole> _roles = new HashSet<>();

        if(roles != null){
            roles.forEach(role -> {
                switch (role) {
                    case "admin":
                        _roles.add(ERole.ROLE_ADMIN);
                        break;
                    case "develop":
                        _roles.add(ERole.ROLE_DEVELOP);
                        break;
                    case "cctld":
                        _roles.add(ERole.ROLE_CCTLD);
                        break;
                    case "gtld":
                        _roles.add(ERole.ROLE_GTLD);
                        break;
                    case "billing`":
                        _roles.add(ERole.ROLE_BILLING);
                        break;
                    case "registry":
                        _roles.add(ERole.ROLE_REGISTRY);
                        break;
                    case "purchase_read":
                        _roles.add(ERole.ROLE_PURCHASE_READ);
                        break;
                    case "purchase_write":
                        _roles.add(ERole.ROLE_PURCHASE_WRITE);
                        break;
                    case "sale_write":
                        _roles.add(ERole.ROLE_SALE_WRITE);
                        break;
                    case "sql":
                        _roles.add(ERole.ROLE_SQL);
                        break;
                }
            });
        }
        return _roles;
    }


}
