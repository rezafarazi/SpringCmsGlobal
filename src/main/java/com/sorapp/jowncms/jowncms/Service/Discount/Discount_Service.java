package com.sorapp.jowncms.jowncms.Service.Discount;

import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Models.discount_code_tbl;
import com.sorapp.jowncms.jowncms.Repository.Discount_Repo;
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

import java.util.List;

@Component
public class Discount_Service implements Discount_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<discount_code_tbl> GetAllDiscounts()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM discount_code_tbl discount WHERE discount.delete_flag='0'").list();
//            return session.get(hub_tbl.class, id);
        }
    }

    @Override
    public discount_code_tbl NewDiscount(String code, int count, String delete_flag, String date)
    {
        discount_code_tbl Discount=new discount_code_tbl();
        Discount.setCode(code);
        Discount.setCount(count);
        Discount.setDate(date);
        Discount.setDelete_flag(delete_flag);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(Discount);
            session.getTransaction().commit();
        }

        return Discount;
    }

    @Override
    public void RemoveDiscount(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            discount_code_tbl discount_code = (discount_code_tbl) session.createQuery("FROM discount_code_tbl discount WHERE discount.id=" + id).list().get(0);
            discount_code.setDelete_flag("1");

            session.beginTransaction();
            session.update(discount_code);
            session.getTransaction().commit();
        }
    }

}
