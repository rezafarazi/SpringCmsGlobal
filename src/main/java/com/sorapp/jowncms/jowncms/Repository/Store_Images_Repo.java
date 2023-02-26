package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.hospital_img_tbl;
import com.sorapp.jowncms.jowncms.Models.store_images_tbl;
import com.sorapp.jowncms.jowncms.Models.store_tbl;

import java.util.ArrayList;

public interface Store_Images_Repo
{

    //Get All store images
    public ArrayList<store_images_tbl> GetAllStoreImages(int store_id);

    //New Store Image
    public store_images_tbl NewStoreImage(String date, store_tbl store_id, file_tbl file_id);

    //Delete Store Image
    public void RemoveStoreImage(int id);

}
