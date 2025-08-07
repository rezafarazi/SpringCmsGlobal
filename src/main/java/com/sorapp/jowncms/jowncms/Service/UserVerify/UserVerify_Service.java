package com.sorapp.jowncms.jowncms.Service.UserVerify;

import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Models.userstype_tbl;
import com.sorapp.jowncms.jowncms.Repository.User_Verify_Repo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.sorapp.jowncms.jowncms.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class UserVerify_Service implements User_Verify_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public boolean CheckVerifyCode(String hash, int user_id)
    {
        try(Session session=sessionFactory.openSession())
        {
            List<userstype_tbl> Result = session.createQuery("FROM user_verify_tbl WHERE user_verify_tbl.user_id.id=:id").setParameter("id",user_id).list();

            if (Result.size() > 0)
            {
                List<userstype_tbl> RemoveResult = session.createQuery("FROM user_verify_tbl WHERE user_verify_tbl.user_id.id=:id").setParameter("id",user_id).list();

                session.beginTransaction();
                session.remove(RemoveResult);
                session.getTransaction().commit();

                return true;
            }
        }

        return false;
    }

    @Override
    public void NewUserVerifyCode(String hash_code, users_tbl user_id)
    {
        Random rnd=new Random();

        user_verify_tbl NUser=new user_verify_tbl();
        NUser.setUser_id(user_id);
        NUser.setHash_code(rnd.nextInt(1111,9999)+"");

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(NUser);
            session.getTransaction().commit();
        }
    }

    @Override
    public void RemoveUserVerifyCode(users_tbl user_id)
    {
        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.remove(session.createQuery("FROM user_verify_tbl WHERE user_verify_tbl.user_id.id=:user_id").setParameter("user_id",user_id).list());
            session.getTransaction().commit();
        }

    }

}
