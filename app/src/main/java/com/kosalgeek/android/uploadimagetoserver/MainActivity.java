package com.kosalgeek.android.uploadimagetoserver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();

    //Declarando los Image View de los iconos
    ImageView ivCamera, ivGallery, ivUpload, ivImage;

    //declarando la libreria de photoutil del autor
    CameraPhoto cameraPhoto;

    // este es un numero cualquiera para cumplir con los parametros pedidos en el camara request
    final int CAMERA_REQUEST = 13323;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            //Este es el icono del correo o snackbar que al apretarlo muestra ese texto
            public void onClick(View view) {
               Snackbar.make(view, "Pon aqui tu accion washo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //el contexto podria ser this, pero si estamos usando fragments es asi:
        cameraPhoto = new CameraPhoto(getApplicationContext());

        //Con r.id.ivCamara buscamos por el id en el xml
        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivUpload = (ImageView) findViewById(R.id.ivUpload);
        ivImage = (ImageView) findViewById(R.id.ivImage);

        // Ahora generamos un evento para la camara
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Aqui usaremos una libreria sugerida por el creador de PhotoUtil.
                  Existe un gran codigo atras de esta libreria, basicamente es para usar la camara
                  Cuando abres la camara y luego la cierras quieres un resultado de vuelta, esto
                  lo haremos afuera del onCreate.
                  Esto es startActivity FOR RESULT*/
                    try {
                        startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                        cameraPhoto.addToGallery();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),
                                "Algo salio mal mientras se tomaba la foto", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
    // Este es el result que obtenemos de la camara
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == CAMERA_REQUEST){
            cameraPhoto.getPhotoPath();
        }
    }
}
