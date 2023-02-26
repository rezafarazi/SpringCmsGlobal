package com.sorapp.jowncms.jowncms.Service.Setting;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.settings_tbl;
import com.sorapp.jowncms.jowncms.Models.store_images_tbl;
import com.sorapp.jowncms.jowncms.Repository.Setting_Repo;
import org.hibernate.query.Query;

public class Setting_Service implements Setting_Repo
{

    @Override
    public settings_tbl GetSetting()
    {
        Query HQL= DBRepository.session.createQuery("FROM settings_tbl");
        settings_tbl Result = (settings_tbl) HQL.list().get(HQL.list().size()-1);
        return Result;
    }

    @Override
    public settings_tbl NewSetting(String parametrs, String discountall)
    {
        settings_tbl settings_tbl=new settings_tbl();

        settings_tbl.setDiscountall(discountall);
        settings_tbl.setParametrs(parametrs);

        DBRepository.session.save(settings_tbl);
        DBRepository.TA.commit();

        return settings_tbl;
    }

}
