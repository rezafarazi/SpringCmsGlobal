package com.sorapp.jowncms.jowncms.Service.Order;

import com.sorapp.jowncms.jowncms.Models.orders_tbl;
import com.sorapp.jowncms.jowncms.Models.store_tbl;
import com.sorapp.jowncms.jowncms.Models.transcation_tbl;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class Order_service implements Orders_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<orders_tbl> GetOrderSearchResult(int code)
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM orders_tbl order WHERE order.id=:order_id").setParameter("order_id",code).list();
        }
    }

    @Override
    public orders_tbl GetOrder(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (orders_tbl) session.createQuery("FROM orders_tbl order WHERE order.id=:order_id").setParameter("order_id",id).list().get(0);
        }
    }

    @Override
    public List<orders_tbl> GetCurrentOrders()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM orders_tbl order WHERE order.order_status='1'").list();
        }
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

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(Order);
            session.getTransaction().commit();
        }

        return Order;
    }

    @Override
    public void RemoveSetStatus(int Order_id)
    {
        try(Session session=sessionFactory.openSession())
        {
            orders_tbl order=GetOrder(Order_id);
            order.setOrder_status("3");

            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        }
    }

    @Override
    public void PaySetStatus(int Order_id,users_tbl user_id)
    {

        try(Session session=sessionFactory.openSession())
        {
            LocalDateTime datetime = LocalDateTime.now();
            String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

            orders_tbl order=GetOrder(Order_id);

            transcation_tbl t=new transcation_tbl();
            t.setOrder_id(order);
            t.setValue(order.getPrice());
            t.setUser_id(user_id);
            t.setDate(time);

            order.setOrder_status("1");

            session.beginTransaction();
            session.save(t);
            session.update(order);
            session.getTransaction().commit();
        }

    }

    @Override
    public void OKSetStatus(int Order_id)
    {
        try(Session session=sessionFactory.openSession())
        {
            orders_tbl order=GetOrder(Order_id);
            order.setOrder_status("2");

            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        }
    }

}
