package com.example.obligatoriomoviles.Clases.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class generalAdapters extends RecyclerView.Adapter<generalAdapters.ProductViewHolder> implements View.OnClickListener {


    //this context we will use to inflate the layout
    private Context mCtx;
    private View.OnClickListener listener;

    //we are storing all the products in a list
    private List<Cine> lista;

    //getting the context and product list with constructor
    public generalAdapters(Context mCtx, List<Cine> lista) {
        this.mCtx = mCtx;
        this.lista = lista;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        final View view = inflater.inflate(R.layout.listageneral, parent, false);
        view.setOnClickListener(this);
        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Cine pelicula = lista.get(position);

        //binding the data with the viewholder views
        String fondo = "https://image.tmdb.org/t/p/w500" + pelicula.getPoster_path().replace("\"","");
        holder.textViewTitulo2.setText(pelicula.getOriginal_title().replace("\"",""));

        Picasso.get().load(fondo).fit().centerCrop().into(holder.imageView2);

    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnClickListener(View.OnClickListener listener2){
        this.listener = listener2;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitulo2;
        ImageView imageView2;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewTitulo2 = itemView.findViewById(R.id.titulo_general);
            imageView2 = itemView.findViewById(R.id.imagen_general);
        }
    }
}