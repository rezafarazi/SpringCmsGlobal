package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.*;

@Entity
@Table(name = "transcation_tbls")
public class transcation_tbl
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String value;
    String date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    users_tbl user_id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    orders_tbl order_id;

    public transcation_tbl() {
    }

    public transcation_tbl(String value, String date, users_tbl user_id, orders_tbl order_id) {
        this.value = value;
        this.date = date;
        this.user_id = user_id;
        this.order_id = order_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public users_tbl getUser_id() {
        return user_id;
    }

    public void setUser_id(users_tbl user_id) {
        this.user_id = user_id;
    }

    public orders_tbl getOrder_id() {
        return order_id;
    }

    public void setOrder_id(orders_tbl order_id) {
        this.order_id = order_id;
    }
}
