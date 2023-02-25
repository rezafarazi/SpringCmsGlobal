package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.report_tbl;

public interface Report_Repo
{

    //New Report
    public report_tbl NewReport(String title,boolean delete_flag);

    //Update Report
    public report_tbl UpdateReport(int id,String title,boolean delete_flag);

    //Remove Report
    public void UpdateReport(int id);

}
