package com.example.obligatoriomoviles.presentacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.obligatoriomoviles.API.APICliente;
import com.example.obligatoriomoviles.API.APIInterface;
import com.example.obligatoriomoviles.Clases.ImageUtil;
import com.example.obligatoriomoviles.Clases.retorno;
import com.example.obligatoriomoviles.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class nuevo_usuario2 extends AppCompatActivity {
    private FloatingActionButton btnSubirFoto;
    private TextInputLayout txtNombre;
    private TextInputLayout txtApellido;
    private TextInputLayout txtEdad;
    private ImageView img;
    private Uri foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario2);
        this.btnSubirFoto=findViewById(R.id.btnFoto);
        this.txtNombre=findViewById(R.id.txtNombre);
        this.txtApellido=findViewById(R.id.txtApellido);
        this.txtEdad=findViewById(R.id.txtEdad);
        this.img=findViewById(R.id.imgView);
    }

    public void subirFoto(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Selecciona la aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path = data.getData();
            img.setImageURI(path);
            this.foto=data.getData();
        }
    }

    public void subirDatos(View view) throws IOException {
        String nombre = this.txtNombre.getEditText().getText().toString();
        String apellido = this.txtApellido.getEditText().getText().toString();
        int edad = Integer.valueOf(this.txtEdad.getEditText().getText().toString());
        if(nombre.equals("") ||apellido.equals("")){
            return;
        }

        Bitmap bm =  MediaStore.Images.Media.getBitmap(this.getContentResolver(),this.foto);
        String base64 = ImageUtil.convert(bm);


        SharedPreferences preferences = getSharedPreferences("temporales", Context.MODE_PRIVATE);
        String token =  preferences.getString("tokenActual","");
        if(token==null){
            return;
        }

        APIInterface apiService = APICliente.getServidor().create(APIInterface.class);
        Call<retorno> call = apiService.altaUsuario2(nombre,apellido,edad,base64,token);

        call.enqueue(new Callback<retorno>() {
            @Override
            public void onResponse(Call<retorno> call, Response<retorno> response) {
                if(response.body().getRetorno()){
                    Toast.makeText(getApplicationContext(),"Bienvenido!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Verifique sus datos!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<retorno> call, Throwable t) {

            }
        });


    }

    private byte[] getImagen(String path){
        Bitmap bm = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm = Bitmap
        return baos.toByteArray();
    }
}
