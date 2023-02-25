package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.orders_tbl;
import com.sorapp.jowncms.jowncms.Models.store_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;

public interface Orders_Repo
{

    //New Order
    public orders_tbl NewOrder(String type, String date, String time, String order_status, String price, store_tbl store_id, users_tbl user_id);

    //Remove Order
    public void NewOrder(int Order_Id);

}
