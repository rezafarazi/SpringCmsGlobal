package com.sorapp.jowncms.jowncms.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.hash.Hashing;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Category.CatsRepo;
import com.sorapp.jowncms.jowncms.Repository.Users.UsersRepo;
import com.sorapp.jowncms.jowncms.Service.Encript.EncriptService;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/Admin")
public class AdminController
{

    //Global Variabels Start
    @Autowired
    UsersRepo users_repository;

    @Autowired
    EncriptService Encription;

    @Autowired
    CatsRepo categories_repository;

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
    public ModelAndView NewPost(HttpServletRequest request)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","پست جدید");
        response.setViewName("Admin/newpost");
        response.addObject("user_data",user_data);
        return response;
    }
    /****************Categories End****************/




    /****************Categories Start****************/
    @RequestMapping("//PanelAllPosts")
    public ModelAndView AllPosts(HttpServletRequest request)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","همه پست ها");
        response.setViewName("Admin/allposts");
        response.addObject("user_data",user_data);
        return response;
    }
    /****************Categories End****************/




    /****************Categories Start****************/
    @RequestMapping("/Panel/Categories")
    public ModelAndView Categories(HttpServletRequest request)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","دسته بندی ها");
        response.setViewName("Admin/categories");
        response.addObject("user_data",user_data);
        response.addObject("categories",categories_repository.GetAllCategories());
        return response;
    }

    @PostMapping("Panel/Categoreis/New")
    public String NewCategory(HttpServletResponse response,HttpServletRequest request,String Category_Name,String Root_Cat,String Visibleity)
    {
        Get_User_Data(request);
        categories_repository.InsertNewCategory(Category_Name,Root_Cat,Visibleity,user_data.getId());
        return "redirect:/Admin/Panel/Categories";
    }

    @RequestMapping(value = "Panel/Categoreis/Delete/{id}",method = RequestMethod.GET)
    public String DeleteCategory(HttpServletResponse response,HttpServletRequest request,@PathVariable("id") String id)
    {
        Get_User_Data(request);
        categories_repository.DeleteCategory(Integer.parseInt(id));
        return "redirect:/Admin/Panel/Categories";
    }

    @RequestMapping(value = "Panel/Categoreis/Update/{id}",method = RequestMethod.GET)
    public ModelAndView UpdateCategory(HttpServletRequest request,@PathVariable("id") String id)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","دسته بندی ها");
        response.setViewName("Admin/categories");
        response.addObject("user_data",user_data);
        response.addObject("categories",categories_repository.GetAllCategories());
        response.addObject("update_category_categories",categories_repository.GetCategory(Integer.parseInt(id)));
        return response;
    }

    @RequestMapping(value = "Panel/Categoreis/Update/Done",method = RequestMethod.POST)
    public String DeleteCategory(HttpServletResponse response,HttpServletRequest request,String Category_Name,String Root_Cat,String Visibleity,String Category_Id)
    {
        Get_User_Data(request);
        categories_repository.UpdateCategory(Integer.parseInt(Category_Id),Category_Name,Root_Cat,Visibleity);
        return "redirect:/Admin/Panel/Categories";
    }
    /****************Categories End****************/




    /****************Chart Start****************/
    @RequestMapping("/Panel/Chart")
    public ModelAndView Chart(HttpServletRequest request)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","آمار");
        response.setViewName("Admin/chart");
        response.addObject("user_data",user_data);
        return response;
    }
    /****************Chart End****************/




    /****************Usermanager Start****************/
    @RequestMapping("/Panel/UserManager")
    public ModelAndView UserManager(HttpServletRequest request)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت کاربران");
        response.setViewName("Admin/usermanager");
        response.addObject("user_data",user_data);
        return response;
    }
    /****************Usermanager End****************/




    /****************Profile Start****************/
    @RequestMapping("/Panel/FileManager")
    public ModelAndView FileManager(HttpServletRequest request)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت فایل");
        response.setViewName("Admin/filemanager");
        response.addObject("user_data",user_data);
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
    public ModelAndView Settings(HttpServletRequest request)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","تنظیمات");
        response.setViewName("Admin/settings");
        response.addObject("user_data",user_data);
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
