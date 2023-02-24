package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "store_categories_tbls")
public class store_categories_tbl
{

    @Id
    @GeneratedValue
    int id;
    String title;
    int root_cat_id;
    String icon_address;
    String datetime;
    String castom_link;
    String visible;
    String delete_flag;

    public store_categories_tbl() {
    }

    public store_categories_tbl(String title, int root_cat_id, String icon_address, String datetime, String castom_link, String visible, String delete_flag) {
        this.title = title;
        this.root_cat_id = root_cat_id;
        this.icon_address = icon_address;
        this.datetime = datetime;
        this.castom_link = castom_link;
        this.visible = visible;
        this.delete_flag = delete_flag;
    }

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

    public int getRoot_cat_id() {
        return root_cat_id;
    }

    public void setRoot_cat_id(int root_cat_id) {
        this.root_cat_id = root_cat_id;
    }

    public String getIcon_address() {
        return icon_address;
    }

    public void setIcon_address(String icon_address) {
        this.icon_address = icon_address;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCastom_link() {
        return castom_link;
    }

    public void setCastom_link(String castom_link) {
        this.castom_link = castom_link;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
    }
}
