package com.sorapp.jowncms.jowncms.Service.Store;

import com.sorapp.jowncms.jowncms.Models.*;
import com.sorapp.jowncms.jowncms.Repository.Store_Repo;
import com.sorapp.jowncms.jowncms.Service.StoreImage.StoreImage_Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Store_Service implements Store_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<store_tbl> GetAllPosts()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM store_tbl").list();
        }
    }

    @Override
    public store_tbl GetPost(int store_id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (store_tbl) session.createQuery("FROM store_tbl store WHERE store.id=:id").setParameter("id",store_id).list().get(0);
//            return session.get(hub_tbl.class, id);
        }
    }

    @Override
    public List<store_tbl> GetSearchPost(String Search)
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM store_tbl WHERE store_tbl.title LIKE :search").setParameter("search", "%" + Search + "%").list();
        }
    }

    @Override
    public store_tbl NewPost(String title, String content, String type, String date, String delete_flag, String visible, String json, String price, String discount, String adiscount, users_tbl user_id, store_categories_tbl category_id, file_tbl file_id)
    {
        store_tbl store=new store_tbl();
        store.setTitle(title);
        store.setContent(content);
        store.setJson(json);
        store.setDiscount(discount);
        store.setType(type);
        store.setPrice(price);
        store.setCategory_id(category_id);
        store.setDelete_flag(delete_flag);
        store.setVisible(visible);
        store.setDate(date);
        store.setAdiscount(adiscount);
        store.setFile_id(file_id);
        store.setUser_id(user_id);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(store);
            session.getTransaction().commit();
        }

        return store;
    }

    @Override
    public store_tbl UpdatePost(int id, String title, String content, String date, String delete_flag, String visible, String json, String price, String discount, String adiscount, users_tbl user_id, store_categories_tbl category_id, file_tbl file_id)
    {
        store_tbl store=GetPost(id);
        store.setTitle(title);
        store.setContent(content);
        store.setJson(json);
        store.setDiscount(discount);
        store.setPrice(price);
        store.setAdiscount(adiscount);
        store.setCategory_id(category_id);
        store.setDelete_flag(delete_flag);
        store.setVisible(visible);
        store.setDate(date);
        store.setFile_id(file_id);
        store.setUser_id(user_id);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(store);
            session.getTransaction().commit();
        }

        return store;
    }

    @Override
    public void RemovePost(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            store_tbl Result = (store_tbl) session.createQuery("FROM store_tbl store WHERE store.id=" + id).list().get(0);;
            Result.setDelete_flag("1");

            session.beginTransaction();
            session.save(Result);
            session.getTransaction().commit();
        }

    }

}
