package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "settings_tbls")
public class settings_tbl
{

    @Id
    @GeneratedValue
    int id;
    String parametrs;
    String discountall;

    public settings_tbl() {
    }

    public settings_tbl(String parametrs, String discountall) {
        this.parametrs = parametrs;
        this.discountall = discountall;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParametrs() {
        return parametrs;
    }

    public void setParametrs(String parametrs) {
        this.parametrs = parametrs;
    }

    public String getDiscountall() {
        return discountall;
    }

    public void setDiscountall(String discountall) {
        this.discountall = discountall;
    }
}
