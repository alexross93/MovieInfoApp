package com.example.alex.movieinfoapp;


public class MovieInfo {
    private int id;
    private String posterUrl;
    private String title;
    private String actors;
    private String length;
    private String description;
    private float rating;
    private String genre;
    private boolean created = false;


    public MovieInfo(int id, String poster, String title, String actors, String length, String description, float rating, String genre) {
        this.id = id;
        this.posterUrl = poster;
        this.title = title;
        this.actors = actors;
        this.length = length;
        this.description = description;
        this.rating = rating;
        this.genre = genre;
    }

    public MovieInfo(String poster, String title, String actors, String length, String description, float rating, String genre) {
        this.posterUrl = poster;
        this.title = title;
        this.actors = actors;
        this.length = length;
        this.description = description;
        this.rating = rating;
        this.genre = genre;
    }


   /* public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String poster) {
        this.posterUrl = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean getCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }
}