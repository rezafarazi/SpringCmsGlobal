package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.city_tbl;

import java.util.List;

public abstract interface City_Repo
{

    //Get City
    public abstract city_tbl GetCity(int id);

    //Get City
    public abstract List<city_tbl> GetCites();

    //Insert new city
    public abstract city_tbl NewCity(String name, int area, String status, String datetime);

    //Update city
    public abstract city_tbl UpdateCity(int id, String name, int area, String status, String datetime);

}
