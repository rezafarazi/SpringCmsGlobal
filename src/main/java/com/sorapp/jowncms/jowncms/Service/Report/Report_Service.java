package com.sorapp.jowncms.jowncms.Service.Report;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Report_Service implements Report_Repo
{

    @Override
    public List<report_tbl> GetAllReports()
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL=DBRepository.session.createQuery("FROM report_tbl");
        List<report_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public report_tbl Report(int id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM report_tbl");
        report_tbl Result = (report_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public report_tbl NewReport(String title)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        report_tbl report=new report_tbl();
        report.setTitle(title);
        report.setDelete_flag(false);

        DBRepository.session.save(report);
        DBRepository.TA.commit();

        return report;
    }

    @Override
    public report_tbl UpdateReport(int id, String title, boolean delete_flag)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        report_tbl report=Report(id);
        report.setTitle(title);
        report.setDelete_flag(delete_flag);

        DBRepository.session.save(report);
        DBRepository.TA.commit();

        return report;
    }

    @Override
    public void RemoveReport(int id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        report_tbl report=Report(id);
        report.setDelete_flag(true);

        DBRepository.session.save(report);
        DBRepository.TA.commit();
    }

}
