package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.posts_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;

import java.util.ArrayList;

public interface Posts_Repo
{

    //Get All Posts
    public ArrayList<posts_tbl> GetAllPosts();

    //Get Post
    public posts_tbl GetPost(int post_id);

    //Get Search Post result
    public ArrayList<posts_tbl> GetSearchPost(String Search);

    //New Post
    public posts_tbl NewPost(String title, String content, String image, String date, String delete_flag, String visible, users_tbl user_id, categories_tbl categpry_id);

    //Update post
    public posts_tbl UpdatePost(int id,String title, String content, String image, String date, String delete_flag, String visible, users_tbl user_id, categories_tbl categpry_id);

    //Remove post
    public void RemovePost(int id);

}
