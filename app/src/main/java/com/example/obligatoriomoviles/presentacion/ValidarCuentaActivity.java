package com.example.obligatoriomoviles.presentacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.retorno;
import com.example.obligatoriomoviles.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidarCuentaActivity extends AppCompatActivity {
    private EditText et1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_cuenta);
        et1=findViewById(R.id.txtCodigo);
    }

    public  void validarCuenta(View view){
        final String codigo = et1.getText().toString();

        if(codigo.equals("")){
            return;
        }


        APIInterface apiService = APICliente.getServidor().create(APIInterface.class);
        Call<retorno> call = apiService.validarCuenta(codigo);

        call.enqueue(new Callback<retorno>() {
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
                if(response.body().getRetorno()){
                    SharedPreferences preferences = getSharedPreferences("temporales", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("tokenActual",codigo);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Usuario activado", Toast.LENGTH_SHORT).show();
                    Intent intento = new Intent(getBaseContext(),nuevo_usuario2.class);
                    startActivity(intento);
                }else {
                    Toast.makeText(getApplicationContext(), "Codigo invalido", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<retorno> call, Throwable t) {

            }
        });




    }



}
