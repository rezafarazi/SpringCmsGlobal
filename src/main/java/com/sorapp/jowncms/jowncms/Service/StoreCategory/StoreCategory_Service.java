package com.sorapp.jowncms.jowncms.Service.StoreCategory;

import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.store_categories_tbl;
import com.sorapp.jowncms.jowncms.Models.store_images_tbl;
import com.sorapp.jowncms.jowncms.Repository.Store_Categories_Repo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreCategory_Service implements Store_Categories_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public store_categories_tbl GetCategory(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (store_categories_tbl) session.createQuery("FROM store_categories_tbl store WHERE store.id=:id").setParameter("id",id).list().get(0);
//            return session.get(hub_tbl.class, id);
        }
    }

    @Override
    public List<store_categories_tbl> GetCategories()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM store_categories_tbl cat WHERE cat.delete_flag LIKE '0'").list();
        }
    }

    @Override
    public store_categories_tbl NewCategory(String title, int root_cat_id, String icon_address, String datetime, String castom_link, String visible, String delete_flag)
    {
        store_categories_tbl NCategory=new store_categories_tbl(
                title,
                root_cat_id,
                icon_address,
                datetime,
                castom_link,
                visible,
                delete_flag
        );

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(NCategory);
            session.getTransaction().commit();
        }

        return NCategory;
    }

    @Override
    public store_categories_tbl UpdateCategory(int id, String title, int root_cat_id, String icon_address, String castom_link, String visible, String delete_flag)
    {
        store_categories_tbl NCategory=GetCategory(id);
        NCategory.setTitle(title);
        NCategory.setRoot_cat_id(root_cat_id);
        NCategory.setIcon_address(icon_address);
        NCategory.setCastom_link(castom_link);
        NCategory.setVisible(visible);
        NCategory.setDelete_flag(delete_flag);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(NCategory);
            session.getTransaction().commit();
        }

        return NCategory;
    }


    @Override
    public void RemoveCategory(int id)
    {
        store_categories_tbl NCategory=GetCategory(id);
        NCategory.setDelete_flag("1");

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(NCategory);
            session.getTransaction().commit();
        }
    }


}
