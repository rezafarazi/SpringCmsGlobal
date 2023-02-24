package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city_tbls")
public class city_tbl
{
    @Id
    @GeneratedValue
    int id;
    String name;
    String area;
    String status;
    String datetime;

    public city_tbl() {
    }

    public city_tbl(String name, String area, String status, String datetime) {
        this.name = name;
        this.area = area;
        this.status = status;
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
