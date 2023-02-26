package com.sorapp.jowncms.jowncms.Service.Notification;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.notifications_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Notification_Repo;
import org.hibernate.query.Query;

import java.util.List;

public class Notification_Service implements Notification_Repo
{
    @Override
    public List<notifications_tbl> GetAllUserNotifications(int user_id)
    {
        Query HQL= DBRepository.session.createQuery("FROM notifications_tbl WHERE notifications_tbl.user_id.id="+user_id);
        List<notifications_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public notifications_tbl NewNotification(String title, String content, String link, String from, boolean seen, users_tbl user_id)
    {
        notifications_tbl Notification=new notifications_tbl();
        Notification.setTitle(title);
        Notification.setContent(content);
        Notification.setFrom(from);
        Notification.setLink(link);
        Notification.setSeen(seen);
        Notification.setUser_id(user_id);

        DBRepository.session.save(Notification);
        DBRepository.TA.commit();

        return Notification;
    }

    @Override
    public void SeenNotification(int id) {
        Query HQL=DBRepository.session.createQuery("FROM notifications_tbl WHERE notifications_tbl.id="+id);
        notifications_tbl Result = (notifications_tbl) HQL.list().get(0);
        notifications_tbl Notification=Result;
        DBRepository.session.update(Notification);
        DBRepository.TA.commit();
    }
}
