package com.example.obligatoriomoviles.Clases.Cine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Comentario;
import com.example.obligatoriomoviles.Clases.retorno;
import com.example.obligatoriomoviles.R;
import com.example.obligatoriomoviles.presentacion.Calendario_elementos;
import com.example.obligatoriomoviles.presentacion.Menu_principal;
import com.example.obligatoriomoviles.presentacion.Perfil_elemento;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        final View view = inflater.inflate(R.layout.lista_comentarios, null);
        view.setOnClickListener(this);
        TextView id = view.findViewById(R.id.idcomentario);
        id.setVisibility(View.INVISIBLE);
        Button button_pun = (Button) view.findViewById(R.id.puntuar);
        button_pun.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView idcomentario = view.findViewById(R.id.idcomentario);
                TextView usuario = view.findViewById(R.id.titulo);
                RatingBar puntuacion = view.findViewById(R.id.puntuacion);

                String id = idcomentario.getText().toString();
                String Usuario = usuario.getText().toString();
                Integer comentario = Integer.parseInt(id);
                final APIInterface apiService_2 = APICliente.getServidor().create(APIInterface.class);
                Call<retorno> call = apiService_2.PuntuarComentario(comentario, Usuario, puntuacion.getRating());
                call.enqueue(new Callback<retorno>() {
                    @Override
                    public void onResponse(Call<retorno> call, Response<retorno> response) {
                        if (response.body().getRetorno()) {
                            Toast.makeText(mCtx, "Comentario puntuado", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(mCtx, "Error al puntuar comentario", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<retorno> call, Throwable t) {

                    }
                });

            }
        });

        ImageButton button_rep = (ImageButton) view.findViewById(R.id.reportar);
        button_rep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView idcomentario = view.findViewById(R.id.idcomentario);
                String id = idcomentario.getText().toString();
                Integer comentario = Integer.parseInt(id);
                final APIInterface apiService_2 = APICliente.getServidor().create(APIInterface.class);
                Call<retorno> call = apiService_2.ReportarComentario(comentario);
                call.enqueue(new Callback<retorno>() {
                    @Override
                    public void onResponse(Call<retorno> call, Response<retorno> response) {
                        if (response.body().getRetorno()) {
                            Toast.makeText(mCtx, "Comentario reportado", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(mCtx, Perfil_elemento.class);
                            mCtx.startActivity(i);
                        } else {
                            Toast.makeText(mCtx, "Error al reportar comentario", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<retorno> call, Throwable t) {
                    }
                });
            }
        });
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
        holder.textViewidcomentario.setText(product.getId().toString());
        

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setOnClickListener(View.OnClickListener listener2) {
        this.listener = listener2;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewComentario, textViewNombre, textViewidcomentario,textViewNombreReal;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            textViewComentario = itemView.findViewById(R.id.comentario);
            imageView = itemView.findViewById(R.id.perfil);
            textViewNombre = itemView.findViewById(R.id.titulo);
            textViewidcomentario = itemView.findViewById(R.id.idcomentario);
            textViewNombreReal = itemView.findViewById(R.id.nombre);
        }
    }
}