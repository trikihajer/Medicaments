package com.tuteur.projet.medicaments.ListeProfil;

/**
 * Created by Lenovo on 20/11/2017.
 */


import java.text.SimpleDateFormat;


public class MyImage {
    private String title, sex, path;
    private String  datetimeLong;
    private SimpleDateFormat df = new SimpleDateFormat("MMMM d, yy  h:mm");

    public MyImage(String title, String sex, String path, String datetimeLong) {
        this.title = title;
        this.sex = sex;
        this.path = path;
        this.datetimeLong = datetimeLong;
    }

    public MyImage() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSexe() {
        return sex;
    }

    public void setSexe(String description) {
        this.sex = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDatetimeLong() {
        return datetimeLong;
    }

    public void setDatetimeLong(String datetimeLong) {
        this.datetimeLong = datetimeLong;
    }

    public SimpleDateFormat getDf() {
        return df;
    }

    public void setDf(SimpleDateFormat df) {
        this.df = df;
    }
}