package com.example.obligatoriomoviles.Clases;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class Peliculas_adapter extends RecyclerView.Adapter<Peliculas_adapter.ProductViewHolder> implements View.OnClickListener {


    //this context we will use to inflate the layout
    private Context mCtx;
    private View.OnClickListener listener;

    //we are storing all the products in a list
    private List<Peliculas> productList;

    //getting the context and product list with constructor
    public Peliculas_adapter(Context mCtx, List<Peliculas> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.lista_calendario, null);
        view.setOnClickListener(this);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Peliculas product = productList.get(position);

        //binding the data with the viewholder views
        String fondo = "https://image.tmdb.org/t/p/w500" + product.getPoster_path();
        holder.textViewTitulo.setText(product.getOriginal_title());
        holder.textViewPuntaje.setText(product.getNota());
        holder.textViewID.setText(product.getId());
        Picasso.get().load(fondo).fit().centerCrop().into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return productList.size();
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

        TextView textViewTitulo, textViewPuntaje, textViewID;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.Titulo);
            textViewPuntaje = itemView.findViewById(R.id.Puntaje);
            imageView = itemView.findViewById(R.id.Poster);
            textViewID = itemView.findViewById(R.id.id);
        }
    }
}