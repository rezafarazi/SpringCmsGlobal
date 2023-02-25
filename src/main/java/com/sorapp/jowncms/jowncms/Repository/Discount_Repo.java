package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.discount_code_tbl;

public interface Discount_Repo
{

    //New discount
    public discount_code_tbl NewDiscount(String code,int count,String delete_flag,String date);

    //Remove discount
    public discount_code_tbl RemoveDiscount(int id);

}
