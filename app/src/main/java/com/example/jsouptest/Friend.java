package com.example.jsouptest;

public class Friend {
    private String first_name;
    private String last_name;
    private String city;
    private String bdate;
    private String photo;
    private String domain;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Friend(String first_name, String last_name, String city, String bdate, String photo, String domain){
        this.first_name = first_name;
        this.last_name = last_name;
        this.city = city;
        this.bdate = bdate;
        this.photo = photo;
        this.domain = domain;
    }
}
