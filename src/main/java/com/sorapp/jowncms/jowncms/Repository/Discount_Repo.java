package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.discount_code_tbl;

import java.util.ArrayList;
import java.util.List;

public interface Discount_Repo
{

    //Get All Discount
    public List<discount_code_tbl> GetAllDiscounts();

    //New discount
    public discount_code_tbl NewDiscount(String code,int count,String delete_flag,String date);

    //Remove discount
    public void RemoveDiscount(int id);

}
