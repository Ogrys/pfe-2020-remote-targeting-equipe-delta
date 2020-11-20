package fr.eseo.arnou.wifi.Sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import fr.eseo.arnou.wifi.R;

public class RotationSensor extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerator;

    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerator = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

        if(mAccelerator != null){
            mSensorManager.registerListener(this,mAccelerator,SensorManager.SENSOR_DELAY_GAME);
        }

        startPlot();

    }

    private void startPlot(){
        if(thread != null){
            thread.interrupt();
        }

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void addEntry(SensorEvent event){
        Log.d("test_x",String.valueOf(event.values[0]));
        Log.d("test_y",String.valueOf(event.values[1]));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        addEntry(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onPause(){
        super.onPause();

        if(thread != null){
            thread.interrupt();
        }
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mAccelerator,SensorManager.SENSOR_DELAY_GAME);
    }
}
