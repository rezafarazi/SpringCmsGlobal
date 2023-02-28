package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;

import java.util.ArrayList;
import java.util.List;

public interface User_Repo
{

    //Get All Users
    public List<users_tbl> GetAllUsers();

    //Get User
    public users_tbl GetUser(int id);

    //Login User
    public users_tbl Login(String username,String password);

    //Login User
    public users_tbl LoginByToken(String token);

    //Insert
    public users_tbl NewUser(String name, String family, String username, String password, String LoginToken, String phone, String email, String bio, String date_time, String user_type, String status, String website, String address, String google_auth, file_tbl profile_image_file_id, city_tbl city_id);

    //Update
    public users_tbl UpdateUser(int id,String name, String family, String username, String password, String phone, String email, String bio, String date_time, String user_type, String status, String website, String address, file_tbl profile_image_file_id, city_tbl city_id);

    //Active 2F
    public void Google2F(users_tbl user_id,String State,String Key);

}
