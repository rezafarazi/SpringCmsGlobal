package com.sorapp.jowncms.jowncms.Service.City;

import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Repository.City_Repo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class City_Service implements City_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public city_tbl GetCity(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (city_tbl) session.createQuery("FROM city_tbl city WHERE city.id=:id").setParameter("id",id).list().get(0);
        }
    }

    @Override
    public List<city_tbl> GetCites()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM city_tbl").list();
        }
    }

    @Override
    public city_tbl NewCity(String name, int area, String status, String datetime)
    {
        city_tbl City=new city_tbl();
        City.setName(name);
        City.setArea(area);
        City.setStatus(status);
        City.setDatetime(datetime);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(City);
            session.getTransaction().commit();
        }

        return City;
    }

    @Override
    public city_tbl UpdateCity(int id, String name, int area, String status, String datetime)
    {
        city_tbl City=GetCity(id);
        City.setName(name);
        City.setArea(area);
        City.setStatus(status);
        City.setDatetime(datetime);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(City);
            session.getTransaction().commit();
        }

        return City;
    }

}
