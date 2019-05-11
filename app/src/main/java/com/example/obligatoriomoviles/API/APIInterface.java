package com.example.obligatoriomoviles.API;

import com.example.obligatoriomoviles.Clases.Peliculas;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET(".")
    Call<Peliculas> getImagen(@Query("sort_by") String sort_by,@Query("year") Integer año,@Query("language") String lenguaje,@Query("api_key") String key);

    @GET("{id}")
    Call<Peliculas> getPelicula(@Path("id") String id,@Query("api_key") String api_key);


}
