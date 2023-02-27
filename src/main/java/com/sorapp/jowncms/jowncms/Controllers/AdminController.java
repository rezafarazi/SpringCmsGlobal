package com.sorapp.jowncms.jowncms.Controllers;

import com.sorapp.jowncms.jowncms.Models.city_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Service.City.City_Service;
import com.sorapp.jowncms.jowncms.Service.Users.Users_Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AdminController
{


    @RequestMapping("/")
    public users_tbl Index()
    {
        users_tbl user = new Users_Service().GetUser(1);
        return user;
    }


}
