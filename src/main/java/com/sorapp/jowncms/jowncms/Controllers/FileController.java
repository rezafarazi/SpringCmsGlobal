package com.sorapp.jowncms.jowncms.Controllers;

import com.sorapp.jowncms.jowncms.Function.Hash.HashService;
import com.sorapp.jowncms.jowncms.Middleware.AdminAuthCheckMiddleware;
import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Service.File.File_Service;
import com.sorapp.jowncms.jowncms.Service.Users.Users_Service;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
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

@RestController
@RequestMapping("/File")
public class FileController
{

    @Value("${FileManager}")
    String UploadPath;

    @Value("${FileManagerTemp}")
    String TempPath;

    @Autowired
    File_Service file_service;


    //Upload file start
    @RequestMapping(value = "Upload",method = RequestMethod.POST)
    public String UploadFile(HttpServletRequest request, MultipartFile UploadFile) throws Exception
    {
        LocalDateTime datetime = LocalDateTime.now();
        String time=datetime.getYear()+"/"+datetime.getMonthValue()+"/"+datetime.getDayOfMonth()+" "+datetime.getHour()+":"+datetime.getMinute()+":"+datetime.getSecond();

        String file_extention=FilenameUtils.getExtension(UploadFile.getOriginalFilename());

        File TempFile=new File(TempPath+"/"+UploadFile.getName()+"."+file_extention);
        UploadFile.transferTo(TempFile);
        String file_hash= HashService.HashFile(TempFile);

        File MainFile=new File(UploadPath+"/"+file_hash+"."+file_extention);
        Files.copy(TempFile.toPath(),MainFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        file_tbl file = file_service.NewFile(file_hash,UploadPath+"/"+file_hash+"."+file_extention,UploadPath+"/"+file_hash+"."+file_extention,file_extention,file_extention,UploadFile.getSize()+"",file_hash,time);
        TempFile.delete();
        return "{file_id:"+file.getId()+"}";
    }
    //Upload file end


}
