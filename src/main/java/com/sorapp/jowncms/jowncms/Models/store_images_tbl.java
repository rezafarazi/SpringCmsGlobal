package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.*;

@Entity
@Table(name = "store_images_tbls")
public class store_images_tbl
{

    @Id
    @GeneratedValue
    int id;
    String date;
    @ManyToOne
    @JoinColumn(name = "store_id")
    store_tbl store_id;
    @ManyToOne
    @JoinColumn(name = "file_id")
    file_tbl file_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public store_tbl getStore_id() {
        return store_id;
    }

    public void setStore_id(store_tbl store_id) {
        this.store_id = store_id;
    }

    public file_tbl getFile_id() {
        return file_id;
    }

    public void setFile_id(file_tbl file_id) {
        this.file_id = file_id;
    }
}
