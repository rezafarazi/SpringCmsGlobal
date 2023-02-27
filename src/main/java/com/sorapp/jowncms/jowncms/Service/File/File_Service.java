package com.sorapp.jowncms.jowncms.Service.File;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class File_Service implements File_Repo
{
    @Override
    public file_tbl GetFileByHash(String hash)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();
        Query HQL=DBRepository.session.createQuery("FROM file_tbl files WHERE files.file_hash LIKE '"+hash+"'");
        file_tbl Result = (file_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public file_tbl GetFileById(int id)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();
        Query HQL=DBRepository.session.createQuery("FROM file_tbl");
        file_tbl Result = (file_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public file_tbl NewFile(String file_name, String file_address, String file_address_server, String file_type, String file_extention, String file_size, String file_hash, String file_date_time)
    {
        DBRepository.session=DBRepository.SF.openSession();
        DBRepository.TA=DBRepository.session.beginTransaction();

        file_tbl file=new file_tbl();
        file.setFile_type(file_type);
        file.setFile_address_server(file_address_server);
        file.setFile_address(file_address);
        file.setFile_date_time(file_date_time);
        file.setFile_name(file_name);
        file.setFile_hash(file_hash);
        file.setFile_extention(file_extention);
        file.setFile_size(file_size);

        DBRepository.session.save(file);
        DBRepository.TA.commit();

        return file;
    }

}
