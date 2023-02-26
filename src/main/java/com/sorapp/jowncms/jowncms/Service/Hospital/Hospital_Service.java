package com.sorapp.jowncms.jowncms.Service.Hospital;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Models.hospital_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Hospital_Repo;
import org.hibernate.query.Query;

import java.util.List;

public class Hospital_Service implements Hospital_Repo
{
    @Override
    public hospital_tbl GetHospital(int id)
    {
        Query HQL= DBRepository.session.createQuery("FROM hospital_tbl WHERE hospital_tbl.id="+id);
        hospital_tbl Result = (hospital_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public List<hospital_tbl> GetAllHospitals()
    {
        Query HQL=DBRepository.session.createQuery("FROM hospital_tbl");
        List<hospital_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public hospital_tbl NewHospital(String name, String bio, String lat, String lng, String address, String start_time, String end_time, String tel, String status, city_tbl city_id)
    {
        hospital_tbl Hospital=new hospital_tbl();
        Hospital.setAddress(address);
        Hospital.setBio(bio);
        Hospital.setName(name);
        Hospital.setStatus(status);
        Hospital.setLat(lat);
        Hospital.setLat(lng);
        Hospital.setStart_time(start_time);
        Hospital.setEnd_time(end_time);
        Hospital.setCity_id(city_id);
        Hospital.setTel(tel);

        DBRepository.session.save(Hospital);
        DBRepository.TA.commit();

        return Hospital;
    }

    @Override
    public hospital_tbl UpdateHospital(int id, String name, String bio, String lat, String lng, String address, String start_time, String end_time, String tel, String status, city_tbl city_id)
    {
        hospital_tbl Hospital=GetHospital(id);

        Hospital.setAddress(address);
        Hospital.setBio(bio);
        Hospital.setName(name);
        Hospital.setStatus(status);
        Hospital.setLat(lat);
        Hospital.setLat(lng);
        Hospital.setStart_time(start_time);
        Hospital.setEnd_time(end_time);
        Hospital.setCity_id(city_id);
        Hospital.setTel(tel);

        DBRepository.session.update(Hospital);
        DBRepository.TA.commit();

        return Hospital;
    }

    @Override
    public void RemoveHospital(int id)
    {
        hospital_tbl Hospital=GetHospital(id);
        Hospital.setStatus("0");
        DBRepository.session.update(Hospital);
        DBRepository.TA.commit();
    }
}
