package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.users_tbl;

public interface User_Verify_Repo
{

    //New user verify code
    public void NewUserVerifyCode(String hash_code, users_tbl user_id);


    //Remove user verify code
    public void RemoveUserVerifyCode(users_tbl user_id);


}
