package com.example.eduardopalacios.paint2;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {


    public static final int IMAGE_GALLERY_REQUEST = 20;
    ImageButton boton_Rojo;
    ImageButton boton_Azul;
    ImageButton boton_Morado;
    ImageButton boton_Verde;
    ImageButton boton_Negro;
    ImageButton tamaño_Pincel;
    ImageButton borrar_Trazo;
    ImageButton nuevolienzo;
    ImageButton guardarImagen;
    ImageButton retroceder;
    ImageView colocarImagen;
    ImageView imagenpunto;
    SeekBar barraValor;
    private ImageView imagen;
    private  ViewGroup rootlayout;
    private int _xDelta;
    private int _yDelta;

    Lienzo lienzo;


    float pincel_pequeño;
    float pincel_mediano;
    float pincel_grande;
    float pincel_defecto;
    float pincel_devuelto;
    String colorPincel="#ff000000";
    boolean presionoBorrar=false;
    boolean cargarimagen=false;
    int Hojaroja;
    int Hojaazul;
    int Hojamorada;
    int Hojaverde;
    int Hojanegra;
    int HojaBlanca;
    int valorPincel=0;
    int valorbarragoma=0;
    int valorbarrapincel=0;
    boolean valorseekbarGoma;
    boolean valorseekbarPincel;
    boolean[] valorSeekbarAnterior=new boolean[2];
    int indicebooleano=0;
    String borrarcolor="#FFFFFF";
    String tamañopincel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // rootlayout=(ViewGroup)findViewById(R.id.view_root);
        //imagen=(ImageView) rootlayout.findViewById(R.id.imagenn);
        //RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(150,150);
       // imagen.setLayoutParams(layoutParams);
        //imagen.setOnTouchListener(new ChoiceTouchListener());

        ImageButton boton_Rojo=(ImageButton)findViewById(R.id.botonRojo);
        ImageButton boton_Azul=(ImageButton)findViewById(R.id.botonAzul);
        ImageButton boton_Morado=(ImageButton)findViewById(R.id.botonMorado);
        ImageButton boton_Verde=(ImageButton)findViewById(R.id.botonVerde);
        ImageButton boton_Negro=(ImageButton)findViewById(R.id.botonNegro);
        ImageButton retroceder=(ImageButton)findViewById(R.id.retroceder);
        colocarImagen=(ImageView) findViewById(R.id.colocarimagen);






        boton_Rojo.setOnClickListener(this);
        boton_Azul.setOnClickListener(this);
        boton_Morado.setOnClickListener(this);
        boton_Verde.setOnClickListener(this);
        boton_Negro.setOnClickListener(this);
        retroceder.setOnClickListener(this);
        lienzo=(Lienzo)findViewById(R.id.lienzo);

        pincel_pequeño=10;
        pincel_mediano=15;
        pincel_grande=20;
        pincel_defecto=10;






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    /*private final class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int x=(int)event.getRawX();
            final  int y=(int)event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK)
            {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams Params=(RelativeLayout.LayoutParams) v.getLayoutParams();
                    _xDelta=x-Params.leftMargin;
                    _yDelta=y-Params.topMargin;

                    break;

                case MotionEvent.ACTION_UP:
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)v.getLayoutParams();
                    layoutParams.leftMargin=x-_xDelta;
                    layoutParams.topMargin=y-_yDelta;
                    layoutParams.rightMargin= 250;
                    layoutParams.bottomMargin=250;
                    v.setLayoutParams(layoutParams);

                    break;



            }
            rootlayout.invalidate();


            return true;
        }
    }*/






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    public void onClick(View v){

        switch(v.getId()) {
            case R.id.botonRojo:
                 colorPincel = "#ffff4444";



                if(presionoBorrar) {



                    lienzo.setTamañoPunto(pincel_devuelto);
                    lienzo.color(colorPincel);
                }
                else {

                    lienzo.color(colorPincel);

                }




                break;
            case R.id.botonAzul:

                colorPincel = "#ff00ddff";

                if (presionoBorrar) {
                    lienzo.setTamañoPunto(pincel_devuelto);
                    lienzo.color(colorPincel);
                } else {

                    lienzo.color(colorPincel);
                }



                break;
            case R.id.botonMorado:
                colorPincel = "#ffaa66cc";

                if (presionoBorrar) {
                    lienzo.setTamañoPunto(pincel_devuelto);
                    lienzo.color(colorPincel);
                } else {

                    lienzo.color(colorPincel);
                }


                break;
            case R.id.botonVerde:
                colorPincel = "#ff99cc00";

                if (presionoBorrar) {
                    lienzo.setTamañoPunto(pincel_devuelto);
                    lienzo.color(colorPincel);
                } else {
                    lienzo.color(colorPincel);
                }


                break;
            case R.id.botonNegro:

                colorPincel = "#ff000000";
                if (presionoBorrar) {
                    lienzo.setTamañoPunto(pincel_devuelto);
                    lienzo.color(colorPincel);
                } else {
                    lienzo.color(colorPincel);
                }


                break;


            case R.id.retroceder:
                lienzo.undo();


                break;

            default:
                break;
        }




    }

    public void colorfondo(String borralo)
    {
        Hojaazul= Color.parseColor(borralo);
        lienzo.setBackgroundColor(Hojaazul);

    }


    public void Obtener_Imagen()
    {

        //Se invoca ala gallerria haciendo uso del intent
        Intent gallery = new Intent(Intent.ACTION_PICK);

        //en donde se encuentra  el archivo?
        File pictureDirectory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath=pictureDirectory.getPath();
        Log.d("Creation",pictureDirectoryPath);
        //Se obtiene la ruta

        Uri data =Uri.parse(pictureDirectoryPath);

        gallery.setDataAndType(data,"image/*");
        // se invoca para obtener lo solicitado
        startActivityForResult(gallery, IMAGE_GALLERY_REQUEST);

    }
    public void Guardar_Imagen()
    {

            lienzo.setDrawingCacheEnabled(true);
            String imagensalvada = MediaStore.Images.Media.insertImage(
                    getContentResolver(), lienzo.getDrawingCache(),
                    UUID.randomUUID().toString() + ".png", "drawing");

            if (imagensalvada != null) {
                Toast almacenar = Toast.makeText(getApplicationContext(), "Dibujo guardado con exito", Toast.LENGTH_SHORT);
                almacenar.show();
            } else {
                Toast almacenar = Toast.makeText(getApplicationContext(), "Error imagen no almacenada", Toast.LENGTH_SHORT);
                almacenar.show();
            }
            lienzo.destroyDrawingCache();
            cargarimagen=false;


        }






    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            //si el proceso que se ha hecho es exito entra la condicion


            if (reqCode==IMAGE_GALLERY_REQUEST) {
                //se obriene la direccion de la imagen que se pulso
                 Uri imageUri = data.getData();
                Log.d("Creation",imageUri.getPath());

                //flujo de entrada para leer la imagen y que sea colocada en canvas
                 InputStream inputStream;


                try {

                    //obtenemos el contenido a resolver y abrimos el flujode entrada y colocamos la direecion de la imagen


                      inputStream = getContentResolver().openInputStream(imageUri);

                      lienzo.recibirImagen_Camera(inputStream);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }


        }

    }

    public void obtenertamanio_Pincel()
    {



        barraValor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valorPincel =progress;
                tamañopincel=String.valueOf(valorPincel);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {





            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

               if (valorseekbarGoma)
               {
                   //para manetener el valor anterior de la barra de la goma y mantenerlo valorSeekbarAnterior se convierte en true;

                   valorbarragoma=valorPincel;
               }


               if(valorseekbarPincel)
               {
                   //para manetener el valor anterior del pincel  y mantenerlo valorSeekbarPincel  se convierte en true
                   valorbarrapincel=valorPincel;
               }

            }
        });
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nuevodoc) {
            AlertDialog.Builder nuevodialogo= new AlertDialog.Builder(this);
            nuevodialogo.setTitle("Nuevo Hoja");
            nuevodialogo.setMessage("¿Deseas crear una nueva hoja(perderas el dibujo anterior)?");
            nuevodialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    lienzo.nuevaHoja();

                }
            });

            nuevodialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {



                }
            });


            nuevodialogo.show();






            // Handle the camera action
        } else if (id == R.id.tamaño) {


            valorseekbarPincel=true;
            valorseekbarGoma=false;

            final Dialog brushDialog=new Dialog(this);
            brushDialog.setTitle("Tamaño Pincel");
            //ligamos el xml a dialogo
            brushDialog.setContentView(R.layout.trazo_tam);
            Button Aceptar=(Button) brushDialog.findViewById(R.id.aceptar);
            barraValor=(SeekBar)brushDialog.findViewById(R.id.seekBar);

            barraValor.setProgress(valorbarrapincel);
            obtenertamanio_Pincel();

            Aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lienzo.color(colorPincel);
                    lienzo.setTamañoPunto(Float.parseFloat(tamañopincel));
                    brushDialog.hide();

                }
            });

           // imagenpunto=(ImageView) brushDialog.findViewById(R.id.pequenio);
            //botonP.setOnClickListener(new View.OnClickListener() {
                //@Override
               // public void onClick(View v) {

                  //  if(presionoBorrar)
                   // {
                     //   lienzo.color(color_devuelto);
                       // lienzo.setTamañoPunto(pincel_pequeño);
                        //presionoBorrar=false;
                    //}
                    //else {

                      //  lienzo.setTamañoPunto(pincel_pequeño);

                    //}
                    //brushDialog.dismiss();
                //}
            //});


           // obtenertamanio_Pincel();


            brushDialog.show();




        } else if (id == R.id.borrar) {


            //la variable valorseekbarGoma se activa cuando se presiona la opcion borrar
            valorseekbarGoma=true;
            //En caso de que la opcion elegir pincel este presionada la variable valorseekbarPincel pasa a false
            valorseekbarPincel=false;

            final Dialog eraseDialog=new Dialog(this);
            eraseDialog.setTitle("Tamaño Goma");
            eraseDialog.setContentView(R.layout.trazo_tam);
            barraValor=(SeekBar)eraseDialog.findViewById(R.id.seekBar);

            barraValor.setProgress(valorbarragoma);






            Button Aceptar=(Button) eraseDialog.findViewById(R.id.aceptar);
            obtenertamanio_Pincel();

            Aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    colorPincel=lienzo.coloranterior();
                    lienzo.color(borrarcolor);
                    lienzo.setTamañoPunto(Float.parseFloat(tamañopincel));
                    eraseDialog.hide();




                }
            });
            eraseDialog.show();






        } else if (id == R.id.guardar) {

            Guardar_Imagen();



        } else if (id == R.id.Colorhoja) {


            final Dialog hojaDialogo=new Dialog(this);
            hojaDialogo.setTitle("Seleccionar Color de Hoja");
            hojaDialogo.setContentView(R.layout.coloreslienzo);

            ImageView Rojo=(ImageView) hojaDialogo.findViewById(R.id.hoja_roja);
            Rojo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrarcolor="#ffff4444";
                    Hojaroja= Color.parseColor("#ffff4444");
                    lienzo.setBackgroundColor(Hojaroja);
                    lienzo.nuevaHoja();
                    lienzo.borrrarT(borrarcolor);
                    lienzo.color_Undo(borrarcolor);

                    hojaDialogo.dismiss();
                }
            });



            ImageView Azul=(ImageView) hojaDialogo.findViewById(R.id.hoja_azul);
            Azul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrarcolor="#ff00ddff";
                    Hojaazul= Color.parseColor("#ff00ddff");
                    lienzo.setBackgroundColor(Hojaazul);
                    lienzo.nuevaHoja();
                    lienzo.borrrarT(borrarcolor);
                    lienzo.color_Undo(borrarcolor);


                    hojaDialogo.dismiss();
                }
            });

            ImageView Morado=(ImageView) hojaDialogo.findViewById(R.id.hoja_morada);
            Morado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrarcolor="#ffaa66cc";
                    Hojamorada= Color.parseColor("#ffaa66cc");
                    lienzo.setBackgroundColor(Hojamorada);
                    lienzo.nuevaHoja();
                    lienzo.borrrarT(borrarcolor);
                    lienzo.color_Undo(borrarcolor);


                    hojaDialogo.dismiss();
                }
            });


            ImageView Verde=(ImageView) hojaDialogo.findViewById(R.id.hoja_verde);
            Verde.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrarcolor="#ff99cc00";
                    Hojaverde= Color.parseColor("#ff99cc00");
                    lienzo.setBackgroundColor(Hojaverde);
                    lienzo.nuevaHoja();
                    lienzo.borrrarT(borrarcolor);
                    lienzo.color_Undo(borrarcolor);


                    hojaDialogo.dismiss();
                }
            });

            ImageView Negro=(ImageView) hojaDialogo.findViewById(R.id.hoja_negra);
            Negro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrarcolor="#ff000000";
                    Hojanegra= Color.parseColor("#ff000000");
                    lienzo.setBackgroundColor(Hojanegra);
                    lienzo.nuevaHoja();
                    lienzo.borrrarT(borrarcolor);
                    lienzo.color_Undo(borrarcolor);


                    hojaDialogo.dismiss();
                }
            });

            ImageView Blanca=(ImageView) hojaDialogo.findViewById(R.id.hoja_blanca);
            Blanca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrarcolor="#FFFFFF";
                    HojaBlanca= Color.parseColor("#FFFFFF");
                    lienzo.setBackgroundColor(HojaBlanca);
                    lienzo.nuevaHoja();
                    lienzo.borrrarT(borrarcolor);
                    lienzo.color_Undo(borrarcolor);


                    hojaDialogo.dismiss();
                }
            });

            hojaDialogo.show();




        } else if (id == R.id.nav_send) {

            if(lienzo.i>0) {
                AlertDialog.Builder salvarDibujo = new AlertDialog.Builder(this);
                salvarDibujo.setTitle("Guardar dibujo");
                salvarDibujo.setMessage("¿Desea Guardar  el Dibujo existente  en la Galeria?");
                salvarDibujo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Guardar_Imagen();

                        Obtener_Imagen();


                    }


                });


                salvarDibujo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Obtener_Imagen();


                    }
                });
                salvarDibujo.show();

            }

            else {
                Obtener_Imagen();
            }



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
