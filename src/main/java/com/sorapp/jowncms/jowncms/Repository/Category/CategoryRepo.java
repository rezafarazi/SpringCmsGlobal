package com.sorapp.jowncms.jowncms.Repository.Category;

import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryRepo
{

    public ArrayList<categories_tbl> GetAllCategories()
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();
        String hql = "FROM categories_tbl categories";
        Query query = session.createQuery(hql);
        List results = query.list();

        t.commit();
        factory.close();
        session.close();

        return (ArrayList<categories_tbl>) results;

    }

}
