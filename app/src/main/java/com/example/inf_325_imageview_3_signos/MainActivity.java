package com.example.inf_325_imageview_3_signos;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int imagenes[] = {R.drawable.aries, R.drawable.tauro, R.drawable.geminis, R.drawable.cancer,
            R.drawable.leo, R.drawable.virgo, R.drawable.libra, R.drawable.escorpion, R.drawable.sagitario,
            R.drawable.capricornio, R.drawable.acuario, R.drawable.piscis};
    String nombres[] = {"Aries", "Tauro", "Geminis", "Cancer", "Leo", "Virgo", "Libra", "Escorpion", "Sagitario", "Capricornio", "Acuario", "Piscis"};
    int indice = 0;
    private ImageView imagen;
    private TextView nombre;

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen = (ImageView) findViewById(R.id.imageView);
        nombre = (TextView) findViewById(R.id.texto);

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(sensor==null){
            Toast.makeText(getApplicationContext(),"No cuenta con el sensor",Toast.LENGTH_LONG).show();
            //finish();
        }

        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                System.out.println("Valor giro "+x);
                if (x < 1 && x > -1) contador = 1;
                //Izquierda
                if(x<-5 && contador==1){
                    //aux++;
                    indice++;
                    if(indice>11) indice=0;
                    nombre.setText(nombres[indice]);
                    imagen.setImageResource(imagenes[indice]);
                    contador = 0;
                }else
                    //Derecha
                    if(x>5 && contador==1){
                    //aux--;
                    indice--;
                    if(indice<0) indice=11;
                    nombre.setText(nombres[indice]);
                    imagen.setImageResource(imagenes[indice]);
                    contador = 0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        start();
    }

    public void siguiente(View view) {
        indice++;
        if(indice>11) indice=0;
        nombre.setText(nombres[indice]);
        imagen.setImageResource(imagenes[indice]);
    }

    public void anterior(View view) {
        indice--;
        if(indice<0) indice=11;
        nombre.setText(nombres[indice]);
        imagen.setImageResource(imagenes[indice]);
    }

    public void ocultar(View view) {
    }


    private void start(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
}


