package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class configs_tbl
{

    @Id
    @GeneratedValue
    int id;

    String configs_json;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConfigs_json() {
        return configs_json;
    }

    public void setConfigs_json(String configs_json) {
        this.configs_json = configs_json;
    }
}
