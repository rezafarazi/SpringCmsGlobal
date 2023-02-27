package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.*;

@Entity
@Table(name = "orders_tbls")
public class orders_tbl
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String type;
    String date;
    String time;
    String order_status;
    String price;
    @ManyToOne
    @JoinColumn(name = "store_id")
    store_tbl store_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    users_tbl user_id;

    public orders_tbl() {
    }

    public orders_tbl(String type, String date, String time, String order_status, String price, store_tbl store_id, users_tbl user_id) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.order_status = order_status;
        this.price = price;
        this.store_id = store_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public store_tbl getStore_id() {
        return store_id;
    }

    public void setStore_id(store_tbl store_id) {
        this.store_id = store_id;
    }

    public users_tbl getUser_id() {
        return user_id;
    }

    public void setUser_id(users_tbl user_id) {
        this.user_id = user_id;
    }
}
