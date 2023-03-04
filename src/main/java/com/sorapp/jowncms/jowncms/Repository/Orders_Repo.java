package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.orders_tbl;
import com.sorapp.jowncms.jowncms.Models.store_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;

import java.util.ArrayList;
import java.util.List;

public interface Orders_Repo
{

    //Get Seach Order result
    public List<orders_tbl> GetOrderSearchResult(int code);

    //Get Order
    public orders_tbl GetOrder(int id);

    //Get All Current orders
    public List<orders_tbl> GetCurrentOrders();

    //New Order
    public orders_tbl NewOrder(String type, String date, String time, String price, store_tbl store_id, users_tbl user_id);

    //Get Remove
    public void RemoveSetStatus(int Order_id);

    //Get Pay
    public void PaySetStatus(int Order_id,users_tbl user_id);

    //Get OK
    public void OKSetStatus(int Order_id);

}
