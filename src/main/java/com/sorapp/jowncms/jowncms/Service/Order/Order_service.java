package com.sorapp.jowncms.jowncms.Service.Order;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.orders_tbl;
import com.sorapp.jowncms.jowncms.Models.store_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Orders_Repo;
import org.aspectj.weaver.ast.Or;
import org.hibernate.query.Query;

import java.util.List;

public class Order_service implements Orders_Repo
{
    @Override
    public orders_tbl GetOrderSearchResult(String code)
    {
        Query HQL= DBRepository.session.createQuery("FROM orders_tbl WHERE orders_tbl.code LIKE '%"+code+"%'");
        orders_tbl Result = (orders_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public List<orders_tbl> GetCurrentOrders() {
        Query HQL=DBRepository.session.createQuery("FROM orders_tbl WHERE orders_tbl.order_status='1'");
        List<orders_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public orders_tbl NewOrder(String type, String date, String time, String price, store_tbl store_id, users_tbl user_id)
    {
        orders_tbl Order=new orders_tbl();
        Order.setOrder_status("0");
        Order.setDate(date);
        Order.setPrice(price);
        Order.setTime(time);
        Order.setStore_id(store_id);
        Order.setType(type);
        Order.setUser_id(user_id);

        return Order;
    }

}
