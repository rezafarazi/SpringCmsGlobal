package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.userstype_tbl;

public interface Users_Type_Repo
{

    //New Type
    public userstype_tbl NewType(String title,String en_title);

    //Update Type
    public userstype_tbl UpdateType(int id,String title,String en_title);

    //Remove Type
    public void UpdateType(int id);

}
