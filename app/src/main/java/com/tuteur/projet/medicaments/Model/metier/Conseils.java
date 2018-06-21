package com.tuteur.projet.medicaments.Model.metier;

/**
 * Created by Lenovo on 26/10/2017.
 */

public class Conseils {
    public String titre;
    public   String desc;
    public int id_;
    public int image;
    public String url;

    public Conseils(String name, String version, int id_, int image, String url) {
        this.titre = name;
        this.desc = version;
        this.id_ = id_;
        this.image=image;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


}
