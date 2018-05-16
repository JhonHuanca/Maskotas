package bo.com.innovasoft.maskotas;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;

public class Crear_Cuenta extends AppCompatActivity {

    private final String CARPETA_RAIZ="Maskotas/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    ImageView imagen;
    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear__cuenta);
        imagen=(ImageView) findViewById(R.id.fotousuario);
    }

    public void onclick(View view) {
        cargarimagen();
    }

    private void cargarimagen() {

        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen", "Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(Crear_Cuenta.this);
        alertOpciones.setTitle("Seleccione una Opcion");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (opciones[i].equals("Tomar Foto")){
                   tomarFotografia();
                }else {
                    if (opciones[i].equals("Cargar Imagen")){

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion "),COD_SELECCIONA);
                    }else {
                        dialogInterface.dismiss();
                    }

                }

            }
        });
        alertOpciones.show();


    }

    private void tomarFotografia() {
        File fileImagen = new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean iscreada=fileImagen.exists();
        String nombreImagen="";

        if (iscreada==false) {
            iscreada=fileImagen.mkdirs();
        }

        if (iscreada==true) {
             nombreImagen=(System.currentTimeMillis()/100)+"jpg";
        }
        path=Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;/*ruta de almacenamiento*/
        File imagen=new File(path);
        /*Activar la opcion de camara*/
        Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);/*lazamos la camara*/
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));/*enviar imagen con el parametro*/
        startActivityForResult(intent,COD_FOTO);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case COD_SELECCIONA:
                    Uri path =data.getData();
                    imagen.setImageURI(path);
                 break;
                case COD_FOTO:




                 break;

            }


            Uri path=data.getData();
            imagen.setImageURI(path);
        }
    }
}