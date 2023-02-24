package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.*;

@Entity
@Table(name = "posts_tbls")
public class posts_tbl
{

    @Id
    @GeneratedValue
    int id;
    String title;
    String content;
    String image;
    String date;
    String delete_flag;
    String visible;
    @ManyToOne
    @JoinColumn(name = "user_id")
    users_tbl user_id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    categories_tbl category_id;

    public posts_tbl() {
    }

    public posts_tbl(String title, String content, String image, String date, String delete_flag, String visible, users_tbl user_id, categories_tbl category_id) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.date = date;
        this.delete_flag = delete_flag;
        this.visible = visible;
        this.user_id = user_id;
        this.category_id = category_id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public users_tbl getUser_id() {
        return user_id;
    }

    public void setUser_id(users_tbl user_id) {
        this.user_id = user_id;
    }

    public categories_tbl getCategory_id() {
        return category_id;
    }

    public void setCategory_id(categories_tbl category_id) {
        this.category_id = category_id;
    }
}
