package com.sorapp.jowncms.jowncms.Service.Store;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.*;
import com.sorapp.jowncms.jowncms.Repository.Store_Repo;
import org.hibernate.query.Query;

import java.util.List;

public class Store_Service implements Store_Repo
{

    @Override
    public List<store_tbl> GetAllPosts()
    {
        Query HQL= DBRepository.session.createQuery("FROM store_tbl");
        List<store_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public store_tbl GetPost(int store_id)
    {
        Query HQL= DBRepository.session.createQuery("FROM store_tbl WHERE store_tbl.id="+store_id);
        store_tbl Result = (store_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public List<store_tbl> GetSearchPost(String Search)
    {
        Query HQL= DBRepository.session.createQuery("FROM store_tbl WHERE store_tbl.title='%"+Search+"%'");
        List<store_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public store_tbl NewPost(String title, String content, String type, String date, String delete_flag, String visible, String json, String price, String discount, String adiscount, users_tbl user_id, store_categories_tbl category_id, file_tbl file_id)
    {
        store_tbl store=new store_tbl();
        store.setTitle(content);
        store.setContent(content);
        store.setJson(content);
        store.setDiscount(content);
        store.setType(content);
        store.setPrice(content);
        store.setCategory_id(category_id);
        store.setDelete_flag(delete_flag);
        store.setVisible(visible);
        store.setDate(date);
        store.setAdiscount(adiscount);
        store.setFile_id(file_id);
        store.setUser_id(user_id);

        DBRepository.session.save(store);
        DBRepository.TA.commit();

        return store;
    }

    @Override
    public store_tbl UpdatePost(int id, String title, String content, String type, String date, String delete_flag, String visible, String json, String price, String discount, String adiscount, users_tbl user_id, store_categories_tbl category_id, file_tbl file_id)
    {
        store_tbl store=GetPost(id);
        store.setTitle(content);
        store.setContent(content);
        store.setJson(content);
        store.setDiscount(content);
        store.setType(content);
        store.setPrice(content);
        store.setCategory_id(category_id);
        store.setDelete_flag(delete_flag);
        store.setVisible(visible);
        store.setDate(date);
        store.setAdiscount(adiscount);
        store.setFile_id(file_id);
        store.setUser_id(user_id);

        DBRepository.session.update(store);
        DBRepository.TA.commit();

        return store;
    }

    @Override
    public void RemovePost(int id)
    {
        Query HQL= DBRepository.session.createQuery("FROM store_tbl WHERE store_tbl.id="+id);
        store_tbl Result = (store_tbl) HQL.list().get(0);

        DBRepository.session.remove(Result);
        DBRepository.TA.commit();
    }

}
