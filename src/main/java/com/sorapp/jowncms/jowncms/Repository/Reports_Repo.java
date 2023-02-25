package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.reports_tbl;
import com.sorapp.jowncms.jowncms.Models.report_tbl;
import com.sorapp.jowncms.jowncms.Models.posts_tbl;

public interface Reports_Repo
{

    //New Report
    public reports_tbl NewReport(String content,String date_time,String condition,report_tbl report_id,posts_tbl post_id);

    //Remove Report
    public void RemoveReport(int id);

    //Seen Report
    public void SeenReport(int id);

}
