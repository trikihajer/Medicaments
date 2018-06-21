package com.tuteur.projet.medicaments.Model.metier;

/**
 * Created by Lenovo on 24/11/2017.
 */

public class Medicas {
    String nomMed,pathologie,photoMed,freq,dateDeb,momentPrise;
    int nbComp,duree,horaire;

    public Medicas(String nomMed, String pathologie, String photoMed, String freq, String dateDeb, String momentPrise, int nbComp, int duree, int horaire) {
        this.nomMed = nomMed;
        this.pathologie = pathologie;
        this.photoMed = photoMed;
        this.freq = freq;
        this.dateDeb = dateDeb;
        this.momentPrise = momentPrise;
        this.nbComp = nbComp;
        this.duree = duree;
        this.horaire = horaire;
    }

    public Medicas() {

    }


    public String getNomMed() {
        return nomMed;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }

    public String getPathologie() {
        return pathologie;
    }

    public void setPathologie(String pathologie) {
        this.pathologie = pathologie;
    }

    public String getPhotoMed() {
        return photoMed;
    }

    public void setPhotoMed(String photoMed) {
        this.photoMed = photoMed;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(String dateDeb) {
        this.dateDeb = dateDeb;
    }

    public String getMomentPrise() {
        return momentPrise;
    }

    public void setMomentPrise(String momentPrise) {
        this.momentPrise = momentPrise;
    }

    public int getNbComp() {
        return nbComp;
    }

    public void setNbComp(int nbComp) {
        this.nbComp = nbComp;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getHoraire() {
        return horaire;
    }

    public void setHoraire(int horaire) {
        this.horaire = horaire;
    }
}
