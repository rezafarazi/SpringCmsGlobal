package com.sorapp.jowncms.jowncms.Controllers.Admin.Store;

import com.sorapp.jowncms.jowncms.Function.RandomString.RandomString;
import com.sorapp.jowncms.jowncms.Middleware.AdminAuthCheckMiddleware;
import com.sorapp.jowncms.jowncms.Models.settings_tbl;
import com.sorapp.jowncms.jowncms.Repository.FileUpload_Repo;
import com.sorapp.jowncms.jowncms.Service.Discount.Discount_Service;
import com.sorapp.jowncms.jowncms.Service.File.File_Service;
import com.sorapp.jowncms.jowncms.Service.Order.Order_service;
import com.sorapp.jowncms.jowncms.Service.Setting.Setting_Service;
import com.sorapp.jowncms.jowncms.Service.Store.Store_Service;
import com.sorapp.jowncms.jowncms.Service.StoreCategory.StoreCategory_Service;
import com.sorapp.jowncms.jowncms.Service.StoreImage.StoreImage_Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/Admin/Panel/Store")
public class StoreDiscountController
{

    @Autowired
    StoreCategory_Service Category_Services;

    @Autowired
    Store_Service Store_Services;

    @Autowired
    FileUpload_Repo FileUploadRepo;

    @Autowired
    Order_service Order_services;

    @Autowired
    File_Service File_Services;

    @Autowired
    Discount_Service Discount_Services;

    @Autowired
    StoreImage_Service StoreImage_Services;

    @Autowired
    Setting_Service Setting_Services;



    /****************Coupons Start****************/

    @RequestMapping(value = "/Coupon")
    public ModelAndView Coupons(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","کوپن ها");
        response.setViewName("Admin/coupons");
        response.addObject("user_data", AdminAuthCheckMiddleware.user_data);
        response.addObject("coupons",Discount_Services.GetAllDiscounts());
        return response;
    }


    @RequestMapping(value = "/Coupon/New",method = RequestMethod.POST)
    public String NewCoupon(HttpServletRequest request,String count)
    {
        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        String code=new RandomString().nextString();

        Discount_Services.NewDiscount(code,Integer.parseInt(count),"0",time);
        return "redirect:" + request.getHeader("referer");
    }


    @RequestMapping(value = "/Coupon/Remove/{id}")
    public String RemoveCoupon(HttpServletRequest request,@PathVariable String id)
    {
        Discount_Services.RemoveDiscount(Integer.parseInt(id));
        return "redirect:" + request.getHeader("referer");
    }

    /****************Coupons Start****************/






    /****************All Discount Start****************/

    @RequestMapping(value = "/Discount")
    public ModelAndView AllDiscount(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","تخفیف برای همه");
        response.setViewName("Admin/alldiscount");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        response.addObject("discount",Setting_Services.GetSetting().getDiscountall());
        return response;
    }

    @RequestMapping(value = "/Discount/New",method = RequestMethod.POST)
    public String NewDiscount(HttpServletRequest request,String discount)
    {
        settings_tbl setting = Setting_Services.GetSetting();
        Setting_Services.NewSetting(setting.getParametrs(),discount);
        return "redirect:" + request.getHeader("referer");
    }


    @RequestMapping(value = "/Discount/Remove")
    public String NewDiscount(HttpServletRequest request)
    {
        settings_tbl setting = Setting_Services.GetSetting();
        Setting_Services.NewSetting(setting.getParametrs(),"0");
        return "redirect:" + request.getHeader("referer");
    }

    /****************All Discount End****************/




}
