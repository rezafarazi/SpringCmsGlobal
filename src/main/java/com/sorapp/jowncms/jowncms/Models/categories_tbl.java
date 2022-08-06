package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.*;

@Entity
public class categories_tbl
{

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = true)
    String titie;

    @Column(nullable = true)
    String icon_address;

    @Column(nullable = true)
    String type;

    @Column(nullable = true)
    String create_date;

    @Column(nullable = true)
    String enabled_flag;

    @Column(nullable = true)
    String root_id;

    @ManyToOne
    users_tbl user_id;

    @Column(nullable = true)
    String cat_link;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitie() {
        return titie;
    }

    public void setTitie(String titie) {
        this.titie = titie;
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

    public String getRoot_id() {
        return root_id;
    }

    public void setRoot_id(String root_id) {
        this.root_id = root_id;
    }

    public users_tbl getUser_id() {
        return user_id;
    }

    public void setUser_id(users_tbl user_id) {
        this.user_id = user_id;
    }

    public String getCat_link() {
        return cat_link;
    }

    public void setCat_link(String cat_link) {
        this.cat_link = cat_link;
    }
}
