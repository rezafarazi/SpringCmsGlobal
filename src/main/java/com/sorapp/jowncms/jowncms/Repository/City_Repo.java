package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.city_tbl;

public interface City_Repo
{

    //Insert new city
    public city_tbl NewCity(String name,int area,String status,String datetime);

    //Update city
    public city_tbl UpdateCity(int id,String name,int area,String status,String datetime);

}
