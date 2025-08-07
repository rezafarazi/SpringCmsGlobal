package com.sorapp.jowncms.jowncms.Service.File;

import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.File_Repo;
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
public class File_Service implements File_Repo
{

    //Global variables
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public file_tbl GetFileByHash(String hash)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (file_tbl) session.createQuery("FROM file_tbl files WHERE files.file_hash LIKE :hash").setParameter("hash",hash).list().get(0);
//            return session.get(hub_tbl.class, id);
        }
    }

    @Override
    public file_tbl GetFileById(int id)
    {
        try(Session session=sessionFactory.openSession())
        {
            return (file_tbl) session.createQuery("FROM file_tbl file WHERE file.id="+id).list().get(0);
//            return session.get(hub_tbl.class, id);
        }
    }

    @Override
    public file_tbl NewFile(String file_name, String file_address, String file_address_server, String file_type, String file_extention, String file_size, String file_hash, String file_date_time)
    {
        file_tbl file=new file_tbl();
        file.setFile_type(file_type);
        file.setFile_address_server(file_address_server);
        file.setFile_address(file_address);
        file.setFile_date_time(file_date_time);
        file.setFile_name(file_name);
        file.setFile_hash(file_hash);
        file.setFile_extention(file_extention);
        file.setFile_size(file_size);

        try(Session session=sessionFactory.openSession())
        {
            session.beginTransaction();
            session.save(file);
            session.getTransaction().commit();
        }

        return file;
    }

}
