package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.*;

public interface Store_Repo
{

    //New Post
    public store_tbl NewPost(String title, String content, String type, String date, String delete_flag, String visible, String json, String price, String discount, String adiscount, users_tbl user_id, store_categories_tbl category_id,file_tbl file_id);

    //Update post
    public store_tbl UpdatePost(int id,String title, String content, String type, String date, String delete_flag, String visible, String json, String price, String discount, String adiscount, users_tbl user_id, store_categories_tbl category_id,file_tbl file_id);

    //Remove post
    public void RemovePost(int id);

}
