package com.sorapp.jowncms.jowncms.Service.Posts;

import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.posts_tbl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostsRepo
{


    //New Post Start
    public void NewPost(String Title,String Content,String enbaled_flag,int user_id,int category_id,String image_address)
    {

        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();

        posts_tbl post=new posts_tbl();
        post.setTitle(Title);
        post.setContent(Content);
        post.setEnabled_flag(enbaled_flag);
        post.setUser_id(user_id);
        post.setCategory_id(category_id);
        post.setImage_address(image_address);
        post.setEdit_time(GetDatetime());

        session.save(post);
        t.commit();
        factory.close();
        session.close();

    }
    //New Post End



    //Get All Posts Start
    public ArrayList<posts_tbl> GetAllPosts()
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();
        String hql = "FROM posts_tbl posts WHERE posts.enabled_flag!='2'";
        Query query = session.createQuery(hql);
        List results = query.list();

        t.commit();
        factory.close();
        session.close();
        return (ArrayList<posts_tbl>) results;
    }
    //Get All Posts End



    //Update Post Start
    public void EditPost(int Post_id,String Title,String Content,String enbaled_flag,int user_id,int category_id,String image_address)
    {

        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();

        posts_tbl post=new posts_tbl();
        post.setId(Post_id);
        post.setTitle(Title);
        post.setContent(Content);
        post.setEnabled_flag(enbaled_flag);
        post.setUser_id(user_id);
        post.setCategory_id(category_id);
        post.setImage_address(image_address);
        post.setEdit_time(GetDatetime());

        session.update(post);
        t.commit();
        factory.close();
        session.close();

    }
    //Update Post End



    //Get Delete Post Start
    public void DeletePost(int id)
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();

        posts_tbl old_post=GetPost(id);

        posts_tbl post=new posts_tbl();
        post.setId(id);
        post.setTitle(old_post.getTitle());
        post.setContent(old_post.getContent());
        post.setEnabled_flag("2");
        post.setUser_id(old_post.getUser_id());
        post.setCategory_id(old_post.getCategory_id());
        post.setImage_address(old_post.getImage_address());
        post.setEdit_time(GetDatetime());

        session.update(post);
        t.commit();
        factory.close();
        session.close();

    }
    //Get Delete Post End




    //Get A Post All Data Start
    public posts_tbl GetPost(int post_id)
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();
        String hql = "FROM posts_tbl posts WHERE posts.id="+post_id+"and posts.enabled_flag!='2'";
        Query query = session.createQuery(hql);
        List results = query.list();

        t.commit();
        factory.close();
        session.close();
        return (posts_tbl) results.get(results.size()-1);
    }
    //Get A Post All Data End




    //Get Date Time Start
    private String GetDatetime()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    //Get Date Time End


}
