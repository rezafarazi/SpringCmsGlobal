package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.hospital_img_tbl;
import com.sorapp.jowncms.jowncms.Models.hospital_tbl;

import java.util.ArrayList;
import java.util.List;

public interface Hospital_Img_Repo
{

    //Get All hospital image
    public List<hospital_img_tbl> GetAllHospitalImages(int Hospital_Id);

    //New Image
    public hospital_img_tbl NewImage(hospital_tbl hospital_id, file_tbl file_id);

    //Remove Image
    public void RemoveHospital(int id);

}
