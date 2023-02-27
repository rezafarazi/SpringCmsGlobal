package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.*;

@Entity
@Table(name = "store_tbls")
public class store_tbl
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String content;
    String type;
    String date;
    String delete_flag;
    String visible;
    String json;
    String price;
    String discount;
    String adiscount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    users_tbl user_id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    store_categories_tbl category_id;
    @ManyToOne
    @JoinColumn(name = "file_id")
    file_tbl file_id;

    public store_tbl() {
    }

    public store_tbl(String title, String content, String type, String date, String delete_flag, String visible, String json, String price, String discount, String adiscount, users_tbl user_id, store_categories_tbl category_id, file_tbl file_id) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.date = date;
        this.delete_flag = delete_flag;
        this.visible = visible;
        this.json = json;
        this.price = price;
        this.discount = discount;
        this.adiscount = adiscount;
        this.user_id = user_id;
        this.category_id = category_id;
        this.file_id = file_id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAdiscount() {
        return adiscount;
    }

    public void setAdiscount(String adiscount) {
        this.adiscount = adiscount;
    }

    public users_tbl getUser_id() {
        return user_id;
    }

    public void setUser_id(users_tbl user_id) {
        this.user_id = user_id;
    }

    public store_categories_tbl getCategory_id() {
        return category_id;
    }

    public void setCategory_id(store_categories_tbl category_id) {
        this.category_id = category_id;
    }

    public file_tbl getFile_id() {
        return file_id;
    }

    public void setFile_id(file_tbl file_id) {
        this.file_id = file_id;
    }
}
