package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.orders_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;

public interface Transcation_Repo
{

    //New Transaction
    public void NewTransaction(String value, String date, orders_tbl order_id, users_tbl user_id);

}
