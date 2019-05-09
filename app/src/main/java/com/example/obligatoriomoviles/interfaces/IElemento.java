package com.example.obligatoriomoviles.interfaces;
import com.example.obligatoriomoviles.Clases.Peliculas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface IElemento {
    String API_ROUTE = "/posts";

    @GET(API_ROUTE)
    Call< List<Peliculas> > getPost();
}
