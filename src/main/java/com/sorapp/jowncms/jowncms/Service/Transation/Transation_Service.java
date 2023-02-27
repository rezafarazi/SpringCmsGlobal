package com.sorapp.jowncms.jowncms.Service.Transation;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.orders_tbl;
import com.sorapp.jowncms.jowncms.Models.transcation_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Models.userstype_tbl;
import com.sorapp.jowncms.jowncms.Repository.Transcation_Repo;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Transation_Service implements Transcation_Repo
{

    @Override
    public List<transcation_tbl> GetAllTransations()
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM transcation_tbl");
        List<transcation_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public void NewTransaction(String value, String date, orders_tbl order_id, users_tbl user_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        transcation_tbl tarnsaction=new transcation_tbl();
        tarnsaction.setDate(date);
        tarnsaction.setUser_id(user_id);
        tarnsaction.setOrder_id(order_id);
        tarnsaction.setValue(value);

        DBRepository.session.save(tarnsaction);
        DBRepository.TA.commit();

    }

}
