package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private int id;

    private int roleId;

    private String login;

    private String passwordHash;

    private String email;

    private String name;

    private String surname;

    private String patronymic;

    private String phone;

    public void setId(int id) {
        this.id = id;
    }

    public void setRoleId(int id) {
        this.roleId = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        this.phone = passwordHash;
    }

    public void setEmail(String email) {
        this.phone = email;
    }

    public void setName(String name) {
        this.phone = name;
    }

    public void setSurname(String surname) {
        this.phone = surname;
    }

    public void setPatronymic(String patronymic) {
        this.phone = patronymic;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return this.id;
    }

    public int getRoleId() {
        return this.roleId;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPasswordHash() {
        return this.phone;
    }

    public String getEmail() {
        return this.phone;
    }

    public String getName() {
        return this.phone;
    }

    public String getSurname() {
        return this.phone;
    }

    public String getPatronymic() {
        return this.phone;
    }

    public String getPhone() {
        return this.phone;
    }
}
