package com.sorapp.jowncms.jowncms.Service.Report;

import com.sorapp.jowncms.jowncms.Models.report_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Report_Repo;
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
public class Report_Service implements Report_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<report_tbl> GetAllReports()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM report_tbl reports WHERE reports.delete_flag=false").list();
        }
    }

    @Override
    public report_tbl Report(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (report_tbl) session.createQuery("FROM report_tbl report WHERE report.id="+id).list().get(0);
        }
    }

    @Override
    public report_tbl NewReport(String title)
    {
        report_tbl report=new report_tbl();
        report.setTitle(title);
        report.setDelete_flag(false);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(report);
            session.getTransaction().commit();
        }

        return report;
    }

    @Override
    public report_tbl UpdateReport(int id, String title, boolean delete_flag)
    {
        report_tbl report=Report(id);
        report.setTitle(title);
        report.setDelete_flag(delete_flag);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(report);
            session.getTransaction().commit();
        }

        return report;
    }

    @Override
    public void RemoveReport(int id)
    {
        report_tbl report=Report(id);
        report.setDelete_flag(true);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.update(report);
            session.getTransaction().commit();
        }

    }

}
