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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Post_Service implements Posts_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public List<posts_tbl> GetAllPosts()
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM posts_tbl posts WHERE posts.delete_flag='0'").list();
        }
    }

    @Override
    public posts_tbl GetPost(int post_id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (posts_tbl) session.createQuery("FROM posts_tbl post WHERE post.id="+post_id).list().get(0);
//            return session.get(hub_tbl.class, id);
        }
    }

    @Override
    public List<posts_tbl> GetSearchPost(String Search)
    {
        try(Session session=sessionFactory.openSession())
        {
            return session.createQuery("FROM posts_tbl WHERE posts_tbl LIKE '%"+Search+"%'").list();
        }
    }

    @Override
    public posts_tbl NewPost(String title, String content, String image, String date, String visible, users_tbl user_id, categories_tbl categpry_id)
    {
        posts_tbl post=new posts_tbl();

        post.setTitle(title);
        post.setContent(content);
        post.setDate(date);
        post.setDelete_flag("0");
        post.setImage(image);
        post.setCategory_id(categpry_id);
        post.setUser_id(user_id);
        post.setVisible(visible);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(post);
            session.getTransaction().commit();
        }

        return post;
    }

    @Override
    public posts_tbl UpdatePost(int id, String title, String content, String image, String date, String visible, users_tbl user_id, categories_tbl categpry_id)
    {
        posts_tbl post=GetPost(id);

        post.setTitle(title);
        post.setContent(content);
        post.setDate(date);
        post.setImage(image);
        post.setCategory_id(categpry_id);
        post.setUser_id(user_id);
        post.setVisible(visible);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(post);
            session.getTransaction().commit();
        }

        return post;
    }

    @Override
    public void RemovePost(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            posts_tbl post=GetPost(id);
            post.setDelete_flag("1");

            session.beginTransaction();
            session.update(post);
            session.getTransaction().commit();
        }
    }
}
