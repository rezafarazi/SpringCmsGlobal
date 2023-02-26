package com.sorapp.jowncms.jowncms.Service.Category;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import com.sorapp.jowncms.jowncms.Models.categories_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;
import com.sorapp.jowncms.jowncms.Repository.Category_Repo;
import org.hibernate.query.Query;

import java.util.List;

public class Category_Service implements Category_Repo
{

    @Override
    public categories_tbl GetCategory(int id)
    {
        Query HQL= DBRepository.session.createQuery("FROM categories_tbl WHERE categories_tbl.id="+id);
        categories_tbl Result = (categories_tbl) HQL.list().get(0);
        return Result;
    }

    @Override
    public List<categories_tbl> GetCategories()
    {
        Query HQL=DBRepository.session.createQuery("FROM categories_tbl WHERE categories_tbl.delete_flag='0'");
        List<categories_tbl> Result = HQL.getResultList();
        return Result;
    }

    @Override
    public categories_tbl NewCategory(String title, int root_cat_id, String icon_address, String datetime, String castom_link, String visible, String delete_flag)
    {
        categories_tbl NCategory=new categories_tbl(
            title,
            root_cat_id,
            icon_address,
            datetime,
            castom_link,
            visible,
            delete_flag
        );

        DBRepository.session.save(NCategory);
        DBRepository.TA.commit();

        return NCategory;
    }

    @Override
    public categories_tbl UpdateCategory(int id, String title, int root_cat_id, String icon_address, String castom_link, String visible, String delete_flag)
    {
        categories_tbl NCategory=GetCategory(id);

        NCategory.setTitle(title);
        NCategory.setRoot_cat_id(root_cat_id);
        NCategory.setIcon_address(icon_address);
        NCategory.setCastom_link(castom_link);
        NCategory.setVisible(visible);
        NCategory.setDelete_flag(delete_flag);

        DBRepository.session.update(NCategory);
        DBRepository.TA.commit();
        return NCategory;
    }

}
