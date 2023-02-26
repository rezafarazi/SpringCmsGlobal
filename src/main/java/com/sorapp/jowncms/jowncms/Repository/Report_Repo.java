package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.report_tbl;

import java.util.ArrayList;

public interface Report_Repo
{

    //Get All Reports
    public ArrayList<report_tbl> GetAllReports();

    //Get Report
    public report_tbl Report(int id);

    //New Report
    public report_tbl NewReport(String title,boolean delete_flag);

    //Update Report
    public report_tbl UpdateReport(int id,String title,boolean delete_flag);

    //Remove Report
    public void UpdateReport(int id);

}
