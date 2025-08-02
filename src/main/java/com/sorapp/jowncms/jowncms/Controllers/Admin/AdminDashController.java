package com.sorapp.jowncms.jowncms.Controllers.Admin;

import com.sorapp.jowncms.jowncms.Function.Encript.EncriptService;
import com.sorapp.jowncms.jowncms.Middleware.AdminAuthCheckMiddleware;
import com.sorapp.jowncms.jowncms.Models.settings_tbl;
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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/Admin")
public class AdminDashController
{


    @Autowired
    Setting_Service Setting_Services;




    /****************Dashboard Start****************/
    @RequestMapping("/Panel/Dashboard")
    public ModelAndView Dashboard(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.setViewName("Admin/dashboard");
        response.addObject("user_data", AdminAuthCheckMiddleware.user_data);
        return response;
    }
    /****************Dashboard End****************/






    /****************Chart Start****************/
    @RequestMapping("/Panel/Charts")
    public ModelAndView Chart(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.setViewName("Admin/chart");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        return response;
    }
    /****************Chart End****************/






    /****************Setting Start****************/

    @RequestMapping("/Panel/Settings")
    public ModelAndView Settins()
    {
        settings_tbl settings_tbl=Setting_Services.GetSetting();
        JSONObject jsonObject=new JSONObject(settings_tbl.getParametrs());

        ModelAndView response=new ModelAndView();

        response.addObject("logo",jsonObject.get("logo"));
        response.addObject("law",jsonObject.get("law"));
        response.addObject("slider",jsonObject.get("slider"));
        response.addObject("banner",jsonObject.get("banner"));
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        response.setViewName("Admin/settings");
        return response;
    }

    @RequestMapping(value = "/Panel/Settings/Done",method = RequestMethod.POST)
    public String Settings(String slider,String banner,String law,String icon)
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("logo",icon);
        jsonObject.put("law",law);
        jsonObject.put("slider",slider);
        jsonObject.put("banner",banner);
        settings_tbl settings_tbl=Setting_Services.GetSetting();
        Setting_Services.NewSetting(jsonObject.toString(),settings_tbl.getDiscountall());
        return "redirect:/Admin/Panel/Settings";
    }

    /****************Setting End****************/



}
