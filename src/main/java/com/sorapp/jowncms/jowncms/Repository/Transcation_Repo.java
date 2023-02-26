package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.orders_tbl;
import com.sorapp.jowncms.jowncms.Models.transcation_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;

import java.util.ArrayList;
import java.util.List;

public interface Transcation_Repo
{

    //Get All Transaction
    public List<transcation_tbl> GetAllTransations();

    //New Transaction
    public void NewTransaction(String value, String date, orders_tbl order_id, users_tbl user_id);

}
