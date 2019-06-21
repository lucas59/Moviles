package com.example.obligatoriomoviles.presentacion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.retorno;
import com.example.obligatoriomoviles.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoUsuarioActivity extends AppCompatActivity {

    private TextInputLayout txtEmail;
    private TextInputLayout txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        this.txtEmail=findViewById(R.id.txtEmail);
        this.txtPass=findViewById(R.id.txtPass);
    }

    public void iniciar(View view){
        Intent intent = new Intent(this, login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void irAValidar(View view){
        Intent intento = new Intent(this,ValidarCuentaActivity.class);
        startActivity(intento);
    }

    public void NuevoUsuario(View view ){
        String email = txtEmail.getEditText().getText().toString();
        String pass = txtPass.getEditText().getText().toString();

        if (email.equals("")||pass.equals("")){
            return ;
        }


        APIInterface apiService = APICliente.getServidor().create(APIInterface.class);
        Call<retorno> call = apiService.altaUsuario(email,pass);

        call.enqueue(new Callback<retorno>() {
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
            if(response.body().getRetorno()){
                Toast.makeText(getApplicationContext(), "Bienvenido!", Toast.LENGTH_SHORT).show();
                enviar_a_validar();
                }else {
                Toast.makeText(getApplicationContext(), "Verifique sus datos!", Toast.LENGTH_SHORT).show();
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

}
