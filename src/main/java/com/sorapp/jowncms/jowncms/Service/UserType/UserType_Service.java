package com.sorapp.jowncms.jowncms.Service.UserType;

import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.discount_code_tbl;
import com.sorapp.jowncms.jowncms.Models.userstype_tbl;
import com.sorapp.jowncms.jowncms.Repository.Users_Type_Repo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserType_Service implements Users_Type_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<userstype_tbl> GetAllUserType()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM userstype_tbl").list();
        }
    }

    @Override
    public userstype_tbl GetUserType(int user_type_id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (userstype_tbl) session.createQuery("FROM userstype_tbl usrtype WHERE usrtype.id="+user_type_id).list().get(0);
//            return session.get(hub_tbl.class, id);
        }
    }

    @Override
    public userstype_tbl NewType(String title, String en_title)
    {
        userstype_tbl NUserType=new userstype_tbl();
        NUserType.setTitle(title);
        NUserType.setEn_title(en_title);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(NUserType);
            session.getTransaction().commit();
        }

        return NUserType;
    }

    @Override
    public userstype_tbl UpdateType(int id, String title, String en_title)
    {
        userstype_tbl NUserType=GetUserType(id);
        NUserType.setTitle(title);
        NUserType.setEn_title(en_title);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(NUserType);
            session.getTransaction().commit();
        }

        return NUserType;
    }

    @Override
    public void RemoveType(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            userstype_tbl NUserType=GetUserType(id);

            session.beginTransaction();
            session.remove(NUserType);
            session.getTransaction().commit();
        }
    }

}
