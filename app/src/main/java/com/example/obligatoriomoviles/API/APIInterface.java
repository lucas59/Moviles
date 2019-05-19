package com.example.obligatoriomoviles.API;

import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.Clases.usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET(".")
    Call<Cine> getImagen(@Query("sort_by") String sort_by, @Query("year") Integer año, @Query("language") String lenguaje, @Query("api_key") String key);

    @GET("{id}")
    Call<Cine> getPelicula(@Path("id") String id,@Query("api_key") String api_key,@Query("append_to_response") String filtro,@Query("language") String idioma);


   // @POST('')
  //  Call <> getUsuario(@Path("correo") String correo);



}
