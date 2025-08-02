package com.sorapp.jowncms.jowncms.Service.StoreImage;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.store_categories_tbl;
import com.sorapp.jowncms.jowncms.Models.store_tbl;
import com.sorapp.jowncms.jowncms.Models.*;
import com.sorapp.jowncms.jowncms.Repository.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreImage_Service implements Store_Images_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<store_images_tbl> GetAllStoreImages(store_tbl store_id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM store_images_tbl store WHERE store.store_id="+store_id.getId()).list();
        }
    }

    @Override
    public store_images_tbl NewStoreImage(String date, store_tbl store_id, file_tbl file_id)
    {
        store_images_tbl store_images_tbl=new store_images_tbl();
        store_images_tbl.setStore_id(store_id);
        store_images_tbl.setFile_id(file_id);
        store_images_tbl.setDate(date);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(store_images_tbl);
            session.getTransaction().commit();
        }

        return store_images_tbl;
    }

    @Override
    public void RemoveStoreImage(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            store_images_tbl Result = (store_images_tbl) session.createQuery("FROM store_images_tbl WHERE store_images_tbl.id="+id).list().get(0);

            session.beginTransaction();
            session.remove(Result);
            session.getTransaction().commit();
        }
    }

    @Override
    public void RemoveRange(store_tbl store_id)
    {
        try(Session session=sessionFactory.openSession())
        {
            List<store_images_tbl> Result = session.createQuery("FROM store_images_tbl st WHERE st.store_id="+store_id.getId()).list();

            session.beginTransaction();
            session.remove(Result);
            session.getTransaction().commit();
        }
    }

}
