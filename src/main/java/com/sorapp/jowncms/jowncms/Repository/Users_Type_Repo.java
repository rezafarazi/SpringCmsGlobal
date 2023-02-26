package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.userstype_tbl;

import java.util.ArrayList;

public interface Users_Type_Repo
{

    //Get All User Type
    public ArrayList<userstype_tbl> GetAllUserType();

    //Get User Type
    public userstype_tbl GetUserType(int user_type_id);

    //New Type
    public userstype_tbl NewType(String title,String en_title);

    //Update Type
    public userstype_tbl UpdateType(int id,String title,String en_title);

    //Remove Type
    public void RemoveType(int id);

}
