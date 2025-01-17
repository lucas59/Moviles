package com.example.obligatoriomoviles.presentacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.retorno;
import com.example.obligatoriomoviles.Clases.usuario;
import com.example.obligatoriomoviles.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil_usuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        final SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);

        if (preferences.getString("sessionCorreo", "").equals("")) {
            Intent intento = new Intent(this, login.class);
            startActivity(intento);
            finish();
        } else {
            final APIInterface apiService = APICliente.getServidor().create(APIInterface.class);
            Call<usuario> call = apiService.getDatosUsuario(preferences.getString("sessionCorreo", ""));
            call.enqueue(new Callback<usuario>() {
                @Override
                public void onResponse(Call<usuario> call, Response<usuario> response) {
                    final String nombre = preferences.getString("sessionNombre", null);
                    final int comentarios = response.body().getNumero_comentario();

                    TextView nombre_t = (TextView) findViewById(R.id.Nombre);
                    TextView comentarios_t = (TextView) findViewById(R.id.NComentarios);
                    final int elementos = response.body().getnContenido();
                    TextView visionado_t = (TextView) findViewById(R.id.visionado_numero);
                    visionado_t.setText(String.valueOf(elementos));
                    nombre_t.setText(nombre);

                    comentarios_t.setText(String.valueOf(comentarios));
                    String foto = preferences.getString("sessionFoto", null);
                    if (!foto.equals("")) {
                        final byte[] decodedBytes = Base64.decode(foto, Base64.DEFAULT);
                        ImageView perfil = findViewById(R.id.imgView);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        perfil.setImageBitmap(decodedBitmap);

                    }
                }

                @Override
                public void onFailure(Call<usuario> call, Throwable t) {

                }

            });

        }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MTV Show");
        }

        @Override
        public void onBackPressed () {
            Intent intento = new Intent(getApplicationContext(), Menu_principal.class);
            startActivity(intento);
        }

        public void desactivarCuenta (View view){
            SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
            String correo = preferences.getString("sessionCorreo", "");

            final APIInterface apiService = APICliente.getServidor().create(APIInterface.class);
            Call<retorno> call = apiService.desactivarUsuario(correo);

            call.enqueue(new Callback<retorno>() {//verifico que los datos sean correctos
                @Override
                public void onResponse(Call<retorno> call, Response<retorno> response) {
                    if (response.body().getRetorno()) {
                        SharedPreferences session = getSharedPreferences("session", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = session.edit();
                        editor.clear();
                        editor.commit();
                        Intent intento = new Intent(getApplicationContext(), login.class);
                        startActivity(intento);
                        finish();
                        Toast.makeText(getApplicationContext(), "Session cerrada!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "No se puede desactivar!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<retorno> call, Throwable t) {

                }
            });
        }

        public void Cerrar_sesion (View view){
            SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("sessionCorreo", null);
            editor.putString("sessionNombre", null);
            editor.putString("sessionApellido", null);
            editor.putInt("sessionEdad", 0);
            editor.commit();
            Intent i = new Intent(getApplicationContext(), login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent i = new Intent(getApplicationContext(), Menu_principal.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.infade, R.anim.outfade);
                        finish();
                        return true;
                    case R.id.navigation_notificacion:
                        //     i = new Intent(Menu_principal.this, Perfil_elemento.class);
                        //     startActivity(i);
                        //finish();
                        return true;
                    case R.id.navigation_calendario:
                        i = new Intent(getApplicationContext(), Calendario_elementos.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.infade, R.anim.outfade);
                        finish();
                        return true;
                }
                return false;
            }
        };

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            return true;
        }


        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case android.R.id.home:
                    Intent intento = new Intent(this, Menu_principal.class);
                    startActivity(intento);
                    finish();
            }
            return super.onOptionsItemSelected(item);
        }

        public void editarPerfil (View view){
            Intent intento = new Intent(this, modificar_usuario.class);
            startActivity(intento);
        }


    }
