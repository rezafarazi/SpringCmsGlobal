package com.sorapp.jowncms.jowncms.Repository.Users;

import com.google.common.hash.Hashing;
import com.sorapp.jowncms.jowncms.Controllers.AdminController;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sun.istack.Nullable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersRepo
{


    //Log Initilizer
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);


    //Check Login User Start
    public users_tbl LOGIN_USER(String Username,String Password) throws Exception {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();
        String hql = "FROM users_tbl users WHERE users.username = '"+Username+"' AND users.password='"+Password+"'";
        Query query = session.createQuery(hql);
        List results = query.list();

        t.commit();
        factory.close();
        session.close();

        if(results.size()>0)
        {
            return (users_tbl) results.get(0);
        }

        throw new Exception("User Not Found");
    }
    //Check Login User End



    //Get All User Start
    public ArrayList<users_tbl> GetAllUsers()
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();
        String hql = "FROM users_tbl users";
        Query query = session.createQuery(hql);
        List results = query.list();

        t.commit();
        factory.close();
        session.close();

        return (ArrayList<users_tbl>) results;
    }
    //Get All User End



    //Get All User Start
    public ArrayList<users_tbl> GetSearchUser(String Search)
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();
        String hql = "FROM users_tbl users fetch all properties WHERE users.phone like '%"+Search+"%' OR  users.username like '%\"+Search+\"%' or users.address like '%\"+Search+\"%' OR  users.city like '%\"+Search+\"%' OR users.create_datetime like '%\"+Search+\"%' OR users.family like '%\"+Search+\"%' OR users.name like '%\"+Search+\"%' OR users.email like '%\"+Search+\"%'";
        Query query = session.createQuery(hql);
        List results = query.list();

        t.commit();
        factory.close();
        session.close();

        return (ArrayList<users_tbl>) results;
    }
    //Get All User End



    //Update User Start
    public void UpdateUser(int id, String username, String name, String family, String email, String phone,String password,String LoginToken,String Google2F,String UserType,String user_active_flag,String datetime,String address,String city)
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();

        users_tbl user=new users_tbl();
        user.setId(id);
        user.setUsername(username);
        user.setName(name);
        user.setFamily(family);
        user.setEmail(email);
        user.setPhone(phone);
        user.setLogin_token(LoginToken);
        user.setPassword(password);
        user.setGoogle2f(Google2F);
        user.setUser_type(UserType);
        user.setActive_user_flag(user_active_flag);
        user.setCreate_datetime(datetime);
        user.setAddress(address);
        user.setCity(city);

        session.update(user);
        t.commit();
        factory.close();
        session.close();
    }
    //Update User End



    //Get User Data Start
    public users_tbl GetUserData(int user_id)
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();
        String hql = "FROM users_tbl users WHERE users.id = "+user_id;
        Query query = session.createQuery(hql);
        List results = query.list();

        t.commit();
        factory.close();
        session.close();
        return (users_tbl) results.get(0);
    }
    //Get User Data End



}
