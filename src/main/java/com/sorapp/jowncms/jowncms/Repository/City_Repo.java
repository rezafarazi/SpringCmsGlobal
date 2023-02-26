package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.city_tbl;

import java.util.ArrayList;

public interface City_Repo
{

    //Get City
    public city_tbl GetCity(int id);

    //Get City
    public ArrayList<city_tbl> GetCites();

    //Insert new city
    public city_tbl NewCity(String name,int area,String status,String datetime);

    //Update city
    public city_tbl UpdateCity(int id,String name,int area,String status,String datetime);

}
