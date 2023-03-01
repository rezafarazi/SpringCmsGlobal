package com.sorapp.jowncms.jowncms.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.hash.Hashing;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sorapp.jowncms.jowncms.Function.Encript.EncriptService;
import com.sorapp.jowncms.jowncms.Models.*;
import com.sorapp.jowncms.jowncms.Repository.FileUpload_Repo;
import com.sorapp.jowncms.jowncms.Service.Category.Category_Service;
import com.sorapp.jowncms.jowncms.Service.City.City_Service;
import com.sorapp.jowncms.jowncms.Service.Notification.Notification_Service;
import com.sorapp.jowncms.jowncms.Service.Post.Post_Service;
import com.sorapp.jowncms.jowncms.Service.Setting.Setting_Service;
import com.sorapp.jowncms.jowncms.Service.UserType.UserType_Service;
import com.sorapp.jowncms.jowncms.Service.Users.Users_Service;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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

    @Value("${FileManagerRoot}")
    String FileManager;

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






    /****************NewPost Start****************/
    @RequestMapping("/Panel/NewPost")
    public ModelAndView NewPost(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","پست جدید");
        response.setViewName("Admin/newpost");
        response.addObject("user_data",user_data);
        response.addObject("categories",Category_Services.GetCategories());
        return response;
    }

    @RequestMapping("/Panel/NewPost/Done")
    public String NewPostSubmit(HttpServletRequest request,String title,String category,String post_image_address,String Visibleity,String Content)
    {
        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        categories_tbl cat = Category_Services.GetCategory(Integer.parseInt(category));

        Posts_Services.NewPost(title,Content,post_image_address,time,Visibleity,user_data,cat);
        return "redirect:/Admin/Panel/NewPost";
    }
    /****************NewPost End****************/




    /****************All Posts Start****************/
    @RequestMapping("/Panel/AllPosts")
    public ModelAndView AllPosts(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","همه پست ها");
        response.setViewName("Admin/allposts");
        response.addObject("user_data",user_data);
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
        response.addObject("title","ویرایش پست");
        response.setViewName("Admin/editpost");
        response.addObject("user_data",user_data);
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

        Posts_Services.UpdatePost(Integer.parseInt(id),title,cntent,img_adrs,time,visible,user_data,cat);
        return "redirect:/Admin/Panel/Post/Edit/"+id;
    }
    /****************All Posts End****************/






    /****************Chart Start****************/
    @RequestMapping("/Panel/Charts")
    public ModelAndView Chart(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","آمار");
        response.setViewName("Admin/chart");
        response.addObject("user_data",user_data);
        return response;
    }
    /****************Chart End****************/







    /****************Profile Start****************/
    @RequestMapping("/Panel/Profile")
    public ModelAndView Profile(HttpServletRequest request)
    {
        users_tbl user = User_Services.GetUser(user_data.getId());
        ModelAndView response=new ModelAndView();
        response.addObject("title","پروفایل");
        response.addObject("user_data",user);

        //Google Auth Start
        if(user.getGoogle_auth().length()==0)
        {
            response.addObject("google_auth", true);
        }
        else
        {
            response.addObject("google_auth", false);
        }
        //Google Auth End

        //Get All Cites
        response.addObject("Cites",City_Services.GetCites());

        response.setViewName("Admin/profile");
        return response;
    }

    @SneakyThrows
    @RequestMapping("/Panel/Profile/Active2F")
    public ModelAndView ProfileActive2F(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
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
        SetAuthToken(response,request,user_data.getUsername(),user_data.getPassword());
        final GoogleAuthenticatorKey key = gAuth.createCredentials(user_data.getUsername());
        User_Services.Google2F(user_data,"Active",key.getKey());
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
        User_Services.Google2F(user_data,"Diactive","");
        SetAuthToken(response,request,user_data.getUsername(),user_data.getPassword());
        return "redirect:/Admin/Panel/Profile";
    }

    @PostMapping("/Panel/Profile")
    public String ProfileSubmit(HttpServletRequest request,HttpServletResponse response,String username,String name,String family,String password,String email,String phone,String address,String city,String bio,MultipartFile profile_image) throws Exception
    {

        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        city_tbl cityi = City_Services.GetCity(Integer.parseInt(city));

        if(profile_image!=null)
        {
            file_tbl file = FileUploadRepo.UploadFile_Repo(profile_image);

            User_Services.UpdateUser(
                    user_data.getId(),
                    name,
                    family,
                    username,
                    password,
                    phone,
                    email,
                    bio,
                    time,
                    user_data.getUser_type(),
                    user_data.getStatus(),
                    user_data.getWebsite(),
                    address,
                    file,
                    cityi);
        }
        else
        {
            User_Services.UpdateUser(
                    user_data.getId(),
                    name,
                    family,
                    username,
                    password,
                    phone,
                    email,
                    bio,
                    time,
                    user_data.getUser_type(),
                    user_data.getStatus(),
                    user_data.getWebsite(),
                    address,
                    user_data.getProfile_image_id(),
                    cityi);

        }

        SetAuthToken(response,request,username,password);

        return "redirect:/Admin/Panel/Profile";
    }
    /****************Profile End****************/






    /****************Usermanager Start****************/
    @RequestMapping("/Panel/UserManager")
    public ModelAndView UserManager(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت کاربران");
        response.setViewName("Admin/usermanager");
        response.addObject("user_data",user_data);
        response.addObject("users",User_Services.GetAllUsers());
        return response;
    }


    @RequestMapping(value = "/Panel/UserManager",method = RequestMethod.POST)
    public ModelAndView UserManager(HttpServletRequest request,String SearchValue)
    {
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت کاربران");
        response.setViewName("Admin/usermanager");
        response.addObject("user_data",user_data);
        response.addObject("users",User_Services.GetSeachUser(SearchValue));
        return response;
    }


    @RequestMapping("/Panel/UserManager/User/{id}")
    public ModelAndView GetUserProfile(HttpServletRequest request,@PathVariable String id)
    {
        users_tbl user=User_Services.GetUser(Integer.parseInt(id));

        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت کاربران");
        response.setViewName("Admin/useredit");
        response.addObject("user_data",user_data);
        response.addObject("user_types",UserType_Services.GetAllUserType());
        response.addObject("edit_user_data",user);

        //Google Auth Start
        if(user.getGoogle_auth().length()==0)
        {
            response.addObject("google_auth", true);
        }
        else
        {
            response.addObject("google_auth", false);
        }
        //Google Auth End

        //Get All Cites
        response.addObject("Cites",City_Services.GetCites());

        return response;
    }



    @RequestMapping("/Panel/UserManager/User/DisableGoogleAuth/{id}")
    public String DisableGoogleAuth(HttpServletRequest request,HttpServletResponse response,@PathVariable String id) throws Exception
    {
        User_Services.Google2F(User_Services.GetUser(Integer.parseInt(id)),"Diactive","");
        return "redirect:/Admin/Panel/UserManager/User/"+id;
    }


    @RequestMapping("/Panel/UserManager/User/SendNotification/{id}")
    public ModelAndView SendNotification(HttpServletRequest request,@PathVariable String id) throws Exception
    {
        ModelAndView response=new ModelAndView();
        response.addObject("user_id",id);
        response.setViewName("Admin/sendnotification");
        return response;
    }


    @RequestMapping(value = "/Panel/UserManager/User/SendNotifications/Done",method = RequestMethod.POST)
    public String SendNotification(HttpServletRequest request , String user_id,String title,String content_text,String link) throws Exception
    {
        users_tbl user = User_Services.GetUser(Integer.parseInt(user_id));
        Notification_Services.NewNotification(title,content_text,link,user_data.getUsername(),false,user);
        return "redirect:" + request.getHeader("referer");
    }


    @RequestMapping(value = "/Panel/UserManager/User/Edit/Done",method = RequestMethod.POST)
    public String EditUserProfile(HttpServletRequest request,HttpServletResponse response,String user_id,String username,String name,String family,String password,String email,String phone,String address,String city,String bio,MultipartFile profile_image,String user_type) throws Exception
    {

        users_tbl user_dt=User_Services.GetUser(Integer.parseInt(user_id));

        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        city_tbl cityi = City_Services.GetCity(Integer.parseInt(city));

        userstype_tbl usrtype= UserType_Services.GetUserType(Integer.parseInt(user_type));

        if(profile_image!=null)
        {
            file_tbl file = FileUploadRepo.UploadFile_Repo(profile_image);

            User_Services.UpdateUserByAdmin(
                    user_dt.getId(),
                    name,
                    family,
                    username,
                    password,
                    phone,
                    email,
                    bio,
                    time,
                    user_dt.getUser_type(),
                    user_dt.getStatus(),
                    user_dt.getWebsite(),
                    address,
                    file,
                    cityi,
                    usrtype);
        }
        else
        {
            User_Services.UpdateUserByAdmin(
                    user_dt.getId(),
                    name,
                    family,
                    username,
                    password,
                    phone,
                    email,
                    bio,
                    time,
                    user_dt.getUser_type(),
                    user_dt.getStatus(),
                    user_dt.getWebsite(),
                    address,
                    user_dt.getProfile_image_id(),
                    cityi,
                    usrtype);

        }


        return "redirect:/Admin/Panel/UserManager/User/"+user_dt.getId();
    }
    /****************Usermanager End****************/






    /****************FileMnager Start****************/
    @RequestMapping("/Panel/FileManager")
    public ModelAndView FileManager(HttpServletRequest request)
    {
        String Path=request.getParameter("path");
        ModelAndView response=new ModelAndView();
        response.addObject("title","مدیریت فایل");
        response.setViewName("Admin/filemanager");
        response.addObject("user_data",user_data);
        response.addObject("address",Path);

        if(Path==null) {
            File folder = new File(FileManager);
            File[] folders = folder.listFiles();
            response.addObject("fileslen",folder.listFiles().length);
            response.addObject("files",folder.listFiles());
            response.addObject("curentlocation",FileManager);
        }
        else{
            File folder = new File(FileManager+"/"+Path);
            File[] folders = folder.listFiles();
            response.addObject("fileslen",folder.listFiles().length);
            response.addObject("files",folder.listFiles());
            response.addObject("curentlocation",FileManager);
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
                File NewFolder = new File(FileManager + "/" + Folder_Name);
                NewFolder.mkdir();
                NewFolder.mkdirs();
            }
            else
            {
                File NewFolder = new File(FileManager + "/" + Addrs + "/" + Folder_Name);
                NewFolder.mkdir();
                NewFolder.mkdirs();
            }
        }
        catch (Exception e)
        {

        }
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/Panel/FileManager/Upload",method = RequestMethod.POST)
    public String UploadFile(HttpServletRequest request,MultipartFile Upload_File,String Address)
    {
        try
        {
            String FileName = Upload_File.getOriginalFilename();
            Upload_File.transferTo(new File(FileManager+"/"+Address+"/"+FileName));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/Panel/FileManager/Delete",method = RequestMethod.GET)
    public String RemoveFile(HttpServletRequest request)
    {
        System.out.println("Get remove file");
        try
        {
            String path=request.getParameter("path");
            String name=request.getParameter("name");
            if(path.equals("null"))
            {
                File file = new File(FileManager + "/" + name);
                file.delete();
                System.out.println("Removed"+file.getPath());
            }
            else
            {
                File file = new File(FileManager + "/" + path + "/" + name);
                file.delete();
                System.out.println("Removed"+file.getPath());
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return "redirect:" + request.getHeader("referer");
    }

    @RequestMapping(value = "/Panel/FileManager/DeleteDir",method = RequestMethod.GET)
    public String RemoveDirectory(HttpServletRequest request)
    {
        try
        {
            String path=request.getParameter("path").replace("null/","");
            FileUtils.deleteDirectory(new File(FileManager+"\\"+path));
        }
        catch (Exception e)
        {

        }
        return "redirect:" + request.getHeader("referer");
    }
    /****************FileMnager End****************/






    /****************Setting Start****************/

    @RequestMapping("/Panel/Settings")
    public ModelAndView Settins()
    {
        settings_tbl settings_tbl=Setting_Services.GetSetting();
        JSONObject jsonObject=new JSONObject(settings_tbl.getParametrs());

        ModelAndView response=new ModelAndView();

        response.addObject("logo",jsonObject.get("logo"));
        response.addObject("law",jsonObject.get("law"));
        response.addObject("slider",jsonObject.get("slider"));
        response.addObject("banner",jsonObject.get("banner"));
        response.addObject("title","تنظیمات");
        response.addObject("user_data",user_data);
        response.setViewName("Admin/settings");
        return response;
    }

    @RequestMapping(value = "/Panel/Settings/Done",method = RequestMethod.POST)
    public String Settings(String slider,String banner,String law,String icon)
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("logo",icon);
        jsonObject.put("law",law);
        jsonObject.put("slider",banner);
        jsonObject.put("banner",slider);
        settings_tbl settings_tbl=Setting_Services.GetSetting();
        Setting_Services.NewSetting(jsonObject.toString(),settings_tbl.getDiscountall());
        return "redirect:/Admin/Panel/Settings";
    }

    /****************Setting End****************/





    /****************Logout Start****************/
    @RequestMapping("/Panel/Logout")
    public String Logout(HttpServletResponse response)
    {
        Cookie auth_c = new Cookie("auth",null);
        auth_c.setPath("/Admin");
        response.addCookie(auth_c);
        return "redirect:/";
    }
    /****************Logout End****************/







    /****************Set Auth Token Start****************/
    private void SetAuthToken(HttpServletResponse response,HttpServletRequest request,String username,String password) throws Exception
    {
        //Set Reload Auth Token
        users_tbl user_data = User_Services.Login(username, password);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user_data);
        String Berar_Token = Encription.ENC(json);
        Cookie auth_c = new Cookie("auth",Berar_Token);
        auth_c.setPath("/Admin");
        response.addCookie(auth_c);
    }
    /****************Set Auth Token End****************/






}
