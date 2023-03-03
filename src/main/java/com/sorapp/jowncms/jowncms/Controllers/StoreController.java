package com.sorapp.jowncms.jowncms.Controllers;

import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.FileUpload_Repo;
import com.sorapp.jowncms.jowncms.Repository.File_Repo;
import com.sorapp.jowncms.jowncms.Repository.Store_Categories_Repo;
import com.sorapp.jowncms.jowncms.Service.File.File_Service;
import com.sorapp.jowncms.jowncms.Service.Store.Store_Service;
import com.sorapp.jowncms.jowncms.Service.StoreCategory.StoreCategory_Service;
import com.sorapp.jowncms.jowncms.Service.StoreImage.StoreImage_Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

import com.sorapp.jowncms.jowncms.Models.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/Admin/Panel/Store")
public class StoreController
{


    @Autowired
    StoreCategory_Service Category_Services;

    @Autowired
    Store_Service Store_Services;

    @Autowired
    FileUpload_Repo FileUploadRepo;

    @Autowired
    File_Service File_Services;

    @Autowired
    StoreImage_Service StoreImage_Services;

    public static users_tbl user_data;



    /****************Categories Start****************/
    @RequestMapping("/CategoryStore")
    public ModelAndView Categories(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","دسته بندی ها");
        response.setViewName("Admin/storecategories");
        response.addObject("user_data",user_data);
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
        response.addObject("user_data",user_data);
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





    /****************New Post Start****************/

    @RequestMapping("/NewStore")
    public ModelAndView NewStore(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","پست جدید فروشگاهی");
        response.setViewName("Admin/newstore");
        response.addObject("user_data",user_data);
        response.addObject("categories",Category_Services.GetCategories());

        return response;
    }

    @RequestMapping(value = "/NewStore/Done",method = RequestMethod.POST)
    public String NewStore(HttpServletRequest request, String title, String category, String visible, String price, String type, MultipartFile file,String all_images,String attr,String cntent)
    {
        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();
        store_tbl store;
        store_categories_tbl cat = Category_Services.GetCategory(Integer.parseInt(category));

        if(!file.isEmpty())
        {
            try
            {
                file_tbl input = FileUploadRepo.UploadFile_Repo(file);
                store = Store_Services.NewPost(title, cntent, type, time, "0", visible, attr, price, "", "", user_data, cat, input);
            }
            catch (Exception e)
            {
                System.out.println("Error "+e.getMessage());
                return "redirect:" + request.getHeader("referer");
            }
        }
        else
        {
            file_tbl input = File_Services.GetFileById(1);
            store = Store_Services.NewPost(title,cntent,type,time,"0",visible,attr,price,"","",user_data,cat,input);
        }


        String []all_imgs=all_images.split(",");

        for (int i=0;i<all_imgs.length;i++)
        {
            file_tbl image = File_Services.GetFileById(Integer.parseInt(all_imgs[i]));
            StoreImage_Services.NewStoreImage(time,store,image);
        }


        return "redirect:" + request.getHeader("referer");
    }

    /****************New Post End****************/





    /****************All Posts Start****************/

    @RequestMapping("/AllStores")
    public ModelAndView AllStorPosts(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","همه پست های فروشگاهی");
        response.setViewName("Admin/allstores");
        response.addObject("user_data",user_data);
        response.addObject("all_stores",Store_Services.GetAllPosts());

        return response;
    }


    @RequestMapping("/AllStores/{id}")
    public ModelAndView AllStorPosts(HttpServletRequest request,@PathVariable String id)
    {
        store_tbl store = Store_Services.GetPost(Integer.parseInt(id));
        List<store_images_tbl> images=StoreImage_Services.GetAllStoreImages(store);

        String images_id="";
        for(int i=0;i<images.size();i++)
        {
            System.out.println("id is "+images.get(i).getId()+"  hash is "+images.get(i).getFile_id().getFile_hash());
            images_id+=images.get(i).getId()+",";
        }

        ModelAndView response=new ModelAndView();
        response.addObject("title","همه پست های فروشگاهی");
        response.setViewName("Admin/editstore");
        response.addObject("post",store);
        response.addObject("categories",Category_Services.GetCategories());
        response.addObject("images",images);
        response.addObject("images_id",images_id);

        return response;
    }


    @RequestMapping("/AllStores/Edit/Done")
    public String EditDone(HttpServletRequest request,String id, String title, String category, String visible, String price,String all_images,String attr,String cntent,String discount)
    {
        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        store_tbl store = Store_Services.GetPost(Integer.parseInt(id));
        System.out.println("OK1");
        StoreImage_Services.RemoveRange(store);
        String []all_imgs=all_images.split(",");

        for (int i=0;i<all_imgs.length;i++)
        {
            file_tbl image = File_Services.GetFileById(Integer.parseInt(all_imgs[i]));
            StoreImage_Services.NewStoreImage(time,store,image);
        }
        System.out.println("OK2");

        store_categories_tbl cat=Category_Services.GetCategory(Integer.parseInt(category));
        System.out.println("OK3");

        String adiscount="";

        if(!discount.equals(""))
        {
            if (Integer.parseInt(discount) > 100)
            {
                adiscount = (Integer.parseInt(price) - Integer.parseInt(discount)) + "";
            }
            else
            {
                adiscount = (Integer.parseInt(price) - Integer.parseInt(price) * (Integer.parseInt(discount) / 100)) + "";
            }
        }
        System.out.println("OK4");

        Store_Services.UpdatePost(Integer.parseInt(id),title,cntent,store.getDate(),store.getDelete_flag(),visible,attr,price,discount,adiscount,store.getUser_id(),cat,store.getFile_id());
        System.out.println("OK5");

        return "redirect:" + request.getHeader("referer");
    }


    @RequestMapping("/AllStores/Remove/{id}")
    public String RemoveStorPost(HttpServletRequest request,@PathVariable String id)
    {
        Store_Services.RemovePost(Integer.parseInt(id));
        return "redirect:" + request.getHeader("referer");
    }

    /****************All Posts Start****************/


}
