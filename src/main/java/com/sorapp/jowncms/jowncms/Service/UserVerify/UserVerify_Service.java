package com.sorapp.jowncms.jowncms.Service.UserVerify;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Models.userstype_tbl;
import com.sorapp.jowncms.jowncms.Repository.User_Verify_Repo;
import org.hibernate.query.Query;
import com.sorapp.jowncms.jowncms.Models.*;

import java.util.List;
import java.util.Random;

public class UserVerify_Service implements User_Verify_Repo
{

    @Override
    public boolean CheckVerifyCode(String hash, int user_id)
    {
        Query HQL= DBRepository.session.createQuery("FROM user_verify_tbl WHERE user_verify_tbl.user_id.id="+user_id);
        List<userstype_tbl> Result = HQL.getResultList();

        if(Result.size()>0)
        {
            Query HQLRemove= DBRepository.session.createQuery("FROM user_verify_tbl WHERE user_verify_tbl.user_id.id="+user_id);
            List<userstype_tbl> RemoveResult = HQLRemove.getResultList();
            DBRepository.session.remove(RemoveResult);
            DBRepository.TA.commit();
            return true;
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

        DBRepository.session.save(NUser);
        DBRepository.TA.commit();
    }

    @Override
    public void RemoveUserVerifyCode(users_tbl user_id)
    {
        Query HQLRemove= DBRepository.session.createQuery("FROM user_verify_tbl WHERE user_verify_tbl.user_id.id="+user_id);
        List<userstype_tbl> RemoveResult = HQLRemove.getResultList();
        DBRepository.session.remove(RemoveResult);
        DBRepository.TA.commit();
    }

}
