package com.example.obligatoriomoviles.presentacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Cine.Capitulo;
import com.example.obligatoriomoviles.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil_capitulo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitulo_perfil);
        APIInterface apiService = APICliente.getPelicula().create(APIInterface.class);
        Call<Capitulo> call = apiService.getCapitulo(getIntent().getExtras().getString("id"), getIntent().getExtras().getInt("temporada"), getIntent().getExtras().getInt("capitulo"), "0d81ceeb977ab515fd9f844377688c5a", "es");
        call.enqueue(new Callback<Capitulo>() {
            @Override
            public void onResponse(Call<Capitulo> call, Response<Capitulo> response) {
                if(response.isSuccessful()) {
                    TextView titulo = findViewById(R.id.titulo);
                    TextView sinopsis = findViewById(R.id.sinopsis);
                    ImageView fondo = findViewById(R.id.imagen_fondo);
                    String fondo_imagen = "https://image.tmdb.org/t/p/w500" + response.body().getImagen();
                    Picasso.get().load(fondo_imagen).fit().centerCrop().into(fondo);
                    titulo.setText(response.body().getNombre());
                    sinopsis.setText(response.body().getSinopsis());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Serie no Agregada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Capitulo> call, Throwable t) {

            }
        });
    }
}
