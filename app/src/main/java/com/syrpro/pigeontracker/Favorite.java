package com.syrpro.pigeontracker;

import com.google.firebase.firestore.DocumentReference;

import java.util.EventListener;
import java.util.Objects;

public class Favorite  {
    private String title,status;

    public Favorite() {
    }

    public Favorite(String title, String status) {
        this.title = title;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(title, favorite.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, status);
    }
}
