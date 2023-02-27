package com.sorapp.jowncms.jowncms.Service.Order;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.orders_tbl;
import com.sorapp.jowncms.jowncms.Models.store_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Orders_Repo;
import org.aspectj.weaver.ast.Or;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Order_service implements Orders_Repo
{

    @Override
    public orders_tbl GetOrderSearchResult(String code)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM orders_tbl WHERE orders_tbl.code LIKE '%"+code+"%'");
        orders_tbl Result = (orders_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public List<orders_tbl> GetCurrentOrders()
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL=DBRepository.session.createQuery("FROM orders_tbl WHERE orders_tbl.order_status='1'");
        List<orders_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public orders_tbl NewOrder(String type, String date, String time, String price, store_tbl store_id, users_tbl user_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        orders_tbl Order=new orders_tbl();
        Order.setOrder_status("0");
        Order.setDate(date);
        Order.setPrice(price);
        Order.setTime(time);
        Order.setStore_id(store_id);
        Order.setType(type);
        Order.setUser_id(user_id);

        DBRepository.session.save(Order);
        DBRepository.TA.commit();

        return Order;
    }

}
