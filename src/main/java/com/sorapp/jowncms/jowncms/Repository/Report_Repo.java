package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.report_tbl;

import java.util.ArrayList;
import java.util.List;

public interface Report_Repo
{

    //Get All Reports
    public List<report_tbl> GetAllReports();

    //Get Report
    public report_tbl Report(int id);

    //New Report
    public report_tbl NewReport(String title);

    //Update Report
    public report_tbl UpdateReport(int id,String title,boolean delete_flag);

    //Remove Report
    public void RemoveReport(int id);

}
