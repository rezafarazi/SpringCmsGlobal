package com.sorapp.jowncms.jowncms.Controllers.Admin.Store;

import com.sorapp.jowncms.jowncms.Middleware.AdminAuthCheckMiddleware;
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

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/Admin/Panel/Store")
public class SotreOrdersController
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



    /****************All Orders Start****************/

    @RequestMapping("/AllOrders")
    public ModelAndView AllOrders(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","همه سفارش ها");
        response.setViewName("Admin/allorders");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        return response;
    }

    @RequestMapping(value = "/AllOrders/Search",method = RequestMethod.POST)
    public ModelAndView AllOrdersSearch(HttpServletRequest request,String search)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","همه سفارش ها");
        response.setViewName("Admin/searchorder");
        response.addObject("user_data", AdminAuthCheckMiddleware.user_data);
        response.addObject("orders",Order_services.GetOrderSearchResult(Integer.parseInt(search)));
        return response;
    }


    @RequestMapping(value = "/CurrentOrders")
    public ModelAndView CurrentOrders(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","همه سفارش ها");
        response.setViewName("Admin/searchorder");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        response.addObject("orders",Order_services.GetCurrentOrders());
        return response;
    }

    /****************All Orders End****************/





    /****************Get Order Start****************/

    @RequestMapping(value = "/Order/{id}")
    public ModelAndView GetOrder(HttpServletRequest request,@PathVariable String id)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","همه سفارش ها");
        response.setViewName("Admin/order");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        response.addObject("order",Order_services.GetOrder(Integer.parseInt(id)));
        return response;
    }

    @RequestMapping(value = "/Order/SetRemove/{id}")
    public String GetRemoveOrder(HttpServletRequest request,@PathVariable String id)
    {
        Order_services.RemoveSetStatus(Integer.parseInt(id));
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/Order/SetPay/{id}")
    public String GetPayOrder(HttpServletRequest request,@PathVariable String id)
    {
        Order_services.PaySetStatus(Integer.parseInt(id), AdminAuthCheckMiddleware.user_data);
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/Order/SetOk/{id}")
    public String GetOkOrder(HttpServletRequest request,@PathVariable String id)
    {
        Order_services.OKSetStatus(Integer.parseInt(id));
        return "redirect:" + request.getHeader("referer");
    }

    /****************Get Order End****************/





}
