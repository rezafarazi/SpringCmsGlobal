package com.sorapp.jowncms.jowncms.Service.StoreImage;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.store_categories_tbl;
import com.sorapp.jowncms.jowncms.Models.store_tbl;
import com.sorapp.jowncms.jowncms.Models.*;
import com.sorapp.jowncms.jowncms.Repository.*;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreImage_Service implements Store_Images_Repo
{

    @Override
    public List<store_images_tbl> GetAllStoreImages(store_tbl store_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM store_images_tbl store WHERE store.store_id="+store_id.getId());
        List<store_images_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public store_images_tbl NewStoreImage(String date, store_tbl store_id, file_tbl file_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

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
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM store_images_tbl WHERE store_images_tbl.id="+id);
        store_images_tbl Result = (store_images_tbl) HQL.list().get(0);

        DBRepository.session.remove(Result);
        DBRepository.TA.commit();
    }

    @Override
    public void RemoveRange(store_tbl store_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM store_images_tbl st WHERE st.store_id="+store_id.getId());
        List<store_images_tbl> Result = HQL.getResultList();

        for(int i=0;i<Result.size();i++)
        {
            DBRepository.session.remove(Result.get(i));
        }
        DBRepository.TA.commit();
    }

}
