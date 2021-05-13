package com.syrpro.pigeontracker;

public class Trackable {
    private String name,doc;
    private boolean isFavorite,isPackage;

    public Trackable(){

    }

    public boolean isPackage() {
        return isPackage;
    }

    public void setPackage(boolean aPackage) {
        isPackage = aPackage;
    }

    public Trackable(String name, String doc, boolean isFavorite, boolean isPackage) {
        this.name = name;
        this.doc = doc;
        this.isFavorite = isFavorite;
        this.isPackage = isPackage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
