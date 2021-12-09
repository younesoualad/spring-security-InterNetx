package com.example.springsecurity.repository;

import com.example.springsecurity.enums.ERole;
import com.example.springsecurity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepo  implements userIRepo{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoleRepo roleRepo;




    @Override
    public int create(User user) {

        GeneratedKeyHolder holder = new GeneratedKeyHolder();


        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("INSERT INTO user (login, password, fname, lname, email) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getFname());
                statement.setString(4, user.getLname());
                statement.setString(5, user.getEmail());
                return statement;
            }
        }, holder);

        int primaryKey = holder.getKey().intValue();



        try{
            User _user = user;
            _user.setId(primaryKey);
            this.roleRepo.create(_user);
        }catch (Exception e){
            System.out.println("Return Exception: "  + e );
        }
        return primaryKey;
    }


    @Override
    public int update(User user) {
        this.roleRepo.update(user);

        return jdbcTemplate.update("UPDATE user SET login=?, password=?, fname=?, lname=?, email=? WHERE id=?",
                new Object[] {user.getLogin(), user.getPassword(), user.getFname(), user.getLname(), user.getEmail(), user.getId()} );
    }

    @Override
    public User findById(int id) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM user WHERE id=?",
                    BeanPropertyRowMapper.newInstance(User.class), id);
            Set<ERole> roles = this.roleRepo.findById(user.getId());
            user.setRoles(roles);
            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Optional<User> findBylogin(String login) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM user WHERE login=?",
                    BeanPropertyRowMapper.newInstance(User.class), login);
            Set<ERole> roles = this.roleRepo.findById(user.getId());
            user.setRoles(roles);
            return Optional.ofNullable(user);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM user WHERE id=?", id);
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE from user");
    }
}
