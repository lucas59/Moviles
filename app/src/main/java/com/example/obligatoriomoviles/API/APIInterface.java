package com.example.obligatoriomoviles.API;
import com.example.obligatoriomoviles.Clases.Comentario;
import com.example.obligatoriomoviles.Clases.retorno;

import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.Clases.usuario;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET(".")
    Call<Cine> getImagen(@Query("sort_by") String sort_by, @Query("year") Integer año, @Query("language") String lenguaje, @Query("api_key") String key);

    @GET("{id}")
    Call<Cine> getPelicula(@Path("id") String id,@Query("api_key") String api_key,@Query("append_to_response") String filtro,@Query("language") String idioma);


    @POST("usuario/nuevo")
    @FormUrlEncoded
    Call <retorno> altaUsuario(@Field("correo") String correo, @Field("pass") String pass);

    @GET("validacion/{token}")
    Call <retorno> validarCuenta(@Path("token") String codigo);

    @POST("usuario/login")
    @FormUrlEncoded
    Call <retorno> login(@Field("correo") String correo, @Field("pass") String pass);

    @GET("usuario/{correo}")
    Call<usuario> getDatosUsuario(@Path("correo") String email);

    @GET("usuario/desactivar/{correo}")
    Call<retorno> desactivarUsuario(@Path("correo") String email);


    @GET("contenido/comentario")
    Call<retorno> SetComentario(@Query("comentario") String comentario,  @Query("capitulo_id") Integer capitulo, @Query("contenido_id") Integer contenido, @Query("usuario") String usuario, @Query("fecha") String fecha, @Query("genero") String genero, @Query("titulo_elemento") String titulo_elemento);

    @GET("contenido/lista_comentario")
    Call<retorno> getComentario( @Query("id") String id);

}
