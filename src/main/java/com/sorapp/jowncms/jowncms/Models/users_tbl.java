package com.sorapp.jowncms.jowncms.Models;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.istack.Nullable;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
public class users_tbl
{

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = true)
    String name;

    @Column(nullable = true)
    String family;

    @Column(nullable = true)
    String username;

    @Column(nullable = true)
    String password;

    @Column(nullable = true)
    String login_token;

    @Column(nullable = true)
    String google2f;

    @Column(nullable = true)
    String user_type;

    @Column(nullable = true)
    String active_user_flag;

    @Column(nullable = true)
    String create_datetime;

    @Column(nullable = true)
    String email;

    @Column(nullable = true)
    String address;

    @Column(nullable = true)
    String city;

    @Column(nullable = true)
    String phone;

    public users_tbl FromJson(String jsonstring)
    {
        JSONObject json=new JSONObject(jsonstring);
        return new users_tbl(
            Integer.parseInt(json.get("id").toString()),
            json.get("name").toString(),
            json.get("family").toString(),
            json.get("username").toString(),
            json.get("password").toString(),
            json.get("login_token").toString(),
            json.get("google2f").toString(),
            json.get("user_type").toString(),
            json.get("active_user_flag").toString(),
            json.get("create_datetime").toString(),
            json.get("email").toString(),
            json.get("address").toString(),
            json.get("city").toString(),
            json.get("phone").toString()
        );
    }

    public users_tbl() {

    }

    public users_tbl(int id, String name, String family, String username, String password, String login_token, String google2f, String user_type, String active_user_flag, String create_datetime, String email, String address, String city, String phone) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.username = username;
        this.password = password;
        this.login_token = login_token;
        this.google2f = google2f;
        this.user_type = user_type;
        this.active_user_flag = active_user_flag;
        this.create_datetime = create_datetime;
        this.email = email;
        this.address = address;
        this.city = city;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin_token() {
        return login_token;
    }

    public void setLogin_token(String login_token) {
        this.login_token = login_token;
    }

    public String getGoogle2f() {
        return google2f;
    }

    public void setGoogle2f(String google2f) {
        this.google2f = google2f;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getActive_user_flag() {
        return active_user_flag;
    }

    public void setActive_user_flag(String active_user_flag) {
        this.active_user_flag = active_user_flag;
    }

    public String getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(String create_datetime) {
        this.create_datetime = create_datetime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
