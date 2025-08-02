package com.sorapp.jowncms.jowncms.Controllers.File;

import com.sorapp.jowncms.jowncms.Function.Hash.HashService;
import com.sorapp.jowncms.jowncms.Middleware.AdminAuthCheckMiddleware;
import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.FileUpload_Repo;
import com.sorapp.jowncms.jowncms.Service.File.File_Service;
import com.sorapp.jowncms.jowncms.Service.Users.Users_Service;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/File")
public class FileController
{



    @Autowired
    FileUpload_Repo FileUploadRepo;


    //Upload file start
    @RequestMapping(value = "Upload",method = RequestMethod.POST)
    public Map<String,String> UploadFile(HttpServletRequest request, MultipartFile UploadFile) throws Exception
    {
        file_tbl file = FileUploadRepo.UploadFile_Repo(UploadFile);
        Map<String,String> result=new HashMap<>();
        result.put("file_id",file.getId()+"");
        return result;
    }
    //Upload file end


}
