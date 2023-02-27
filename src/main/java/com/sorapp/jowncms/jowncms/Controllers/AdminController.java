package com.sorapp.jowncms.jowncms.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.hash.Hashing;
import com.sorapp.jowncms.jowncms.Function.Encript.EncriptService;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Service.Category.Category_Service;
import com.sorapp.jowncms.jowncms.Service.Users.Users_Service;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/Admin")
public class AdminController
{

    @Autowired
    EncriptService Encription;

    @Autowired
    Users_Service User_Services;

    @Autowired
    Category_Service Category_Services;

    public static users_tbl user_data;

    private final GoogleAuthenticator gAuth;




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
    public String Login(String username, String password, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            String pass = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
            user_data = User_Services.Login(username, pass);
            if (user_data != null)
            {
                if(user_data.getGoogle_auth().equals("")) {
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    String json = ow.writeValueAsString(user_data);

                    //Most Change This
                    //String Berar_Token = Base64.getEncoder().encodeToString(json.getBytes());

                    String Berar_Token = Encription.ENC(json);

                    response.addCookie(new Cookie("auth", Berar_Token));
                    return "redirect:/Admin/Panel/Dashboard";
                }
                else
                {
                    request.getSession().setAttribute("lgt",user_data.getLogin_token());
                    return "redirect:/Admin/Login2F";
                }
            }
            else
            {
                return "redirect:/Admin/Login";
            }
        }
        catch (Exception e)
        {
            return "redirect:/Admin/Login";
        }
    }

    @RequestMapping("/Login2F")
    public ModelAndView Login2F(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","ورود - پنل کاربری");
        response.setViewName("Admin/login2f");
        return response;
    }

    @PostMapping("/Login2F")
    public String Login2FSubmit(HttpServletRequest request,HttpServletResponse response,String twofpass) throws Exception
    {
        String Token=request.getSession().getAttribute("lgt").toString();
        user_data = User_Services.LoginByToken(Token);

        boolean auth_status = gAuth.authorizeUser(user_data.getUsername(), Integer.parseInt(twofpass));

        if(auth_status)
        {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(user_data);

            String Berar_Token = Encription.ENC(json);

            response.addCookie(new Cookie("auth", Berar_Token));
            request.getSession().setAttribute("lgt",null);
            return "redirect:/Admin/Panel/Dashboard";
        }
        else
        {
            request.getSession().setAttribute("lgt",null);
            return "redirect:/";
        }
    }
    /************************Login End**************************/






    /****************Dashboard Start****************/
    @RequestMapping("/Panel/Dashboard")
    public ModelAndView Dashboard(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","داشبورد");
        response.setViewName("Admin/dashboard");
        response.addObject("user_data",user_data);
        return response;
    }
    /****************Dashboard End****************/









    /****************Categories Start****************/
    @RequestMapping("/Panel/Categories")
    public ModelAndView Categories(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","دسته بندی ها");
        response.setViewName("Admin/categories");
        response.addObject("user_data",user_data);
        response.addObject("categories",Category_Services.GetCategories());
        return response;
    }

    @PostMapping(value = "/Panel/Categories/New")
    public String NewCategory(HttpServletResponse response,HttpServletRequest request,String Category_Name,String Root_Cat,String Visibleity,String Cat_link,String icon_address)
    {
        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        Category_Services.NewCategory(Category_Name,Integer.parseInt(Root_Cat),icon_address,time,Cat_link,Visibleity,"0");
        return "redirect:/Admin/Panel/Categories";
    }

    @RequestMapping(value = "/Panel/Categories/Delete/{id}",method = RequestMethod.GET)
    public String DeleteCategory(HttpServletResponse response,HttpServletRequest request,@PathVariable("id") String id)
    {
        Category_Services.RemoveCategory(Integer.parseInt(id));
        return "redirect:/Admin/Panel/Categories";
    }

    @RequestMapping(value = "/Panel/Categories/Update/{id}",method = RequestMethod.GET)
    public ModelAndView UpdateCategory(HttpServletRequest request,@PathVariable("id") String id)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","دسته بندی ها");
        response.setViewName("Admin/categories");
        response.addObject("user_data",user_data);
        response.addObject("categories",Category_Services.GetCategories());
        response.addObject("update_category_categories",Category_Services.GetCategory(Integer.parseInt(id)));
        return response;
    }

    @RequestMapping(value = "/Panel/Categories/Update/Done",method = RequestMethod.POST)
    public String UpdateCategory(HttpServletResponse response,HttpServletRequest request,String Category_Name,String Root_Cat,String Visibleity,String Category_Id,String Cat_link,String icon_address)
    {
        Category_Services.UpdateCategory(Integer.parseInt(Category_Id),Category_Name,Integer.parseInt(Root_Cat),icon_address,Cat_link,Visibleity,"0");
        return "redirect:/Admin/Panel/Categories";
    }
    /****************Categories End****************/



}
