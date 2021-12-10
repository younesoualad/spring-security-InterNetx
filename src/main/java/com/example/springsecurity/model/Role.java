package com.example.springsecurity.model;

public class Role {

    private int id;
    private int user_id;
    private Boolean role_admin = false ,
            role_develop = false,
            role_cctld = false,
            role_gtld = false,
            role_billing = false ,
            role_registry = false,
            role_purchase_read = false,
            role_purchase_write = false,
            role_sale_write= false,
            role_sql = false;

    public Boolean getRole_admin() {
        return role_admin;
    }

    public void setRole_admin(Boolean role_admin) {
        this.role_admin = role_admin;
    }

    public Boolean getRole_develop() {
        return role_develop;
    }

    public void setRole_develop(Boolean role_develop) {
        this.role_develop = role_develop;
    }

    public Boolean getRole_cctld() {
        return role_cctld;
    }

    public void setRole_cctld(Boolean role_cctld) {
        this.role_cctld = role_cctld;
    }

    public Boolean getRole_gtld() {
        return role_gtld;
    }

    public void setRole_gtld(Boolean role_gtld) {
        this.role_gtld = role_gtld;
    }

    public Boolean getRole_billing() {
        return role_billing;
    }

    public void setRole_billing(Boolean role_billing) {
        this.role_billing = role_billing;
    }

    public Boolean getRole_registry() {
        return role_registry;
    }

    public void setRole_registry(Boolean role_registry) {
        this.role_registry = role_registry;
    }

    public Boolean getRole_purchase_read() {
        return role_purchase_read;
    }

    public void setRole_purchase_read(Boolean role_purchase_read) {
        this.role_purchase_read = role_purchase_read;
    }

    public Boolean getRole_purchase_write() {
        return role_purchase_write;
    }

    public void setRole_purchase_write(Boolean role_purchase_write) {
        this.role_purchase_write = role_purchase_write;
    }

    public Boolean getRole_sale_write() {
        return role_sale_write;
    }

    public void setRole_sale_write(Boolean role_sale_write) {
        this.role_sale_write = role_sale_write;
    }

    public Boolean getRole_sql() {
        return role_sql;
    }

    public void setRole_sql(Boolean role_sql) {
        this.role_sql = role_sql;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Role other = (Role) obj;

        return this.role_admin == other.role_admin &&
                this.role_billing == other.role_billing &&
                this.role_cctld == other.role_cctld &&
                this.role_develop == other.role_develop &&
                this.role_gtld == other.role_gtld &&
                this.role_purchase_read == other.role_purchase_read &&
                this.role_purchase_write == other.role_purchase_write &&
                this.role_registry == other.role_registry &&
                this.role_sale_write == other.role_sale_write &&
                this.role_sql == other.role_sql;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", role_admin=" + role_admin +
                ", role_develop=" + role_develop +
                ", role_cctld=" + role_cctld +
                ", role_gtld=" + role_gtld +
                ", role_billing=" + role_billing +
                ", role_registry=" + role_registry +
                ", role_purchase_read=" + role_purchase_read +
                ", role_purchase_write=" + role_purchase_write +
                ", role_sale_write=" + role_sale_write +
                ", role_sql=" + role_sql +
                '}';
    }
}
