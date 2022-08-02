package com.sorapp.jowncms.jowncms.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Admin")
public class AdminController
{


    /*************************Login Start**************************/
    @RequestMapping("/Login")
    public ModelAndView Login()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","ورود - پنل کاربری");
        response.setViewName("Admin/login");
        return response;
    }
    /************************Login End**************************/




    /****************Dashboard Start****************/
    @RequestMapping("/Dashboard")
    public ModelAndView Dashboard()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","داشبورد");
        response.setViewName("Admin/dashboard");
        return response;
    }
    /****************Dashboard End****************/




    /****************Categories Start****************/
    @RequestMapping("/NewPost")
    public ModelAndView NewPost()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","پست جدید");
        response.setViewName("Admin/newpost");
        return response;
    }
    /****************Categories End****************/




    /****************Categories Start****************/
    @RequestMapping("/AllPosts")
    public ModelAndView AllPosts()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","همه پست ها");
        response.setViewName("Admin/allposts");
        return response;
    }
    /****************Categories End****************/




    /****************Categories Start****************/
    @RequestMapping("/Categories")
    public ModelAndView Categories()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","دسته بندی ها");
        response.setViewName("Admin/categories");
        return response;
    }
    /****************Categories End****************/




    /****************Chart Start****************/
    @RequestMapping("/Chart")
    public ModelAndView Chart()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","آمار");
        response.setViewName("Admin/chart");
        return response;
    }
    /****************Chart End****************/




    /****************Usermanager Start****************/
    @RequestMapping("/UserManager")
    public ModelAndView UserManager()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت کاربران");
        response.setViewName("Admin/usermanager");
        return response;
    }
    /****************Usermanager End****************/




    /****************Profile Start****************/
    @RequestMapping("/FileManager")
    public ModelAndView FileManager()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت فایل");
        response.setViewName("Admin/filemanager");
        return response;
    }
    /****************Profile End****************/




    /****************Profile Start****************/
    @RequestMapping("/Profile")
    public ModelAndView Profile()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","پروفایل");
        response.setViewName("Admin/profile");
        return response;
    }
    /****************Profile End****************/




    /****************Settings Start****************/
    @RequestMapping("/Settings")
    public ModelAndView Settings()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","تنظیمات");
        response.setViewName("Admin/settings");
        return response;
    }
    /****************Settings End****************/





    /****************Logout Start****************/
    @RequestMapping("/Logout")
    public String Logout()
    {
        return "redirect:http://google.com";
    }
    /****************Settings End****************/




}
