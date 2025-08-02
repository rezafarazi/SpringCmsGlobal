package com.sorapp.jowncms.jowncms.Service.Category;

import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Category_Repo;
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
public class Category_Service implements Category_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public categories_tbl GetCategory(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (categories_tbl) session.createQuery("FROM categories_tbl category WHERE category.id="+id).list().get(0);
//            return session.get(hub_tbl.class, id);
        }
    }

    @Override
    public List<categories_tbl> GetCategories()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM categories_tbl cat WHERE cat.delete_flag LIKE '0'").list();
        }
    }

    @Override
    public categories_tbl NewCategory(String title, int root_cat_id, String icon_address, String datetime, String castom_link, String visible, String delete_flag)
    {
        categories_tbl NCategory=new categories_tbl(
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
    public categories_tbl UpdateCategory(int id, String title, int root_cat_id, String icon_address, String castom_link, String visible, String delete_flag)
    {
        categories_tbl NCategory=GetCategory(id);

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
        categories_tbl NCategory=GetCategory(id);

        NCategory.setDelete_flag("1");

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(NCategory);
            session.getTransaction().commit();
        }
    }

}
