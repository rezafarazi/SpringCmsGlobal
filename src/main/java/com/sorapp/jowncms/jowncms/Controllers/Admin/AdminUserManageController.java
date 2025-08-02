package com.sorapp.jowncms.jowncms.Controllers.Admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sorapp.jowncms.jowncms.Function.Encript.EncriptService;
import com.sorapp.jowncms.jowncms.Middleware.AdminAuthCheckMiddleware;
import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Models.userstype_tbl;
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
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import java.time.LocalDateTime;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/Admin")
public class AdminUserManageController
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

    private final GoogleAuthenticator gAuth;


    /****************Profile Start****************/
    @RequestMapping("/Panel/Profile")
    public ModelAndView Profile(HttpServletRequest request)
    {
        users_tbl user = User_Services.GetUser(AdminAuthCheckMiddleware.user_data.getId());
        ModelAndView response=new ModelAndView();
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
    public ModelAndView ProfileActive2F(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ModelAndView responsee=new ModelAndView();
        responsee.addObject("title","فعال سازی رمز دوم");
        responsee.addObject("user_data",AdminAuthCheckMiddleware.user_data);

        responsee.addObject("qrcode","/Admin/Panel/Profile/Active2F/QRCODE");

        responsee.setViewName("Admin/active2f");

        return responsee;
    }

    @RequestMapping("/Panel/Profile/Active2F/QRCODE")
    public void GetActive2FQR(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        SetAuthToken(response,request,AdminAuthCheckMiddleware.user_data.getUsername(),AdminAuthCheckMiddleware.user_data.getPassword());
        final GoogleAuthenticatorKey key = gAuth.createCredentials(AdminAuthCheckMiddleware.user_data.getUsername());
        User_Services.Google2F(AdminAuthCheckMiddleware.user_data,"Active",key.getKey());
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("jowncms", AdminAuthCheckMiddleware.user_data.getUsername(), key);
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);
        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();
    }

    @RequestMapping("/Panel/Profile/Diactive2F")
    public String ProfileDiactive2F(HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        User_Services.Google2F(AdminAuthCheckMiddleware.user_data,"Diactive","");
        SetAuthToken(response,request,AdminAuthCheckMiddleware.user_data.getUsername(),AdminAuthCheckMiddleware.user_data.getPassword());
        return "redirect:/Admin/Panel/Profile";
    }

    @PostMapping("/Panel/Profile")
    public String ProfileSubmit(HttpServletRequest request, HttpServletResponse response, String username, String name, String family, String password, String email, String phone, String address, String city, String bio, MultipartFile profile_image) throws Exception
    {

        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        city_tbl cityi = City_Services.GetCity(Integer.parseInt(city));

        if(profile_image!=null)
        {
            file_tbl file = FileUploadRepo.UploadFile_Repo(profile_image);

            User_Services.UpdateUser(
                    AdminAuthCheckMiddleware.user_data.getId(),
                    name,
                    family,
                    username,
                    password,
                    phone,
                    email,
                    bio,
                    time,
                    AdminAuthCheckMiddleware.user_data.getUser_type(),
                    AdminAuthCheckMiddleware.user_data.getStatus(),
                    AdminAuthCheckMiddleware.user_data.getWebsite(),
                    address,
                    file,
                    cityi);
        }
        else
        {
            User_Services.UpdateUser(
                    AdminAuthCheckMiddleware.user_data.getId(),
                    name,
                    family,
                    username,
                    password,
                    phone,
                    email,
                    bio,
                    time,
                    AdminAuthCheckMiddleware.user_data.getUser_type(),
                    AdminAuthCheckMiddleware.user_data.getStatus(),
                    AdminAuthCheckMiddleware.user_data.getWebsite(),
                    address,
                    AdminAuthCheckMiddleware.user_data.getProfile_image_id(),
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
        response.setViewName("Admin/usermanager");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        response.addObject("users",User_Services.GetAllUsers());
        return response;
    }


    @RequestMapping(value = "/Panel/UserManager",method = RequestMethod.POST)
    public ModelAndView UserManager(HttpServletRequest request,String SearchValue)
    {
        ModelAndView response=new ModelAndView();
        response.setViewName("Admin/usermanager");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        response.addObject("users",User_Services.GetSeachUser(SearchValue));
        return response;
    }


    @RequestMapping("/Panel/UserManager/User/{id}")
    public ModelAndView GetUserProfile(HttpServletRequest request,@PathVariable String id)
    {
        users_tbl user=User_Services.GetUser(Integer.parseInt(id));

        ModelAndView response=new ModelAndView();
        response.setViewName("Admin/useredit");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
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
        Notification_Services.NewNotification(title,content_text,link,AdminAuthCheckMiddleware.user_data.getUsername(),false,user);
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






    /****************Access Start****************/

    @RequestMapping(value = "/Panel/Groups")
    public ModelAndView Access(HttpServletRequest request)
    {
        ModelAndView response=new ModelAndView();
        response.setViewName("Admin/accesses");
        response.addObject("user_data",AdminAuthCheckMiddleware.user_data);
        response.addObject("ussertypes",UserType_Services.GetAllUserType());
        return response;
    }


    @RequestMapping(value = "/Panel/Groups/New",method = RequestMethod.POST)
    public String AccessNew(HttpServletRequest request,String title,String en_title)
    {
        UserType_Services.NewType(title,en_title);
        return "redirect:" + request.getHeader("referer");
    }


    @RequestMapping(value = "/Panel/Groups/Delete/{id}")
    public String AccessRemove(HttpServletRequest request,@PathVariable String id)
    {
        UserType_Services.RemoveType(Integer.parseInt(id));
        return "redirect:" + request.getHeader("referer");
    }

    /****************Access End****************/












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
