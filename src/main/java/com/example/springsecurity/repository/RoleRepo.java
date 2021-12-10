package com.example.springsecurity.repository;

import com.example.springsecurity.enums.ERole;
import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.example.springsecurity.enums.ERole.*;


@Repository
public class RoleRepo implements roleIRepo{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int create(User user) {
        Role role = this.getRolesAsObject(user.getRoles());

        role.setUser_id(user.getId());

        // Insert request
        return jdbcTemplate.update("INSERT INTO role (" +
                        "user_id," +
                        " role_admin," +
                        " role_develop," +
                        " role_cctld," +
                        " role_gtld," +
                        "role_billing,"+
                        "role_registry,"+
                        "role_purchase_read,"+
                        "role_purchase_write," +
                        "role_sale_write," +
                        "role_sql"+
                        ") VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                new Object[] {
                        role.getUser_id(),
                        role.getRole_admin(),
                        role.getRole_develop(),
                        role.getRole_cctld(),
                        role.getRole_gtld(),
                        role.getRole_billing(),
                        role.getRole_registry(),
                        role.getRole_purchase_read(),
                        role.getRole_purchase_write(),
                        role.getRole_sale_write(),
                        role.getRole_sql()
        });
    }

    @PreAuthorize("!@UserAccess.isOwner(#user.id) or @UserAccess.rolesNotChanged(#user)")
    @Override
    public int update(User user) {
        Role role = getRolesAsObject(user.getRoles());
        role.setUser_id(user.getId());
        return jdbcTemplate.update("UPDATE role SET " +
                        " role_admin=?," +
                        " role_develop=?," +
                        " role_cctld=?," +
                        " role_gtld=?," +
                        "role_billing=?,"+
                        "role_registry=?,"+
                        "role_purchase_read=?,"+
                        "role_purchase_write=?," +
                        "role_sale_write=?," +
                        "role_sql=? WHERE user_id=?"
                        ,
                new Object[] {
                        role.getRole_admin(),
                        role.getRole_develop(),
                        role.getRole_cctld(),
                        role.getRole_gtld(),
                        role.getRole_billing(),
                        role.getRole_registry(),
                        role.getRole_purchase_read(),
                        role.getRole_purchase_write(),
                        role.getRole_sale_write(),
                        role.getRole_sql(),
                        role.getUser_id()
                });
    }

    @Override
    public Set<ERole> findById(int id) {
        try {
            Role role = jdbcTemplate.queryForObject("SELECT * FROM role WHERE user_id=?",
                    BeanPropertyRowMapper.newInstance(Role.class), id);

            Set<ERole> roles = this.getRoleAsSet(role);
            return roles;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Role read(int id) {
        try {
            Role role = jdbcTemplate.queryForObject("SELECT * FROM role WHERE user_id=?",
                    BeanPropertyRowMapper.newInstance(Role.class), id);
            return role;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    /**
     * convert the list of enum to the persistence object
     * @param roles
     * @return
     */
    public Role getRolesAsObject(Set<ERole> roles){
        Role role = new Role();

        if(roles != null){
            roles.forEach(_role -> {
                switch (_role) {
                    case ROLE_ADMIN:
                        role.setRole_admin(true);
                        break;
                    case ROLE_DEVELOP:
                        role.setRole_develop(true);
                        break;
                    case ROLE_CCTLD:
                        role.setRole_cctld(true);
                        break;
                    case ROLE_GTLD:
                        role.setRole_gtld(true);
                        break;
                    case ROLE_BILLING:
                        role.setRole_billing(true);
                        break;
                    case ROLE_REGISTRY:
                        role.setRole_registry(true);
                        break;
                    case ROLE_PURCHASE_READ:
                        role.setRole_purchase_read(true);
                        break;
                    case ROLE_PURCHASE_WRITE:
                        role.setRole_purchase_write(true);
                        break;
                    case ROLE_SALE_WRITE:
                        role.setRole_sale_write(true);
                        break;
                    case ROLE_SQL:
                        role.setRole_sql(true);
                        break;
                }
            });
        }
        return role;
    }
    private Set<ERole> getRoleAsSet(Role role){
        if(role != null){
            Set<ERole> roles = new HashSet<>();

            if(role.getRole_admin()) roles.add(ROLE_ADMIN);
            if(role.getRole_develop()) roles.add(ROLE_DEVELOP);
            if(role.getRole_cctld()) roles.add(ROLE_CCTLD);
            if(role.getRole_billing()) roles.add(ROLE_BILLING);
            if(role.getRole_gtld()) roles.add(ROLE_GTLD);
            if(role.getRole_registry()) roles.add(ROLE_REGISTRY);
            if(role.getRole_purchase_read()) roles.add(ROLE_PURCHASE_READ);
            if(role.getRole_purchase_write()) roles.add(ROLE_PURCHASE_WRITE);
            if(role.getRole_sale_write()) roles.add(ROLE_SALE_WRITE);

            return roles;
        }

        return null;
    }


}
