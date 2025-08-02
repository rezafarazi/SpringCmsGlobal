package com.sorapp.jowncms.jowncms.Controllers.Admin;

import com.sorapp.jowncms.jowncms.Function.Encript.EncriptService;
import com.sorapp.jowncms.jowncms.Middleware.AdminAuthCheckMiddleware;
import com.sorapp.jowncms.jowncms.Models.categories_tbl;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/Admin")
public class AdminPostController
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



    /****************Categories Start****************/
    @RequestMapping("/Panel/Categories")
    public ModelAndView Categories(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.setViewName("Admin/categories");
        response.addObject("user_data", AdminAuthCheckMiddleware.user_data);
        response.addObject("categories",Category_Services.GetCategories());
        return response;
    }

    @PostMapping(value = "/Panel/Categories/New")
    public String NewCategory(HttpServletResponse response, HttpServletRequest request, String Category_Name, String Root_Cat, String Visibleity, String Cat_link, String icon_address)
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
        response.setViewName("Admin/categories");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
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





    /****************NewPost Start****************/
    @RequestMapping("/Panel/NewPost")
    public ModelAndView NewPost(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.setViewName("Admin/newpost");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        response.addObject("categories",Category_Services.GetCategories());
        return response;
    }

    @RequestMapping("/Panel/NewPost/Done")
    public String NewPostSubmit(HttpServletRequest request,String title,String category,String post_image_address,String Visibleity,String Content)
    {
        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        categories_tbl cat = Category_Services.GetCategory(Integer.parseInt(category));

        Posts_Services.NewPost(title,Content,post_image_address,time,Visibleity,AdminAuthCheckMiddleware.user_data,cat);
        return "redirect:/Admin/Panel/NewPost";
    }
    /****************NewPost End****************/




    /****************All Posts Start****************/
    @RequestMapping("/Panel/AllPosts")
    public ModelAndView AllPosts(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.setViewName("Admin/allposts");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        response.addObject("posts",Posts_Services.GetAllPosts());
        return response;
    }

    @RequestMapping("/Panel/Post/Delete/{id}")
    public String DeletePost(HttpServletRequest request,@PathVariable String id)
    {
        Posts_Services.RemovePost(Integer.parseInt(id));
        return "redirect:/Admin/Panel/AllPosts";
    }

    @RequestMapping("/Panel/Post/Edit/{id}")
    public ModelAndView AllPosts(HttpServletRequest request,@PathVariable String id)
    {
        System.out.println("salam");
        ModelAndView response=new ModelAndView();
        response.setViewName("Admin/editpost");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        response.addObject("categories",Category_Services.GetCategories());
        response.addObject("post",Posts_Services.GetPost(Integer.parseInt(id)));
        return response;
    }

    @RequestMapping(value = "/Panel/Post/Edit/Done",method = RequestMethod.POST)
    public String EditDonePost(HttpServletRequest request,String id,String title,String category,String img_adrs,String visible,String cntent)
    {
        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        categories_tbl cat = Category_Services.GetCategory(Integer.parseInt(category));

        Posts_Services.UpdatePost(Integer.parseInt(id),title,cntent,img_adrs,time,visible,AdminAuthCheckMiddleware.user_data,cat);
        return "redirect:/Admin/Panel/Post/Edit/"+id;
    }
    /****************All Posts End****************/




}
