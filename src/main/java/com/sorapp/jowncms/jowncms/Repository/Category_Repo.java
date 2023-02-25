package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.categories_tbl;

public interface Category_Repo
{

    //Insert Cateogry
    public categories_tbl NewCategory(String title,int root_cat_id,String icon_address,String datetime,String castom_link,String visible,String delete_flag);

    //Update Category
    public categories_tbl UpdateCategory(int id,String title,int root_cat_id,String icon_address,String datetime,String castom_link,String visible,String delete_flag);

}
