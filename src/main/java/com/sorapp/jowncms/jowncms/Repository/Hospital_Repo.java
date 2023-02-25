package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Models.hospital_tbl;

public interface Hospital_Repo
{

    //New hospital
    public hospital_tbl NewHospital(String name, String bio, String lat, String lng, String address, String start_time, String end_time, String tel, String status, city_tbl city_id);

    //Update hospital
    public hospital_tbl UpdateHospital(int id,String name, String bio, String lat, String lng, String address, String start_time, String end_time, String tel, String status, city_tbl city_id);

    //Remove Hospital
    public hospital_tbl RemoveHospital(int id);

}
