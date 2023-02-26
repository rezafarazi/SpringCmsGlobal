package com.sorapp.jowncms.jowncms.Service.Discount;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Models.discount_code_tbl;
import com.sorapp.jowncms.jowncms.Repository.Discount_Repo;
import org.hibernate.query.Query;

import java.util.List;

public class Discount_Service implements Discount_Repo
{

    @Override
    public List<discount_code_tbl> GetAllDiscounts()
    {
        Query HQL= DBRepository.session.createQuery("FROM discount_code_tbl");
        List<discount_code_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public discount_code_tbl NewDiscount(String code, int count, String delete_flag, String date)
    {
        discount_code_tbl Discount=new discount_code_tbl();
        Discount.setCode(code);
        Discount.setCount(count);
        Discount.setDate(date);
        Discount.setDelete_flag(delete_flag);

        DBRepository.session.save(Discount);
        DBRepository.TA.commit();

        return Discount;
    }

    @Override
    public void RemoveDiscount(int id)
    {
        Query HQL= DBRepository.session.createQuery("FROM discount_code_tbl WHERE id"+id);
        discount_code_tbl Result = (discount_code_tbl) HQL.list().get(0);

        DBRepository.session.remove(Result);
        DBRepository.TA.commit();
    }

}
