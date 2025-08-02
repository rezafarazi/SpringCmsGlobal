package com.sorapp.jowncms.jowncms.Service.Hospital;

import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Models.hospital_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Hospital_Repo;
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
public class Hospital_Service implements Hospital_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;



    @Override
    public hospital_tbl GetHospital(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (hospital_tbl) session.createQuery("FROM hospital_tbl WHERE hospital_tbl.id="+id).list().get(0);
//            return session.get(hub_tbl.class, id);
        }
    }

    @Override
    public List<hospital_tbl> GetAllHospitals()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM hospital_tbl").list();
        }
    }

    @Override
    public hospital_tbl NewHospital(String name, String bio, String lat, String lng, String address, String start_time, String end_time, String tel, String status, city_tbl city_id)
    {
        hospital_tbl Hospital=new hospital_tbl();
        Hospital.setAddress(address);
        Hospital.setBio(bio);
        Hospital.setName(name);
        Hospital.setStatus(status);
        Hospital.setLat(lat);
        Hospital.setLat(lng);
        Hospital.setStart_time(start_time);
        Hospital.setEnd_time(end_time);
        Hospital.setCity_id(city_id);
        Hospital.setTel(tel);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(Hospital);
            session.getTransaction().commit();
        }

        return Hospital;
    }

    @Override
    public hospital_tbl UpdateHospital(int id, String name, String bio, String lat, String lng, String address, String start_time, String end_time, String tel, String status, city_tbl city_id)
    {
        hospital_tbl Hospital=GetHospital(id);
        Hospital.setAddress(address);
        Hospital.setBio(bio);
        Hospital.setName(name);
        Hospital.setStatus(status);
        Hospital.setLat(lat);
        Hospital.setLat(lng);
        Hospital.setStart_time(start_time);
        Hospital.setEnd_time(end_time);
        Hospital.setCity_id(city_id);
        Hospital.setTel(tel);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(Hospital);
            session.getTransaction().commit();
        }

        return Hospital;
    }

    @Override
    public void RemoveHospital(int id)
    {
        hospital_tbl Hospital=GetHospital(id);
        Hospital.setStatus("0");

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(Hospital);
            session.getTransaction().commit();
        }
    }
}
