package com.sorapp.jowncms.jowncms.Service.Reports;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.posts_tbl;
import com.sorapp.jowncms.jowncms.Models.report_tbl;
import com.sorapp.jowncms.jowncms.Models.reports_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Reports_Repo;
import org.hibernate.query.Query;

import java.util.List;

public class Reports_Service implements Reports_Repo
{

    @Override
    public List<reports_tbl> GetAllUnseenReports()
    {
        Query HQL=DBRepository.session.createQuery("FROM reports_tbl");
        List<reports_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public reports_tbl GetReport(int id)
    {
        Query HQL= DBRepository.session.createQuery("FROM reports_tbl");
        reports_tbl Result = (reports_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public reports_tbl NewReport(String content, String date_time, String condition, report_tbl report_id, posts_tbl post_id,users_tbl user_id)
    {
        reports_tbl Reports=new reports_tbl();
        Reports.setDate_time(date_time);
        Reports.setCondition(condition);
        Reports.setReport_id(report_id);
        Reports.setPost_id(post_id);
        Reports.setUser_id(user_id);

        DBRepository.session.save(Reports);
        DBRepository.TA.commit();

        return Reports;
    }

    @Override
    public void RemoveReport(int id)
    {
        reports_tbl Reports=GetReport(id);
        DBRepository.session.remove(Reports);
        DBRepository.TA.commit();
    }

    @Override
    public void SeenReport(int id)
    {
        reports_tbl Reports=GetReport(id);
        Reports.setCondition("1");
        DBRepository.session.update(Reports);
        DBRepository.TA.commit();
    }

}
