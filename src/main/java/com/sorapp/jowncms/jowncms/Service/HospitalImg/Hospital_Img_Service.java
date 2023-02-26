package com.sorapp.jowncms.jowncms.Service.HospitalImg;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.hospital_img_tbl;
import com.sorapp.jowncms.jowncms.Models.hospital_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Hospital_Img_Repo;
import org.hibernate.query.Query;

import java.util.List;

public class Hospital_Img_Service implements Hospital_Img_Repo
{
    @Override
    public List<hospital_img_tbl> GetAllHospitalImages(int Hospital_Id) {
        Query HQL= DBRepository.session.createQuery("FROM hospital_img_tbl WHERE hospital_img_tbl.hospital_id.id="+Hospital_Id);
        List<hospital_img_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public hospital_img_tbl NewImage(hospital_tbl hospital_id, file_tbl file_id)
    {
        hospital_img_tbl HospitalImage=new hospital_img_tbl();
        HospitalImage.setHospital_id(hospital_id);
        HospitalImage.setFile_id(file_id);

        DBRepository.session.save(HospitalImage);
        DBRepository.TA.commit();

        return HospitalImage;
    }

    @Override
    public void RemoveHospital(int id)
    {
        Query HQL= DBRepository.session.createQuery("FROM hospital_img_tbl WHERE hospital_img_tbl.id="+id);
        List<hospital_img_tbl> Result = HQL.getResultList();
        DBRepository.session.remove(Result);
        DBRepository.TA.commit();
    }
}
