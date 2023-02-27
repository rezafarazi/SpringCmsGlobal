package com.sorapp.jowncms.jowncms.Service.Users;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Function.Hash.HashService;
import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.User_Repo;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class Users_Service implements User_Repo
{


    public static Logger LOGGER= LoggerFactory.getLogger(Users_Service.class);


    @Override
    public List<users_tbl> GetAllUsers()
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL=DBRepository.session.createQuery("FROM users_tbl");
        List<users_tbl> Result = HQL.getResultList();
        return Result;
    }



    @Override
    public users_tbl GetUser(int id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();
        Query HQL=DBRepository.session.createQuery("FROM users_tbl user WHERE user.id="+id);
        users_tbl Result = (users_tbl) HQL.list().get(0);
        return Result;
    }


    @Override
    public users_tbl Login(String username, String password)
    {
        try
        {
            DBRepository.session = DBRepository.SF.openSession();
            DBRepository.TA = DBRepository.session.beginTransaction();
            Query HQL = DBRepository.session.createQuery("FROM users_tbl user WHERE user.username LIKE '"+username+"' AND user.password LIKE '"+password+"'");
            users_tbl Result = (users_tbl) HQL.list().get(0);
            return Result;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public users_tbl LoginByToken(String token)
    {
        try
        {
            DBRepository.session = DBRepository.SF.openSession();
            DBRepository.TA = DBRepository.session.beginTransaction();
            Query HQL = DBRepository.session.createQuery("FROM users_tbl user WHERE user.login_token LIKE '"+token+"'");
            users_tbl Result = (users_tbl) HQL.list().get(0);
            return Result;
        }
        catch (Exception e)
        {
            return null;
        }
    }


    @Override
    public users_tbl NewUser(String name, String family, String username, String password, String LoginToken, String phone, String email, String bio, String date_time, String user_type, String status, String website, String address, String google_auth, file_tbl profile_image_file_id, city_tbl city_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        password= HashService.HashText(password);
        LoginToken = HashService.HashText(name+date_time+family+username+password);

        users_tbl NUser=new users_tbl(
            username,
            password,
            name,
            family,
            phone,
            email,
            bio,
            LoginToken,
            date_time,
            user_type,
            status,
            website,
            address,
            "",
            profile_image_file_id,
            city_id
        );

        DBRepository.session.save(NUser);
        DBRepository.TA.commit();

        return NUser;
    }

    @Override
    public users_tbl UpdateUser(int id, String name, String family, String username, String password, String LoginToken, String phone, String email, String bio, String date_time, String user_type, String status, String website, String address, String google_auth, file_tbl profile_image_file_id, city_tbl city_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        users_tbl NUser=GetUser(id);

        NUser.setUsername(username);

        if(!password.equals("")) {
            password= HashService.HashText(password);
            NUser.setPassword(password);
        }

        NUser.setName(name);
        NUser.setFamily(family);
        NUser.setEmail(email);
        NUser.setBio(bio);
        NUser.setPhone(phone);
        NUser.setUser_type(user_type);
        NUser.setStatus(status);
        NUser.setWebsite(website);
        NUser.setAddress(address);
        NUser.setProfile_image_id(profile_image_file_id);
        NUser.setCity_id(city_id);

        DBRepository.session.update(NUser);
        DBRepository.TA.commit();

        return NUser;
    }

}
