package com.example.obligatoriomoviles.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICliente {


    public static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit_peliculas = null;
    public static final String BASE_URL2 = "http://api.themoviedb.org/3/movie/";

    public static Retrofit getCalendario() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getPelicula() {
        if (retrofit_peliculas==null) {
            retrofit_peliculas = new Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit_peliculas;
    }
}
