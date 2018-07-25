package com.example.eduardopalacios.paint2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eduardopalacios on 30/05/17.
 */

public class Lienzo extends View {
    private Path drawPath;
    private Path drawPathUndo;
    private Paint drawPaint;
    private Paint canvasPaint;
    private int paintcolor;
    private int tamañoPincel=10;
    private Canvas drawCanvas;
    private Canvas drawCanvas2;



    Bitmap[] canvasBitmap = new Bitmap[3];
    ArrayList <Path> AlmacenaTrazo=new ArrayList<Path>();



    float[] trazox;
    float[] trazoy;
    float [] xDown;
    float [] yDown;

    float [] xUp;
    float[] yUp;

    int indiceDown=0;
    int indiceUp=0;
    boolean down;
    boolean up;
    boolean add;
    int k;
    int l;



    int i=0;

    String color1="#ff000000";
    String colorundo="#FFFFFF";
    float tamañoPunto=10;
    boolean borrado;
    float x=0;
    float y=0;







    public Lienzo(Context context, AttributeSet attrs) {
        super(context,attrs);

        //pathMap=new HashMap<Integer,Path>();

        setupDrawing();
    }


    public void color(String color)
    {
        invalidate();
        color1=color;
        paintcolor= Color.parseColor(color);
        drawPaint.setColor(paintcolor);
    }

    public void tamañoPincel(int tamañoP)
    {
        invalidate();
        tamañoPincel=tamañoP;
        drawPaint.setStrokeWidth(tamañoPincel);
    }



    private void setupDrawing()
    {
        drawPath=new Path();
        drawPathUndo=new Path();
        drawPaint=new Paint();

        drawPaint.setAntiAlias(true);

        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint=new Paint(Paint.DITHER_FLAG);

    }

    protected  void onSizeChanged(int w,int h, int oldw,int oldh)
    {
        super.onSizeChanged(w,h,oldw,oldh);

        canvasBitmap[0]=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        canvasBitmap[1]=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        //int height=canvasBitmap[1].getHeight();
        //int width=canvasBitmap[1].getWidth();



        //trazox=new float[width];
        //trazoy=new float[height];

        //xDown=new float[width];
        //yDown=new float[height];

        //xUp=new float[width];
        //yUp=new float[height];





        drawCanvas=new Canvas(canvasBitmap[0]);
        drawCanvas2=new Canvas(canvasBitmap[1]);








    }



    protected void onDraw(Canvas canvas)
    {



        canvas.drawBitmap(canvasBitmap[0],0,0,null);


        if (up)
        {
            i++;
            up=false;
        }
      // if(add)
        //{
          //  i++;
            //add=false;
        //}




        //if (down)
        //{
          //  i++;
            //indiceDown++;
            //down=false;
        //}

       // if (up)
        //{

          //  i++;
            //indiceUp++;
            //k=indiceUp-1;
            //up=false;
        //}



        canvas.drawBitmap(canvasBitmap[1],x,y,null);





    }

    public boolean onTouchEvent(MotionEvent event)
    {
        float touchX=event.getX();
        float touchY=event.getY();



        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

               //down=true;

                //x=event.getX();
                //y=event.getY();



               // trazox[i]=x;
                //trazoy[i]=y;

                //xDown[indiceDown]=x;
                //yDown[indiceDown]=y;
    





                drawCanvas2.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.botonverde),-55,-70,null);


                drawPath.moveTo(touchX,touchY);


                invalidate();




                break;

            case MotionEvent.ACTION_MOVE:
                //add=true;

                //trazox[i]=x;
                //trazoy[i]=y;

                x=event.getX();
                y=event.getY();







                drawPath.lineTo(touchX,touchY);

                drawCanvas.drawPath(drawPath,drawPaint);
                invalidate();




                break;

            case MotionEvent.ACTION_UP:

                up=true;
                //trazox[i]=x;
                //trazoy[i]=y;

                //xUp[indiceUp]=x;
                //yUp[indiceUp]=y;





                AlmacenaTrazo.add(i,drawPath);
                drawPath.reset();

                drawCanvas2.drawColor(0, PorterDuff.Mode.CLEAR);
                invalidate();


                break;

            default:
                return false;


        }




        return true;
    }

    public int tamañoPincel()
    {
        return tamañoPincel;
    }

    //poner tamaño del punto
    public void setTamañoPunto(float NuevoTamaño)
    {

        tamañoPunto=NuevoTamaño;

        drawPaint.setStrokeWidth(tamañoPunto);

    }

    public void borrrarT(String color)
    {
        invalidate();
        paintcolor= Color.parseColor(color);
        drawPaint.setColor(paintcolor);
    }

    public float tamañoPincelAnterior()
    {
        return tamañoPunto;
    }

    public String coloranterior()
    {
        return color1;
    }

    public void nuevaHoja() {

        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);

        invalidate();


    }


    //public void undo()

    //{
        // paintcolor= Color.parseColor("#ffaa66cc");
        //drawPaint.setColor(paintcolor);

        //Toast toast=Toast.makeText(getContext(),String.valueOf(pathMap.get(1)),Toast.LENGTH_SHORT);
        //toast.show();
       // Path path= pathMap.get(contador_trazos2);
       // drawCanvas.drawPath(pathMap.get(contador_trazos2-1), drawPaint);
       // pathMap.remove(contador_trazos2-1);


        //invalidate();

       // drawPathUndo.reset();

        //contador_trazos2--;

   // }
    public void borradoo(boolean estaborrado)
    {
        borrado=estaborrado;
        if(borrado)
        {
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        else
        {

            drawPaint.setXfermode(null);
        }
    }


    public void color_Undo(String color)
    {
        colorundo=color;
    }

   public void borrartrazo()
    {
        drawPaint.setStrokeWidth(15);
        paintcolor= Color.parseColor(colorundo);
        drawPaint.setColor(paintcolor);



        int j=0;

        if(k>=0)
        {



            do {
                j++;
            } while (xDown[k] != trazox[j]) ;

                    drawPath.moveTo(xDown[k],trazoy[j]);
                    drawPath.lineTo(trazox[j], trazoy[j]);
                    drawCanvas.drawPath(drawPath, drawPaint);


                    int m=j;
                    do
                    {
                        drawPath.lineTo(trazox[m], trazoy[m]);
                        drawCanvas.drawPath(drawPath, drawPaint);
                        trazox[m]=0;
                        trazoy[m]=0;
                        m++;



                    }while(yUp[k]!=trazoy[m]);

                    if(yUp[k]==trazoy[m])
                    {
                        drawPath.lineTo(trazox[m], trazoy[m]);
                        drawCanvas.drawPath(drawPath, drawPaint);
                    }


            k--;


            invalidate();



        drawPath.reset();
        drawPaint.setStrokeWidth(10);



      }
      else {
            indiceDown=0;
            indiceUp=0;
            i=0;
            drawPaint.setStrokeWidth(10);

            drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);

            invalidate();

        }


    }

    public void recibirImagen_Camera(InputStream inputStream)
    {

        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);

        Bitmap imagen=BitmapFactory.decodeStream(inputStream);


        int ancho_canvas=getRight();
        int alto_canvas=getBottom();



        int ancho_Imagen =imagen.getWidth();
        int alto_Imagen=imagen.getHeight();
        //float medioIm=(float)alto_Imagen/ancho_Imagen;

        //int alto,ancho;
        //if(medioCa<medioIm)
        //{
          //  ancho=ancho_canvas;
            //alto=(int)(medioIm*ancho);
        //}
        //else {
          //  alto=alto_canvas;
            //ancho=(int)(alto/medioIm);
        //}


        Bitmap SizeImage=Bitmap.createScaledBitmap(imagen,700,700,true);

        drawCanvas.drawBitmap(SizeImage,ancho_canvas/20,alto_canvas/8,null);





        invalidate();
    }

    public void undo()
    {
        paintcolor= Color.parseColor("#ffffff");
        drawPaint.setColor(paintcolor);
        drawPathUndo= AlmacenaTrazo.get(i-1);
        AlmacenaTrazo.remove(i-1);
        drawCanvas.drawPath(drawPathUndo,drawPaint);
        invalidate();


    }






}

