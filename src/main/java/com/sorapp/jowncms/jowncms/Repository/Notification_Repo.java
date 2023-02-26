package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.notifications_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;

import java.util.ArrayList;
import java.util.List;

public interface Notification_Repo
{

    //Get All Notifications
    public List<notifications_tbl> GetAllUserNotifications(int user_id);

    //New notification
    public notifications_tbl NewNotification(String title, String content, String link, String from, boolean seen, users_tbl user_id);

    //Seen notification
    public void SeenNotification(int id);

}
