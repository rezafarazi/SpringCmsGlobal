package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.settings_tbl;

public interface Setting_Repo
{

    //Get Last setting
    public settings_tbl GetSetting();

    //New Setting
    public settings_tbl NewSetting(String parametrs,String discountall);

}
