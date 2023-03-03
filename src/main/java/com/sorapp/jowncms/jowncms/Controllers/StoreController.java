package com.sorapp.jowncms.jowncms.Controllers;

import com.sorapp.jowncms.jowncms.Repository.Store_Categories_Repo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/Admin/Panel/Store")
public class StoreController
{


    @Autowired
    Store_Categories_Repo Category_Services;



    /****************Categories Start****************/
    @RequestMapping("/CategoryStore")
    public ModelAndView Categories(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","دسته بندی ها");
        response.setViewName("Admin/storecategories");
        response.addObject("user_data",AdminController.user_data);
        response.addObject("categories",Category_Services.GetCategories());
        return response;
    }

    @PostMapping(value = "/CategoryStore/New")
    public String NewCategory(HttpServletResponse response, HttpServletRequest request, String Category_Name, String Root_Cat, String Visibleity, String Cat_link, String icon_address)
    {
        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        Category_Services.NewCategory(Category_Name,Integer.parseInt(Root_Cat),icon_address,time,Cat_link,Visibleity,"0");
        return "redirect:/Admin/Panel/Store/CategoryStore";
    }

    @RequestMapping(value = "/CategoryStore/Delete/{id}",method = RequestMethod.GET)
    public String DeleteCategory(HttpServletResponse response,HttpServletRequest request,@PathVariable("id") String id)
    {
        Category_Services.RemoveCategory(Integer.parseInt(id));
        return "redirect:/Admin/Panel/Store/CategoryStore";
    }

    @RequestMapping(value = "/CategoryStore/Update/{id}",method = RequestMethod.GET)
    public ModelAndView UpdateCategory(HttpServletRequest request,@PathVariable("id") String id)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","دسته بندی ها");
        response.setViewName("Admin/storecategories");
        response.addObject("user_data",AdminController.user_data);
        response.addObject("categories",Category_Services.GetCategories());
        response.addObject("update_category_categories",Category_Services.GetCategory(Integer.parseInt(id)));
        return response;
    }

    @RequestMapping(value = "/CategoryStore/Update/Done",method = RequestMethod.POST)
    public String UpdateCategory(HttpServletResponse response,HttpServletRequest request,String Category_Name,String Root_Cat,String Visibleity,String Category_Id,String Cat_link,String icon_address)
    {
        Category_Services.UpdateCategory(Integer.parseInt(Category_Id),Category_Name,Integer.parseInt(Root_Cat),icon_address,Cat_link,Visibleity,"0");
        return "redirect:/Admin/Panel/Store/CategoryStore";
    }
    /****************Categories End****************/



}
