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
        String buildString = titre.replaceAll(" ", "_").toLowerCase();
        buildString = Normalizer.normalize(buildString, Normalizer.Form.NFD);
        this.stringId = buildString.replaceAll("[^\\p{ASCII}]", "");
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

    private String titre;

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    private String ville;

    public String getStringId() {
        return stringId;
    }

    private String stringId;
    private String details;

    public Activite(int id, double longtitude, double latitude, String titre, String details){
        this.id = id;
        this.longtitude = longtitude;
        this.titre = titre;
        this.details = details;
    }
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
                ", details='" + details + '\'' +
                '}';
    }
}
