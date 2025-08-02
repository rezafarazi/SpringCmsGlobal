package com.sorapp.jowncms.jowncms.Controllers.Admin;

import com.sorapp.jowncms.jowncms.Function.Encript.EncriptService;
import com.sorapp.jowncms.jowncms.Middleware.AdminAuthCheckMiddleware;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.FileUpload_Repo;
import com.sorapp.jowncms.jowncms.Service.Category.Category_Service;
import com.sorapp.jowncms.jowncms.Service.City.City_Service;
import com.sorapp.jowncms.jowncms.Service.Notification.Notification_Service;
import com.sorapp.jowncms.jowncms.Service.Post.Post_Service;
import com.sorapp.jowncms.jowncms.Service.Report.Report_Service;
import com.sorapp.jowncms.jowncms.Service.Reports.Reports_Service;
import com.sorapp.jowncms.jowncms.Service.Setting.Setting_Service;
import com.sorapp.jowncms.jowncms.Service.Transation.Transation_Service;
import com.sorapp.jowncms.jowncms.Service.UserType.UserType_Service;
import com.sorapp.jowncms.jowncms.Service.Users.Users_Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/Admin")
public class AdminReportController
{


    @Autowired
    EncriptService Encription;

    @Autowired
    Users_Service User_Services;

    @Autowired
    Category_Service Category_Services;

    @Autowired
    Post_Service Posts_Services;

    @Autowired
    City_Service City_Services;

    @Autowired
    FileUpload_Repo FileUploadRepo;

    @Autowired
    Setting_Service Setting_Services;

    @Autowired
    UserType_Service UserType_Services;

    @Autowired
    Notification_Service Notification_Services;

    @Autowired
    Transation_Service Transation_Services;

    @Autowired
    Report_Service Report_Services;

    @Autowired
    Reports_Service Reports_Services;

    @Value("${FileManagerRoot}")
    String FileManager;



    /****************Reports Start****************/

    @RequestMapping(value = "/Panel/Reports")
    public ModelAndView Reports(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.setViewName("Admin/reports");
        response.addObject("user_data", AdminAuthCheckMiddleware.user_data);
        response.addObject("reports",Reports_Services.GetAllUnseenReports());
        return response;
    }

    @RequestMapping(value = "/Panel/Reports/ReportSeen/{id}")
    public String ReportSeen(HttpServletRequest request,@PathVariable String id)
    {
        Reports_Services.SeenReport(Integer.parseInt(id));
        return "redirect:" + request.getHeader("referer");
    }

    /****************Reports End****************/





    /****************New Report Start****************/

    @RequestMapping(value = "/Panel/NewReport")
    public ModelAndView NewReport(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.setViewName("Admin/new_report");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        response.addObject("all_reports",Report_Services.GetAllReports());
        return response;
    }

    @RequestMapping(value = "/Panel/NewReport/Add",method = RequestMethod.POST)
    public String NewReportAdd(HttpServletRequest request,String new_report)
    {
        Report_Services.NewReport(new_report);
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/Panel/NewReport/Remove/{id}")
    public String NewReportRemove(HttpServletRequest request,@PathVariable String id)
    {
        Report_Services.RemoveReport(Integer.parseInt(id));
        return "redirect:" + request.getHeader("referer");
    }

    /****************New Report End****************/







}
