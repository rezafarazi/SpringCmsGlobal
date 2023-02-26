package com.sorapp.jowncms.jowncms.Service.City;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Repository.City_Repo;
import org.hibernate.query.Query;

import java.util.List;

public class City_Service implements City_Repo
{

    @Override
    public city_tbl GetCity(int id)
    {
        Query HQL= DBRepository.session.createQuery("FROM city_tbl WHERE id"+id);
        city_tbl Result = (city_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public List<city_tbl> GetCites() {
        Query HQL= DBRepository.session.createQuery("FROM city_tbl");
        List<city_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public city_tbl NewCity(String name, int area, String status, String datetime) {

        city_tbl City=new city_tbl();
        City.setName(name);
        City.setArea(area);
        City.setStatus(status);
        City.setDatetime(datetime);

        DBRepository.session.save(City);
        DBRepository.TA.commit();

        return City;
    }

    @Override
    public city_tbl UpdateCity(int id, String name, int area, String status, String datetime)
    {
        city_tbl City=GetCity(id);
        City.setName(name);
        City.setArea(area);
        City.setStatus(status);
        City.setDatetime(datetime);

        DBRepository.session.update(City);
        DBRepository.TA.commit();

        return City;
    }

}
