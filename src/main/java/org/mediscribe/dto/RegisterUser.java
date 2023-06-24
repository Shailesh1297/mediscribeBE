package org.mediscribe.dto;

import org.mediscribe.enums.Roles;

public class RegisterUser {
    private String username;
    private String firstname;
    private String lastname;

    private String password;

    private Roles role;

    public RegisterUser() {
    }

    public RegisterUser(String username, String firstname, String lastname, String password, Roles role) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role.toString();
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
