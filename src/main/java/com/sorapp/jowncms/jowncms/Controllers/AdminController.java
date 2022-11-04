package com.sorapp.jowncms.jowncms.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.hash.Hashing;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Category.CatsRepo;
import com.sorapp.jowncms.jowncms.Repository.Configs.ConfigsRepo;
import com.sorapp.jowncms.jowncms.Repository.Posts.PostsRepo;
import com.sorapp.jowncms.jowncms.Repository.Users.UsersRepo;
import com.sorapp.jowncms.jowncms.Service.Encript.EncriptService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.util.Validation;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
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

    @Autowired
    PostsRepo posts_repository;

    @Autowired
    ConfigsRepo configs_repository;

    users_tbl user_data;

    private static final Logger LOGGER=LoggerFactory.getLogger(AdminController.class);
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
    public String Login(String username, String password, HttpServletResponse response,HttpServletRequest request)
    {
        try
        {
            String pass = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
            user_data = users_repository.LOGIN_USER(username, pass);
            if (user_data != null)
            {
                if(user_data.getGoogle2f().equals("")) {
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
            LOGGER.info(e.getMessage());
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
        user_data = users_repository.GetUserByToken(Token);

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
        response.addObject("categories",categories_repository.GetAllCategories());
        return response;
    }

    @RequestMapping("/Panel/NewPost/Done")
    public String NewPostSubmit(HttpServletRequest request,String title,String category,String post_image_address,String Visibleity,String Content)
    {
        Get_User_Data(request);
        posts_repository.NewPost(title,Content,Visibleity,user_data.getId(),Integer.parseInt(category),post_image_address);
        return "redirect:/Admin/Panel/NewPost";
    }
    /****************Categories End****************/




    /****************Categories Start****************/
    @RequestMapping("/Panel/AllPosts")
    public ModelAndView AllPosts(HttpServletRequest request)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","همه پست ها");
        response.setViewName("Admin/allposts");
        response.addObject("user_data",user_data);
        response.addObject("posts",posts_repository.GetAllPosts());
        return response;
    }

    @RequestMapping("/Panel/Post/Delete/{id}")
    public String DeletePost(HttpServletRequest request,@PathVariable String id)
    {
        Get_User_Data(request);
        posts_repository.DeletePost(Integer.parseInt(id));
        return "redirect:/Admin/Panel/AllPosts";
    }

    @RequestMapping("/Panel/Post/Edit/{id}")
    public ModelAndView AllPosts(HttpServletRequest request,@PathVariable String id)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","ویرایش پست");
        response.setViewName("Admin/editpost");
        response.addObject("user_data",user_data);
        response.addObject("categories",categories_repository.GetAllCategories());
        response.addObject("post",posts_repository.GetPost(Integer.parseInt(id)));
        return response;
    }

    @RequestMapping("/Panel/Post/Edit/Done")
    public String EditDonePost(HttpServletRequest request,String post_id,String title,String category,String post_image_address,String Visibleity,String Content)
    {
        Get_User_Data(request);
        posts_repository.EditPost(Integer.parseInt(post_id),title,Content,Visibleity,user_data.getId(),Integer.parseInt(category),post_image_address);
        return "redirect:/Admin/Panel/Post/Edit/"+post_id;
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
        response.addObject("users",users_repository.GetAllUsers());
        return response;
    }


    @RequestMapping(value = "/Panel/UserManager",method = RequestMethod.POST)
    public ModelAndView UserManager(HttpServletRequest request,String SearchValue)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت کاربران");
        response.setViewName("Admin/usermanager");
        response.addObject("user_data",user_data);
        response.addObject("users",users_repository.GetSearchUser(SearchValue));
        return response;
    }


    @RequestMapping("/Panel/UserManager/User/{id}")
    public ModelAndView GetUserProfile(HttpServletRequest request,@PathVariable String id)
    {
        Get_User_Data(request);
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت کاربران");
        response.setViewName("Admin/useredit");
        response.addObject("user_data",user_data);
        response.addObject("edit_user_data",users_repository.GetUserData(Integer.parseInt(id)));
        return response;
    }


    @RequestMapping(value = "/Panel/UserManager/User/Edit/Done",method = RequestMethod.POST)
    public String EditUserProfile(HttpServletRequest request,HttpServletResponse response,String user_id,String username,String name,String family,String password,String email,String phone,String usertype,String active_user)
    {
        Get_User_Data(request);

        users_tbl user_dt=users_repository.GetUserData(Integer.parseInt(user_id));
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

        users_repository.UpdateUser(Integer.parseInt(user_id),
                username,
                name,
                family,
                email,
                phone,
                password,
                user_dt.getLogin_token(),
                user_dt.getGoogle2f(),
                usertype,
                active_user,
                user_dt.getCreate_datetime(),
                user_dt.getAddress(),
                user_dt.getCity());

        return "redirect:/Admin/Panel/UserManager/User/"+user_dt.getId();
    }
    /****************Usermanager End****************/




    /****************Profile Start****************/
    String Upload_Path="C:\\Users\\Rezafta\\Desktop\\SpringCms\\Uploads";

    @RequestMapping("/Panel/FileManager")
    public ModelAndView FileManager(HttpServletRequest request)
    {
        Get_User_Data(request);
        String Path=request.getParameter("path");
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت فایل");
        response.setViewName("Admin/filemanager");
        response.addObject("user_data",user_data);
        response.addObject("address",Path);

        if(Path==null) {
            File folder = new File(Upload_Path);
            File[] folders = folder.listFiles();
            response.addObject("fileslen",folder.listFiles().length);
            response.addObject("files",folder.listFiles());
            response.addObject("curentlocation",Upload_Path);
        }
        else{
            File folder = new File(Upload_Path+"/"+Path);
            File[] folders = folder.listFiles();
            response.addObject("fileslen",folder.listFiles().length);
            response.addObject("files",folder.listFiles());
            response.addObject("curentlocation",Upload_Path);
        }

        return response;
    }

    @RequestMapping(value = "/Panel/FileManager/CreateFolder",method = RequestMethod.POST)
    public String CreateFolder(HttpServletRequest request,String Folder_Name,String Addrs)
    {
        try
        {

            if(Addrs==null)
            {
                File NewFolder = new File(Upload_Path + "/" + Folder_Name);
                LOGGER.info(Upload_Path + "/" + Folder_Name);
                NewFolder.mkdir();
                NewFolder.mkdirs();
            }
            else
            {
                File NewFolder = new File(Upload_Path + "/" + Addrs + "/" + Folder_Name);
                LOGGER.info(Upload_Path + "/" + Addrs + "/" + Folder_Name);
                NewFolder.mkdir();
                NewFolder.mkdirs();
            }
        }
        catch (Exception e)
        {
            LOGGER.info(e.getMessage());
        }
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/Panel/FileManager/Upload",method = RequestMethod.POST)
    public String UploadFile(HttpServletRequest request,MultipartFile Upload_File,String Address)
    {
        try
        {
            String FileName = Upload_File.getOriginalFilename();
            Upload_File.transferTo(new File(Upload_Path+"/"+Address+"/"+FileName));
        }
        catch (Exception e)
        {

        }
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/Panel/FileManager/Delete",method = RequestMethod.GET)
    public String RemoveFile(HttpServletRequest request)
    {
        try
        {
            String path=request.getParameter("path");
            String name=request.getParameter("name");
            if(path.equals("null"))
            {
                File file = new File(Upload_Path + "\\" + name);
                file.delete();
            }
            else
            {
                File file = new File(Upload_Path + "\\" + path + "\\" + name);
                file.delete();
            }
        }
        catch (Exception e)
        {

        }
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/Panel/FileManager/DeleteDir",method = RequestMethod.GET)
    public String RemoveDirectory(HttpServletRequest request)
    {
        try
        {
            String path=request.getParameter("path").replace("null/","");
            FileUtils.deleteDirectory(new File(Upload_Path+"\\"+path));
        }
        catch (Exception e)
        {

        }
        return "redirect:" + request.getHeader("referer");
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
        LOGGER.info(user_data.getEmail());
        response.setViewName("Admin/profile");
        return response;
    }

    @SneakyThrows
    @RequestMapping("/Panel/Profile/Active2F")
    public ModelAndView ProfileActive2F(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
        Get_User_Data(request);
        ModelAndView responsee=new ModelAndView();
        responsee.addObject("title","فعال سازی رمز دوم");
        responsee.addObject("user_data",user_data);

        responsee.addObject("qrcode","/Admin/Panel/Profile/Active2F/QRCODE");

        responsee.setViewName("Admin/active2f");

        return responsee;
    }

    @RequestMapping("/Panel/Profile/Active2F/QRCODE")
    public void GetActive2FQR(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        Get_User_Data(request);
        SetAuthToken(response,request,user_data.getUsername(),user_data.getPassword());
        final GoogleAuthenticatorKey key = gAuth.createCredentials(user_data.getUsername());
        users_repository.Google2F(user_data.getId(),"Active",key.getKey());
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("jowncms", user_data.getUsername(), key);
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);
        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();
    }

    @RequestMapping("/Panel/Profile/Diactive2F")
    public String ProfileDiactive2F(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        Get_User_Data(request);
        users_repository.Google2F(user_data.getId(),"Diactive","");
        SetAuthToken(response,request,user_data.getUsername(),user_data.getPassword());
        return "redirect:/Admin/Panel/Profile";
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
        response.addObject("configs",configs_repository.GetConfigs().get(0).getConfigs_json());
        return response;
    }

    @RequestMapping(value = "/Panel/Settings/Done",method = RequestMethod.POST)
    public String SettingsSubmit(HttpServletRequest request,String Config)
    {
        Get_User_Data(request);
        configs_repository.SetConfigs(Config);
        return "redirect:/Admin/Panel/Settings";
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
