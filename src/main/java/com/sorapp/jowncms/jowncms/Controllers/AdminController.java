package com.sorapp.jowncms.jowncms.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.hash.Hashing;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Users.UsersRepo;
import com.sorapp.jowncms.jowncms.Service.Encript.EncriptService;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
@RequestMapping("/Admin")
public class AdminController
{

    //Global Variabels Start
    @Autowired
    UsersRepo users_repository;

    @Autowired
    EncriptService Encription;

    users_tbl user_data;

    private static final Logger LOGGER=LoggerFactory.getLogger(AdminController.class);




    /*************************Login Start**************************/
    @RequestMapping("/Login")
    public ModelAndView Login(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","ورود - پنل کاربری");
        response.setViewName("Admin/login");
        return response;
    }

    @PostMapping("/Login")
    public String Login(String username, String password, HttpServletResponse response)
    {
        try
        {
            String pass = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
            users_tbl user_data = users_repository.LOGIN_USER(username, pass);
            if (user_data != null)
            {
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(user_data);

                //Most Change This
                //String Berar_Token = Base64.getEncoder().encodeToString(json.getBytes());

                String Berar_Token = Encription.ENC(json);

                response.addCookie(new Cookie("auth",Berar_Token));
                return "redirect:/Admin/Panel/Dashboard";
            }
            else
            {
                return "redirect:/Admin/Login";
            }
        }
        catch (Exception e)
        {
            LOGGER.info(e.getMessage());
            return "redirect:/Admin/Login";
        }
    }
    /************************Login End**************************/




    /****************Dashboard Start****************/
    @RequestMapping("/Panel/Dashboard")
    public ModelAndView Dashboard(HttpServletRequest request)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","داشبورد");
        response.setViewName("Admin/dashboard");
        response.addObject("user_data",user_data);
        return response;
    }
    /****************Dashboard End****************/




    /****************Categories Start****************/
    @RequestMapping("/Panel/NewPost")
    public ModelAndView NewPost()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","پست جدید");
        response.setViewName("Admin/newpost");
        return response;
    }
    /****************Categories End****************/




    /****************Categories Start****************/
    @RequestMapping("//PanelAllPosts")
    public ModelAndView AllPosts()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","همه پست ها");
        response.setViewName("Admin/allposts");
        return response;
    }
    /****************Categories End****************/




    /****************Categories Start****************/
    @RequestMapping("/Panel/Categories")
    public ModelAndView Categories()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","دسته بندی ها");
        response.setViewName("Admin/categories");
        return response;
    }
    /****************Categories End****************/




    /****************Chart Start****************/
    @RequestMapping("/Panel/Chart")
    public ModelAndView Chart()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","آمار");
        response.setViewName("Admin/chart");
        return response;
    }
    /****************Chart End****************/




    /****************Usermanager Start****************/
    @RequestMapping("/Panel/UserManager")
    public ModelAndView UserManager()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت کاربران");
        response.setViewName("Admin/usermanager");
        return response;
    }
    /****************Usermanager End****************/




    /****************Profile Start****************/
    @RequestMapping("/Panel/FileManager")
    public ModelAndView FileManager()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت فایل");
        response.setViewName("Admin/filemanager");
        return response;
    }
    /****************Profile End****************/




    /****************Profile Start****************/
    @RequestMapping("/Panel/Profile")
    public ModelAndView Profile(HttpServletRequest request)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","پروفایل");
        response.addObject("user_data",user_data);
        response.setViewName("Admin/profile");
        return response;
    }

    @PostMapping("/Panel/Profile")
    public String ProfileSubmit(HttpServletRequest request,HttpServletResponse response,String username,String name,String family,String password,String email,String phone) throws Exception
    {
        Get_User_Data(request);

        //Check Password Start
        if(!password.trim().equals(""))
        {
            password=Hashing.sha256().hashString(password,StandardCharsets.UTF_8).toString();
        }
        else
        {
            password=user_data.getPassword();
        }
        //Check Password End

        //Update User Start
        users_repository.UpdateUser(
                user_data.getId(),
                username,
                name,
                family,
                email,
                phone,
                password,
                user_data.getLogin_token(),
                user_data.getGoogle2f(),
                user_data.getUser_type(),
                user_data.getActive_user_flag(),
                user_data.getCreate_datetime(),
                user_data.getAddress(),
                user_data.getCity());
        //Update User End

        SetAuthToken(response,request,username,password);

        return "redirect:/Admin/Panel/Profile";
    }
    /****************Profile End****************/




    /****************Settings Start****************/
    @RequestMapping("/Panel/Settings")
    public ModelAndView Settings()
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","تنظیمات");
        response.setViewName("Admin/settings");
        return response;
    }
    /****************Settings End****************/





    /****************Logout Start****************/
    @RequestMapping("/Panel/Logout")
    public String Logout(HttpServletResponse response)
    {
        Cookie auth_c = new Cookie("auth",null);
        auth_c.setPath("/Admin");
        response.addCookie(auth_c);
        return "redirect:/";
    }
    /****************Settings End****************/






    /****************Ftech User Data Start****************/
    private void Get_User_Data(HttpServletRequest request)
    {
        Cookie []cookies=request.getCookies();
        String auth="";

        for(int i=0;i<cookies.length;i++)
        {
            if(cookies[i].getName().equals("auth"))
            {
                auth=cookies[i].getValue();
            }
        }

        if(!auth.toString().trim().equals(""))
        {
            try
            {
                String json = Encription.DEC(auth);
                user_data = new users_tbl().FromJson(json);
            }
            catch (Exception e)
            {

            }
        }

    }
    /****************Ftech User Data End****************/



    /****************Set Auth Token Start****************/
    private void SetAuthToken(HttpServletResponse response,HttpServletRequest request,String username,String password) throws Exception
    {
        //Set Reload Auth Token
        users_tbl user_data = users_repository.LOGIN_USER(username, password);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user_data);
        String Berar_Token = Encription.ENC(json);
        Cookie auth_c = new Cookie("auth",Berar_Token);
        auth_c.setPath("/Admin");
        response.addCookie(auth_c);
    }
    /****************Set Auth Token End****************/



}
