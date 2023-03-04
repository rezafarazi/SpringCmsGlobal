package com.sorapp.jowncms.jowncms.Service.Order;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
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
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class Order_service implements Orders_Repo
{

    @Override
    public List<orders_tbl> GetOrderSearchResult(int code)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM orders_tbl order WHERE order.id="+code);
        List<orders_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public orders_tbl GetOrder(int id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM orders_tbl order WHERE order.id="+id);
        List<orders_tbl> Result = HQL.getResultList();
        return Result.get(0);
    }

    @Override
    public List<orders_tbl> GetCurrentOrders()
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL=DBRepository.session.createQuery("FROM orders_tbl order WHERE order.order_status='1'");
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

    @Override
    public void RemoveSetStatus(int Order_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        orders_tbl order=GetOrder(Order_id);
        order.setOrder_status("3");

        DBRepository.session.update(order);
        DBRepository.TA.commit();
    }

    @Override
    public void PaySetStatus(int Order_id,users_tbl user_id)
    {
        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        orders_tbl order=GetOrder(Order_id);

        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        transcation_tbl t=new transcation_tbl();
        t.setOrder_id(order);
        t.setValue(order.getPrice());
        t.setUser_id(user_id);
        t.setDate(time);

        DBRepository.session.save(t);
        DBRepository.TA.commit();


        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        order.setOrder_status("1");

        DBRepository.session.update(order);
        DBRepository.TA.commit();
    }

    @Override
    public void OKSetStatus(int Order_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        orders_tbl order=GetOrder(Order_id);
        order.setOrder_status("2");

        DBRepository.session.update(order);
        DBRepository.TA.commit();
    }

}
