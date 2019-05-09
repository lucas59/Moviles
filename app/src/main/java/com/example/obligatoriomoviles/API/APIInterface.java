package com.example.obligatoriomoviles.API;

import com.example.obligatoriomoviles.Clases.Peliculas;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("{t}")
    Call<Peliculas> getImagen(@Path("t") String nombre , @Query("api_key") String key);
}
