package com.example.obligatoriomoviles.presentacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.ImageUtil;
import com.example.obligatoriomoviles.Clases.retorno;
import com.example.obligatoriomoviles.Clases.usuario;
import com.example.obligatoriomoviles.R;

import java.io.Console;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class modificar_usuario extends AppCompatActivity {

    APIInterface apiServer = APICliente.getServidor().create(APIInterface.class);
    private String correoSession;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText intEdad;
    private ImageView imgPerfil;
    private Uri foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);

        SharedPreferences preferenciasSession = getSharedPreferences("session", Context.MODE_PRIVATE);
        correoSession = preferenciasSession.getString("sessionCorreo", "");
        txtApellido = findViewById(R.id.txtApellido);
        txtNombre = findViewById(R.id.txtNombre);
        intEdad = findViewById(R.id.intEdad);
        imgPerfil = findViewById(R.id.imgPerfil);

        if (preferenciasSession.getString("sessionCorreo", "").equals("")) {
            Toast.makeText(getApplicationContext(), "No ha iniciado sessión", Toast.LENGTH_SHORT).show();
            Intent intento = new Intent(this, login.class);
        } else {

            Call<usuario> call = apiServer.getDatosUsuario(preferenciasSession.getString("sessionCorreo", ""));
            call.enqueue(new Callback<usuario>() {
                @Override
                public void onResponse(Call<usuario> call, Response<usuario> response) {
                    txtNombre.setText(response.body().getNombre());
                    txtApellido.setText(response.body().getApellido());
                    intEdad.setText(String.valueOf(response.body().getEdad()));

                    String foto = response.body().getFoto();
                    if (!foto.equals("")) {
                        final byte[] decodedBytes = Base64.decode(foto, Base64.DEFAULT);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        imgPerfil.setImageBitmap(decodedBitmap);
                    }
                }

                @Override
                public void onFailure(Call<usuario> call, Throwable t) {

                }
            });
        }
        getSupportActionBar().setTitle("MTV Show");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            imgPerfil.setImageURI(path);
            this.foto = data.getData();
        }
    }


    public void subirFoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecciona la aplicación"), 10);
    }

    public void actualizar(final View view) throws IOException {
        final String nombre = txtNombre.getText().toString();
        final String apellido = txtApellido.getText().toString();
        final int edad = Integer.valueOf(intEdad.getText().toString());
        String foto = "";

        if (this.foto != null) {
            Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), this.foto);
            foto = ImageUtil.convert(bm);
        }
        if (nombre.equals("") || apellido.equals("") || this.intEdad.getText().toString().equals("")) {
            return;
        }

        Call<retorno> call = apiServer.actualizarPerfil(correoSession, nombre, apellido, edad, foto);
        call.enqueue(new Callback<retorno>() {
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {

                if (response.body().getRetorno()) {
                    Toast.makeText(getApplicationContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferenciasSession = getSharedPreferences("session", Context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = preferenciasSession.edit();

                    Call<usuario> call2 = apiServer.getDatosUsuario(preferenciasSession.getString("sessionCorreo", ""));
                    call2.enqueue(new Callback<usuario>() {
                        @Override
                        public void onResponse(Call<usuario> call, Response<usuario> response) {
                            editor.putString("sessionCorreo", response.body().getCorreo());
                            editor.putString("sessionNombre", response.body().getNombre());
                            editor.putString("sessionApellido", response.body().getApellido());
                            editor.putString("sessionFoto", response.body().getFoto());
                            editor.putInt("sessionEdad", response.body().getEdad());
                            editor.putInt("sessionNComentarios", response.body().getNumero_comentario());
                            editor.commit();
                        }

                        @Override
                        public void onFailure(Call<usuario> call, Throwable t) {

                        }
                    });

                    Intent intento = new Intent(getApplicationContext(), Perfil_usuario.class);
                    startActivity(intento);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Verifique sus datos", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<retorno> call, Throwable t) {

            }
        });

    }


}
