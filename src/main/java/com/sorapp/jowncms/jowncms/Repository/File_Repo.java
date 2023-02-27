package com.sorapp.jowncms.jowncms.Repository;

import com.sorapp.jowncms.jowncms.Models.file_tbl;
import com.sorapp.jowncms.jowncms.Models.users_tbl;

public interface File_Repo
{

    //Get File with hash
    public file_tbl GetFileByHash(String hash);

    //Get File by id
    public file_tbl GetFileById(int id);

    //New File
    public file_tbl NewFile(String file_name, String file_address, String file_address_server, String file_type, String file_extention, String file_size, String file_hash, String file_date_time);

}
