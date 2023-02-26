package com.sorapp.jowncms.jowncms.DatabaseRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DBRepository
{
    //Global variables
    public static StandardServiceRegistry SSR;
    public static Metadata Meta;
    public static SessionFactory SF;
    public static Session session;
    public static Transaction TA;


    //Constractor start
    public DBRepository()
    {
        SSR=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Meta=new MetadataSources(SSR).getMetadataBuilder().build();
        SF=Meta.getSessionFactoryBuilder().build();
        session=SF.openSession();
        TA=session.beginTransaction();
    }
    //Constractor end

}
