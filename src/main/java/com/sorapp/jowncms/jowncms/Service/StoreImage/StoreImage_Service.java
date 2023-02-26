package com.sorapp.jowncms.jowncms.Service.StoreImage;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.store_categories_tbl;
import com.sorapp.jowncms.jowncms.Models.store_tbl;
import com.sorapp.jowncms.jowncms.Models.*;
import com.sorapp.jowncms.jowncms.Repository.*;
import org.hibernate.query.Query;

import java.util.List;

public class StoreImage_Service implements Store_Images_Repo
{

    @Override
    public List<store_images_tbl> GetAllStoreImages(int store_id)
    {
        Query HQL= DBRepository.session.createQuery("FROM store_images_tbl");
        List<store_images_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public store_images_tbl NewStoreImage(String date, store_tbl store_id, file_tbl file_id)
    {
        store_images_tbl store_images_tbl=new store_images_tbl();
        store_images_tbl.setStore_id(store_id);
        store_images_tbl.setFile_id(file_id);
        store_images_tbl.setDate(date);

        DBRepository.session.save(store_images_tbl);
        DBRepository.TA.commit();

        return store_images_tbl;
    }

    @Override
    public void RemoveStoreImage(int id)
    {
        Query HQL= DBRepository.session.createQuery("FROM store_images_tbl WHERE store_images_tbl.id="+id);
        store_images_tbl Result = (store_images_tbl) HQL.list().get(0);

        DBRepository.session.remove(Result);
        DBRepository.TA.commit();
    }
}
