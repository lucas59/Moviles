package com.example.obligatoriomoviles.presentacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.retorno;
import com.example.obligatoriomoviles.Clases.usuario;
import com.example.obligatoriomoviles.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Setear el focus a la opcion correspondiente (del 0 al numero de botones)
        navView.getMenu().getItem(2).setChecked(true);

        etEmail=findViewById(R.id.txtEmail);
        etPass=findViewById(R.id.txtContraseña);

//creo la variable session
        SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        boolean session =  preferences.contains("sessionCorreo");
        if(session==true){
            Intent i = new Intent(this,Perfil_usuario.class);
            startActivity(i);
        }

    }

    public void crearNuevaCuenta(View view){
        Intent intent = new Intent(this, NuevoUsuarioActivity.class);
        startActivity(intent);
    }

    public void login(View view){
        final String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        if(email.equals("") || pass.equals("")){
            return;
        }

        final APIInterface apiService = APICliente.getServidor().create(APIInterface.class);
        Call<retorno> call = apiService.login(email,pass);

        call.enqueue(new Callback<retorno>() {//verifico que los datos sean correctos
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
                if(response.body().getRetorno()){
                    Call<usuario> callUsuario = apiService.getDatosUsuario(email);//Si los datos son correctos entonces traigo el array con los datos y lo pongo en session
                    callUsuario.enqueue(new Callback<usuario>() {
                        @Override
                        public void onResponse(Call<usuario> call, Response<usuario> response) {
                            if (response.body()!=null){
                                SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("sessionCorreo",response.body().getCorreo());
                                editor.putString("sessionNombre",response.body().getNombre());
                                editor.putString("sessionApellido",response.body().getApellido());
                                editor.putInt("sessionEdad",response.body().getEdad());
                                editor.commit();
                                Toast.makeText(getApplicationContext(),"Bienvenido!", Toast.LENGTH_SHORT).show();
                                Intent intento = new Intent(getBaseContext(),Calendario_elementos.class);
                                startActivity(intento);
                            }else {
                                Toast.makeText(getApplicationContext(),"Verifique sus datos!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<usuario> call, Throwable t) {

                        }
                    });

                }else {
                    Toast.makeText(getApplicationContext(),"Verifique sus datos!", Toast.LENGTH_LONG).show();
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
