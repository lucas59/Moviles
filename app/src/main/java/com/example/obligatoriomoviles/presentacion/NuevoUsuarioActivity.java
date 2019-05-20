package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIError;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.Peliculas;
import com.example.obligatoriomoviles.Clases.Peliculas_adapter;
import com.example.obligatoriomoviles.Clases.retorno;
import com.example.obligatoriomoviles.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NuevoUsuarioActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        this.txtEmail=findViewById(R.id.txtEmail);
        this.txtPass=findViewById(R.id.txtPass);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(4).setChecked(true);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void iniciar(View view){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void irAValidar(View view){
        Intent intento = new Intent(this,ValidarCuentaActivity.class);
        startActivity(intento);
    }

    public void NuevoUsuario(View view ){
        String email = txtEmail.getText().toString();
        String pass = txtPass.getText().toString();

        if (email.equals("")||pass.equals("")){
            return ;
        }


        APIInterface apiService = APICliente.getServidor().create(APIInterface.class);
        Call<retorno> call = apiService.altaUsuario(email,pass);

        call.enqueue(new Callback<retorno>() {
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
            if(response.body().getRetorno()){
                Toast.makeText(getApplicationContext(),"Bienvenido!", Toast.LENGTH_SHORT).show();
                System.out.println("llegooo");
                enviar_a_validar();
                }else {
                Toast.makeText(getApplicationContext(),"Verifique sus datos!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<retorno> call, Throwable t) {

            }
        });
    }

    public void enviar_a_validar(){
        Intent intento = new Intent(this,ValidarCuentaActivity.class);
        startActivity(intento);
        }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(NuevoUsuarioActivity.this, Menu_principal.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_buscar:
                    i = new Intent(NuevoUsuarioActivity.this, Perfil_elemento.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_perfil:
                    return true;
                case R.id.navigation_sesion:
                    i = new Intent(NuevoUsuarioActivity.this, login.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_registrarse:
                    i = new Intent(NuevoUsuarioActivity.this, NuevoUsuarioActivity.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };

}
