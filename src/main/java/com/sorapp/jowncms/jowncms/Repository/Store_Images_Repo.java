package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.store_images_tbl;
import com.sorapp.jowncms.jowncms.Models.store_tbl;

public interface Store_Images_Repo
{

    //New Store Image
    public store_images_tbl NewStoreImage(String date, store_tbl store_id, file_tbl file_id);

    //Delete Store Image
    public void RemoveStoreImage(int id);

}
