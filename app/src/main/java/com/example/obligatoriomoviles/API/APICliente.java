package com.example.obligatoriomoviles.API;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICliente {


    public static final String BASE_URL = "https://api.themoviedb.org/3/discover/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit_peliculas = null;
    private static Retrofit retrofit_servidor = null;
    private static final String BASE_URL2 = "http://api.themoviedb.org/3/";
    private static final String base_servidor = "http://192.168.1.44/ServidorMovil/public/";
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
    public static Retrofit getServidor() {
        if (retrofit_servidor==null) {
            OkHttpClient.Builder okhttp = new OkHttpClient.Builder();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okhttp.addInterceptor(logging);
            retrofit_servidor = new Retrofit.Builder()
                    .baseUrl(base_servidor)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttp.build())
                    .build();
        }
        return retrofit_servidor;
    }
}
