package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userstype_tbls")
public class userstype_tbl
{
    @Id
    @GeneratedValue
    int id;
    String title;
    String en_title;

    public userstype_tbl() {
    }

    public userstype_tbl(String title, String en_title) {
        this.title = title;
        this.en_title = en_title;
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

    public String getEn_title() {
        return en_title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }
}
