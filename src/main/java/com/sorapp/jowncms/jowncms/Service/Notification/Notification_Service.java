package com.sorapp.jowncms.jowncms.Service.Notification;

import com.sorapp.jowncms.jowncms.Models.notifications_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Notification_Repo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Notification_Service implements Notification_Repo
{


    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<notifications_tbl> GetAllUserNotifications(int user_id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM notifications_tbl WHERE notifications_tbl.user_id.id="+user_id).list();
        }
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

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(Notification);
            session.getTransaction().commit();
        }

        return Notification;
    }

    @Override
    public void SeenNotification(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            notifications_tbl Notification = (notifications_tbl) session.createQuery("FROM notifications_tbl WHERE notifications_tbl.id="+id).list().get(0);
            Notification.setSeen(true);

            session.beginTransaction();
            session.update(Notification);
            session.getTransaction().commit();
        }
    }
}
