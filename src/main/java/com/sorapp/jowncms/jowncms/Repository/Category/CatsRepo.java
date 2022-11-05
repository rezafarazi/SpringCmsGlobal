package com.sorapp.jowncms.jowncms.Repository.Category;

import com.sorapp.jowncms.jowncms.Controllers.AdminController;
import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CatsRepo
{

    //Logs
    private static final Logger LOGGER = LoggerFactory.getLogger(CatsRepo.class);


    //Get Fetch All Categores Start
    public ArrayList<categories_tbl> GetAllCategories()
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();
        String hql = "FROM categories_tbl cat WHERE cat.enabled_flag!='2'";
        Query query = session.createQuery(hql);
        List results = query.list();

        t.commit();
        factory.close();
        session.close();
        return (ArrayList<categories_tbl>) results;
    }
    //Get Fetch All Categores End



    //New Category Start
    public void InsertNewCategory(String Category_Name,String Root_Cat,String Visibleity,int user_id)
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();

        categories_tbl category=new categories_tbl();
        category.setTitle(Category_Name);
        category.setRoot_id(Integer.parseInt(Root_Cat));
        category.setEnabled_flag(Visibleity);
        category.setIcon_address("");
        category.setType((Root_Cat.equals("0")?"ROOT":"Sub"));
        category.setCreate_date(GetDatetime());
        category.setUser_id(user_id);
        category.setCat_link("");

        t.commit();
        session.save(category);
        factory.close();
        session.close();
    }
    //New Category End


    //Update Category Start
    public void UpdateCategory(int Category_Id,String Category_Name,String Root_Cat,String Visibleity)
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();

        categories_tbl old_data=GetCategory(Category_Id);

        categories_tbl category=new categories_tbl();
        category.setId(Category_Id);
        category.setEnabled_flag(Visibleity);
        category.setTitle(Category_Name);
        category.setIcon_address(old_data.getIcon_address());
        category.setType(old_data.getType());
        category.setCreate_date(old_data.getCreate_date());
        category.setRoot_id(Integer.parseInt(Root_Cat));
        category.setUser_id(old_data.getUser_id());
        category.setCat_link(old_data.getCat_link());

        session.update(category);
        t.commit();
        factory.close();
        session.close();
    }
    //Update Category End


    //DELETE Category Start
    public void DeleteCategory(int Category_Id)
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();

        categories_tbl old_data=GetCategory(Category_Id);

        categories_tbl category=new categories_tbl();
        category.setId(Category_Id);
        category.setEnabled_flag("2");
        category.setTitle(old_data.getTitle());
        category.setIcon_address(old_data.getIcon_address());
        category.setType(old_data.getType());
        category.setCreate_date(old_data.getCreate_date());
        category.setRoot_id(old_data.getRoot_id());
        category.setUser_id(old_data.getUser_id());
        category.setCat_link(old_data.getCat_link());

        session.update(category);
        t.commit();
        factory.close();
        session.close();
    }
    //DELETE Category Start



    //Get Date Time Start
    private String GetDatetime()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    //Get Date Time End



    //Get a Category Data Start
    public categories_tbl GetCategory(int id)
    {
        StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("HibernateConfigs/hibernate.cfg.xml").build();
        Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();
        SessionFactory factory=meta.getSessionFactoryBuilder().build();
        Session session=factory.openSession();
        Transaction t=session.beginTransaction();
        String hql = "FROM categories_tbl cat WHERE cat.id = "+id;
        Query query = session.createQuery(hql);
        List results = query.list();

        t.commit();
        factory.close();
        session.close();
        return (categories_tbl) results.get(0);
    }
    //Get a Category Data End



}
