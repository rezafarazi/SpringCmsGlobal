package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.settings_tbl;

public interface Setting_Repo
{

    //New Setting
    public settings_tbl NewSetting(String parametrs,String discountall);

}
