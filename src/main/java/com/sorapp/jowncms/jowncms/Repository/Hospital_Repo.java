package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Models.hospital_tbl;

import java.util.ArrayList;
import java.util.List;

public interface Hospital_Repo
{

    //Get Hospital
    public hospital_tbl GetHospital(int id);

    //Get All Hospital
    public List<hospital_tbl> GetAllHospitals();

    //New hospital
    public hospital_tbl NewHospital(String name, String bio, String lat, String lng, String address, String start_time, String end_time, String tel, String status, city_tbl city_id);

    //Update hospital
    public hospital_tbl UpdateHospital(int id,String name, String bio, String lat, String lng, String address, String start_time, String end_time, String tel, String status, city_tbl city_id);

    //Remove Hospital
    public hospital_tbl RemoveHospital(int id);

}
