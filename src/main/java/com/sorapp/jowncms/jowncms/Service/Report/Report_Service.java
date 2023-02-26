package com.sorapp.jowncms.jowncms.Service.Report;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.report_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Report_Repo;
import org.hibernate.query.Query;

import java.util.List;

public class Report_Service implements Report_Repo
{

    @Override
    public List<report_tbl> GetAllReports()
    {
        Query HQL=DBRepository.session.createQuery("FROM report_tbl");
        List<report_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public report_tbl Report(int id) {
        Query HQL= DBRepository.session.createQuery("FROM report_tbl");
        report_tbl Result = (report_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public report_tbl NewReport(String title)
    {
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
        report_tbl report=Report(id);
        report.setTitle(title);
        report.setDelete_flag(delete_flag);

        DBRepository.session.save(report);
        DBRepository.TA.commit();

        return report;
    }

    @Override
    public void RemoveReport(int id) {
        report_tbl report=Report(id);
        report.setDelete_flag(true);

        DBRepository.session.save(report);
        DBRepository.TA.commit();
    }

}
