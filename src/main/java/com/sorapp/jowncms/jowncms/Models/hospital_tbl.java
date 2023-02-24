package com.sorapp.jowncms.jowncms.Models;

import javax.persistence.*;

@Entity
@Table(name = "hospital_tbls")
public class hospital_tbl
{

    @Id
    @GeneratedValue
    int id;
    String name;
    String bio;
    String lat;
    String lng;
    String address;
    String start_time;
    String end_time;
    String tel;
    String status;
    @ManyToOne
    @JoinColumn(name = "city_id")
    city_tbl city_id;

    public hospital_tbl() {
    }

    public hospital_tbl(String name, String bio, String lat, String lng, String address, String start_time, String end_time, String tel, String status, city_tbl city_id) {
        this.name = name;
        this.bio = bio;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.start_time = start_time;
        this.end_time = end_time;
        this.tel = tel;
        this.status = status;
        this.city_id = city_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public city_tbl getCity_id() {
        return city_id;
    }

    public void setCity_id(city_tbl city_id) {
        this.city_id = city_id;
    }
}
