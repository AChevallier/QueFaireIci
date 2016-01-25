package com.example.steve.quefaireici;

import java.io.Serializable;
import java.text.Normalizer;

/**
 * Created by alexandre on 12/01/16.
 */
public class Activite implements Serializable{
    public void setId(int id) {
        this.id = id;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setTitre(String titre) {
        this.titre = titre;
        if(ville != null && stringId == null){
            this.stringId = createId();
        }

    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getTitre() {
        return titre;
    }

    public String getDetails() {
        return details;
    }

    private int id;
    private double longtitude;
    private double latitude;

    private String titre = null;

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
        if(titre != null && stringId == null){
            this.stringId = createId();
        }
    }

    private String ville = null;

    public String getStringId() {
        return stringId;
    }

    private String stringId = null;
    private String details;

    public String getHoraires() {
        return horaires;
    }

    public void setHoraires(String horaires) {
        this.horaires = horaires;
    }

    private String horaires;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    private String tel;
    private String adresse;


    public Activite(){

    }

    @Override
    public String toString() {
        return "Activite{" +
                "id=" + id +
                ", longtitude=" + longtitude +
                ", latitude=" + latitude +
                ", titre='" + titre + '\'' +
                ", ville='" + ville + '\'' +
                ", stringId='" + stringId + '\'' +
                ", details='" + details + '\'' +
                ", horaires='" + horaires + '\'' +
                ", tel='" + tel + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }

    private String createId(){
        String villeModif = ville.replaceAll("-| ", "_").toLowerCase();
        String buildString = titre.replaceAll("-| ", "_").toLowerCase();
        buildString = Normalizer.normalize(buildString, Normalizer.Form.NFD);
        villeModif = Normalizer.normalize(villeModif, Normalizer.Form.NFD);
        buildString =  buildString.replaceAll("[^\\p{ASCII}]", "");
        villeModif =  villeModif.replaceAll("[^\\p{ASCII}]", "");
        return villeModif+"_"+buildString;
    }
}
