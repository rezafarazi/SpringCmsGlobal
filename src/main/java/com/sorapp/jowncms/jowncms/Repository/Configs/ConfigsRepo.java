package com.sorapp.jowncms.jowncms.Repository.Configs;

import com.sorapp.jowncms.jowncms.Models.configs_tbl;
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
public class ConfigsRepo
{

    //Get Configs Start
    public ArrayList<configs_tbl> GetConfigs()
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();
        String hql = "FROM configs_tbl config";
        Query query = session.createQuery(hql);
        List results = query.list();

        t.commit();
        factory.close();
        session.close();
        return (ArrayList<configs_tbl>) results;
    }
    //Get Configs End



    //Add New Config Start
    public void SetConfigs(String json)
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();

        configs_tbl conf=new configs_tbl();
        conf.setId(1);
        conf.setConfigs_json(json);

        session.update(conf);
        t.commit();
        factory.close();
        session.close();
    }
    //Add New Config End


}
