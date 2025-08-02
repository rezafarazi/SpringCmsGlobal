package com.sorapp.jowncms.jowncms.Service.Reports;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.posts_tbl;
import com.sorapp.jowncms.jowncms.Models.report_tbl;
import com.sorapp.jowncms.jowncms.Models.reports_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Reports_Repo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Reports_Service implements Reports_Repo
{


    //Global variables
    @Autowired
    private SessionFactory sessionFactory;



    @Override
    public List<reports_tbl> GetAllUnseenReports()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM reports_tbl report WHERE report.condition=false").list();
        }
    }

    @Override
    public reports_tbl GetReport(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (reports_tbl) session.createQuery("FROM reports_tbl report WHERE report.id="+id).list().get(0);
        }
    }

    @Override
    public reports_tbl NewReport(String content, String date_time, boolean condition, report_tbl report_id, posts_tbl post_id,users_tbl user_id)
    {
        reports_tbl Reports=new reports_tbl();
        Reports.setDate_time(date_time);
        Reports.setCondition(condition);
        Reports.setReport_id(report_id);
        Reports.setPost_id(post_id);
        Reports.setUser_id(user_id);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(Reports);
            session.getTransaction().commit();
        }

        return Reports;
    }

    @Override
    public void RemoveReport(int id)
    {
        reports_tbl Reports=GetReport(id);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(Reports);
            session.getTransaction().commit();
        }
    }

    @Override
    public void SeenReport(int id)
    {
        reports_tbl Reports=GetReport(id);
        Reports.setCondition(true);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(Reports);
            session.getTransaction().commit();
        }
    }

}
