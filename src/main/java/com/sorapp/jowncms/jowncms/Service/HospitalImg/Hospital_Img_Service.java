package com.sorapp.jowncms.jowncms.Service.HospitalImg;

import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.hospital_img_tbl;
import com.sorapp.jowncms.jowncms.Models.hospital_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Hospital_Img_Repo;
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
public class Hospital_Img_Service implements Hospital_Img_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<hospital_img_tbl> GetAllHospitalImages(int Hospital_Id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM hospital_img_tbl WHERE hospital_img_tbl.hospital_id.id="+Hospital_Id).list();
        }
    }

    @Override
    public hospital_img_tbl NewImage(hospital_tbl hospital_id, file_tbl file_id)
    {
        hospital_img_tbl HospitalImage=new hospital_img_tbl();
        HospitalImage.setHospital_id(hospital_id);
        HospitalImage.setFile_id(file_id);

        try(Session session = sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(HospitalImage);
            session.getTransaction().commit();
        }

        return HospitalImage;
    }

    @Override
    public void RemoveHospital(int id)
    {
        try(Session session = sessionFactory.openSession())
        {
             List<hospital_img_tbl> images = session.createQuery("FROM city_tbl").list();

            session.beginTransaction();
            session.remove(images);
            session.getTransaction().commit();
        }
    }
}
