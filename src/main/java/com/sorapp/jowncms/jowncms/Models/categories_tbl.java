package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class categories_tbl
{

    @Id
    @GeneratedValue
    int id;

    String title;
    String icon_address;
    String type;
    String create_date;
    String enabled_flag;
    int root_id;
    int user_id;
    String cat_link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon_address() {
        return icon_address;
    }

    public void setIcon_address(String icon_address) {
        this.icon_address = icon_address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getEnabled_flag() {
        return enabled_flag;
    }

    public void setEnabled_flag(String enabled_flag) {
        this.enabled_flag = enabled_flag;
    }

    public int getRoot_id() {
        return root_id;
    }

    public void setRoot_id(int root_id) {
        this.root_id = root_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCat_link() {
        return cat_link;
    }

    public void setCat_link(String cat_link) {
        this.cat_link = cat_link;
    }
}
