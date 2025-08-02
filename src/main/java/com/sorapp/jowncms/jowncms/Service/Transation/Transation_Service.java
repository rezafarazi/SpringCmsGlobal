package com.sorapp.jowncms.jowncms.Service.Transation;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.orders_tbl;
import com.sorapp.jowncms.jowncms.Models.transcation_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Models.userstype_tbl;
import com.sorapp.jowncms.jowncms.Repository.Transcation_Repo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Transation_Service implements Transcation_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<transcation_tbl> GetAllTransations()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM transcation_tbl").list();
        }
    }

    @Override
    public void NewTransaction(String value, String date, orders_tbl order_id, users_tbl user_id)
    {
        transcation_tbl tarnsaction=new transcation_tbl();
        tarnsaction.setDate(date);
        tarnsaction.setUser_id(user_id);
        tarnsaction.setOrder_id(order_id);
        tarnsaction.setValue(value);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(tarnsaction);
            session.getTransaction().commit();
        }

    }

}
