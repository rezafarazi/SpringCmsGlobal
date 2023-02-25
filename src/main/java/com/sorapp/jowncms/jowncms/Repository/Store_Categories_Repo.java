package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.store_categories_tbl;

public interface Store_Categories_Repo
{

    //Insert Cateogry
    public store_categories_tbl NewCategory(String title, int root_cat_id, String icon_address, String datetime, String castom_link, String visible, String delete_flag);

    //Update Category
    public store_categories_tbl UpdateCategory(int id,String title,int root_cat_id,String icon_address,String datetime,String castom_link,String visible,String delete_flag);


}
