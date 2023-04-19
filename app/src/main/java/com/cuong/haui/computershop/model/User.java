package com.cuong.haui.computershop.model;

import java.io.Serializable;

public class User implements Serializable {
    public int user_id;
    public int role;
    public String fullname;
    public String username;
    public String email;
    public String password;
    public String phone;
    public String address;
    public String image;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User(int user_id, int role, String fullname, String username, String email, String password, String phone, String address, String image) {
        this.user_id = user_id;
        this.role = role;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", role=" + role +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
