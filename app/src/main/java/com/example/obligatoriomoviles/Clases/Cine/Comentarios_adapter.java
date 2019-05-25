package com.example.obligatoriomoviles.Clases.Cine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.obligatoriomoviles.Clases.Cine.Cine;
import com.example.obligatoriomoviles.Clases.Comentario;
import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class Comentarios_adapter extends RecyclerView.Adapter<Comentarios_adapter.ProductViewHolder> implements View.OnClickListener {


    //this context we will use to inflate the layout
    private Context mCtx;
    private View.OnClickListener listener;

    //we are storing all the products in a list
    private List<Comentario> productList;

    //getting the context and product list with constructor
    public Comentarios_adapter(Context mCtx, List<Comentario> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.lista_comentarios, null);
        view.setOnClickListener(this);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Comentario product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewComentario.setText(product.getTexto());
        holder.imageView.setImageResource(R.drawable.user);
        holder.textViewNombre.setText(product.getUsuario_correo());


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

        TextView textViewComentario,textViewNombre;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewComentario = itemView.findViewById(R.id.comentario);
            imageView= itemView.findViewById(R.id.perfil);
            textViewNombre = itemView.findViewById(R.id.titulo);
        }
    }
}