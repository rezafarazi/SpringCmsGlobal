package com.sorapp.jowncms.jowncms.Service.UserType;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.discount_code_tbl;
import com.sorapp.jowncms.jowncms.Models.userstype_tbl;
import com.sorapp.jowncms.jowncms.Repository.Users_Type_Repo;
import org.hibernate.query.Query;
import org.hibernate.usertype.UserType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserType_Service implements Users_Type_Repo
{

    @Override
    public List<userstype_tbl> GetAllUserType()
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM userstype_tbl");
        List<userstype_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public userstype_tbl GetUserType(int user_type_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM categories_tbl WHERE userstype_tbl.id="+user_type_id);
        userstype_tbl Result = (userstype_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public userstype_tbl NewType(String title, String en_title)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        userstype_tbl NUserType=new userstype_tbl();
        NUserType.setTitle(title);
        NUserType.setEn_title(en_title);

        DBRepository.session.save(NUserType);
        DBRepository.TA.commit();

        return NUserType;
    }

    @Override
    public userstype_tbl UpdateType(int id, String title, String en_title)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        userstype_tbl NUserType=GetUserType(id);
        NUserType.setTitle(title);
        NUserType.setEn_title(en_title);

        DBRepository.session.update(NUserType);
        DBRepository.TA.commit();

        return NUserType;
    }

    @Override
    public void RemoveType(int id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();
        userstype_tbl NUserType=GetUserType(id);
        DBRepository.session.remove(NUserType);
        DBRepository.TA.commit();
    }

}
