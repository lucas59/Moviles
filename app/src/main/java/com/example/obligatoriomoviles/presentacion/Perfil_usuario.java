package com.example.obligatoriomoviles.presentacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

        //Setear el focus a la opcion correspondiente (del 0 al numero de botones)
        navView.getMenu().getItem(2).setChecked(true);
    }

    public void desactivarCuenta(View view){
    SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
    String correo = preferences.getString("sessionCorreo", "");

        final APIInterface apiService = APICliente.getServidor().create(APIInterface.class);
        Call<retorno> call = apiService.desactivarUsuario(correo);

        call.enqueue(new Callback<retorno>() {//verifico que los datos sean correctos
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
                if(response.body().getRetorno()){
                    SharedPreferences session = getSharedPreferences("session", Context.MODE_PRIVATE);
                   // SharedPreferences.Editor editor = preferences.edit();
                    session.edit().clear().commit();
                    Intent intento = new Intent(getApplicationContext(),login.class);
                    Toast.makeText(getApplicationContext(),"Session cerrada!", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"No se puede desactivar!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<retorno> call, Throwable t) {

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(getApplicationContext(), Menu_principal.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;
                case R.id.navigation_buscar:
                    //     i = new Intent(Menu_principal.this, Perfil_elemento.class);
                    //     startActivity(i);
                    return true;
                case R.id.navigation_perfil:
                    i = new Intent(getApplicationContext(), Calendario_elementos.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;
                case R.id.navigation_sesion:
                    i = new Intent(getApplicationContext(), login.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;
                case R.id.navigation_registrarse:
                    i = new Intent(getApplicationContext(), NuevoUsuarioActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.infade,R.anim.outfade);
                    return true;

            }
            return false;
        }
    };



}
