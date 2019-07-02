package com.example.obligatoriomoviles.Clases.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.obligatoriomoviles.Clases.Actor;
import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import java.util.List;



public class Actor_adapter extends RecyclerView.Adapter<Actor_adapter.ProductViewHolder> implements View.OnClickListener {


    //this context we will use to inflate the layout
    private Context mCtx;
    private View.OnClickListener listener;

    //we are storing all the products in a list
    private List<Actor> actoresList;

    //getting the context and product list with constructor
    public Actor_adapter(Context mCtx, List<Actor> actoresList) {
        this.mCtx = mCtx;
        this.actoresList = actoresList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        final View view = inflater.inflate(R.layout.lista_actores, parent, false);
        view.setOnClickListener(this);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Actor product = actoresList.get(position);

        //binding the data with the viewholder views
        if(product.getImagen() != null) {
            String fondo = "https://image.tmdb.org/t/p/w500" + product.getImagen().replace("\"", "");
            Picasso.get().load(fondo).fit().centerCrop().into(holder.imageView);
        }
        holder.textViewTitulo.setText(product.getNombre().replace("\"", ""));
        holder.textViewPuntaje.setText(product.getPersonaje().replace("\"", ""));

    }


    @Override
    public int getItemCount() {
        return actoresList.size();
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

        TextView textViewTitulo, textViewPuntaje;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.nombre);
            textViewPuntaje = itemView.findViewById(R.id.personaje);
            imageView = itemView.findViewById(R.id.imgView);
        }
    }
}