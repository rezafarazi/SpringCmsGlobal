package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.*;

@Entity
@Table(name = "hospital_img_tbls")
public class hospital_img_tbl
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    hospital_tbl hospital_id;
    @ManyToOne
    @JoinColumn(name = "file_id")
    file_tbl file_id;

    public hospital_img_tbl() {
    }

    public hospital_img_tbl(hospital_tbl hospital_id, file_tbl file_id) {
        this.hospital_id = hospital_id;
        this.file_id = file_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public hospital_tbl getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(hospital_tbl hospital_id) {
        this.hospital_id = hospital_id;
    }

    public file_tbl getFile_id() {
        return file_id;
    }

    public void setFile_id(file_tbl file_id) {
        this.file_id = file_id;
    }
}
