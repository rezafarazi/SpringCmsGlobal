package com.sorapp.jowncms.jowncms.Service.Post;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.posts_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Posts_Repo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Post_Service implements Posts_Repo
{

    @Override
    public List<posts_tbl> GetAllPosts()
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM posts_tbl");
        List<posts_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public posts_tbl GetPost(int post_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL=DBRepository.session.createQuery("FROM posts_tbl WHERE posts_tbl.id="+post_id);
        posts_tbl Result = (posts_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public List<posts_tbl> GetSearchPost(String Search)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        Query HQL= DBRepository.session.createQuery("FROM posts_tbl WHERE posts_tbl LIKE '%"+Search+"%'");
        List<posts_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public posts_tbl NewPost(String title, String content, String image, String date, String visible, users_tbl user_id, categories_tbl categpry_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        posts_tbl post=new posts_tbl();

        post.setTitle(title);
        post.setContent(content);
        post.setDate(date);
        post.setDelete_flag("0");
        post.setImage(image);
        post.setCategory_id(categpry_id);
        post.setUser_id(user_id);
        post.setVisible(visible);

        DBRepository.session.save(post);
        DBRepository.TA.commit();

        return post;
    }

    @Override
    public posts_tbl UpdatePost(int id, String title, String content, String image, String date, String visible, users_tbl user_id, categories_tbl categpry_id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        posts_tbl post=GetPost(id);

        post.setTitle(title);
        post.setContent(content);
        post.setDate(date);
        post.setImage(image);
        post.setCategory_id(categpry_id);
        post.setUser_id(user_id);
        post.setVisible(visible);

        DBRepository.session.update(post);
        DBRepository.TA.commit();

        return post;
    }

    @Override
    public void RemovePost(int id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();
        posts_tbl post=GetPost(id);
        post.setDelete_flag("1");
        DBRepository.session.update(post);
        DBRepository.TA.commit();

    }
}
