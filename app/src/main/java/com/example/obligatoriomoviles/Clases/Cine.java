package com.example.obligatoriomoviles.Clases;


import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cine {

    @SerializedName("original_title")
    private String original_title;
    @SerializedName("original_name")
    private String original_name;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("results")
    private List<Cine> lista_elementos;
    @SerializedName("seasons")
    private List<Temporadas> lista_temporadas;
    @SerializedName("vote_average")
    private String nota;
    @SerializedName("overview")
    private String sinopsis;
    @SerializedName("release_date")
    private String fecha;
    @SerializedName("id")
    private String id;
    @SerializedName("credits")
    private JsonObject creditos;
    @SerializedName("profile_path")
    private String profile_path;
    @SerializedName("key")
    private String video;
    @SerializedName("first_air_date")
    private String fecha_serie;
    @SerializedName("sigue_id")
    private Integer id_sigue;

    public Integer getId_sigue() {
        return id_sigue;
    }

    public String getFecha_serie() {
        return fecha_serie;
    }

    public String getVideo() {
        return video;
    }

    public JsonObject getCreditos() {
        return creditos;
    }

    public void setCreditos(JsonObject creditos) {
        this.creditos = creditos;
    }




    public String getProfile_path() {
        return profile_path;
    }



    private List<usuario> seguidores;



    public String getSinopsis() {
        return sinopsis;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNota() {
        return nota;
    }

    public List<Cine> getData(){
        return lista_elementos;
    }

    public Cine(String original_title, String nota, String poster_path, String id,String fecha) {
        this.original_title = original_title;
        this.nota = nota;
        this.poster_path = poster_path;
        this.id = id;
        this.fecha = fecha;
    }

    public String getBackdrop_path() {

        return backdrop_path;
    }

    public String getOriginal_title() {

        return original_title;
    }

    public List<Temporadas> getLista_temporadas() {
        return lista_temporadas;
    }

    public String getPoster_path() {

        return poster_path;
    }


    public String getOriginal_name() {
        return original_name;
    }

    public String getId() {
        return id;
    }


}
