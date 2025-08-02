package com.sorapp.jowncms.jowncms.Service.Setting;

import com.sorapp.jowncms.jowncms.Models.settings_tbl;
import com.sorapp.jowncms.jowncms.Models.store_images_tbl;
import com.sorapp.jowncms.jowncms.Repository.Setting_Repo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Setting_Service implements Setting_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public settings_tbl GetSetting()
    {
        try(Session session=sessionFactory.openSession())
        {
            return (settings_tbl) session.createQuery("FROM settings_tbl").list().get(0);
//            return session.get(hub_tbl.class, id);
        }
    }

    @Override
    public settings_tbl NewSetting(String parametrs, String discountall)
    {
        settings_tbl settings_tbl=new settings_tbl();

        settings_tbl.setDiscountall(discountall);
        settings_tbl.setParametrs(parametrs);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(settings_tbl);
            session.getTransaction().commit();
        }

        return settings_tbl;
    }

}
