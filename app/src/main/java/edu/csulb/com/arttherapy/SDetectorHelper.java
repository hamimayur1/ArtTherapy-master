package edu.csulb.com.arttherapy;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

class SDetectorHelper implements SensorEventListener {

    private OnShakeListener mShakeListner;
    private int msCount;
    private long msTS;

    private static final float SHAKE_THRESHOLD_BENCHMARK_GRAVITY = 2.7F;
    private static final int SHAKE_COUNT_RES_TIME = 3000;
    private static final int NEAR_SHAKE_DETECTOR = 500;


    public void setOnShakeListener(OnShakeListener listener) {

        this.mShakeListner = listener;
    }

    public interface OnShakeListener {
        public void onShake(int count);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (mShakeListner != null) {
            float xLocation = event.values[0];
            float yLocation = event.values[1];
            float zLocation = event.values[2];

            float gX = xLocation / SensorManager.GRAVITY_EARTH;
            float gY = yLocation / SensorManager.GRAVITY_EARTH;
            float gZ = zLocation / SensorManager.GRAVITY_EARTH;

            // close to 1 when no movement
            float gFrc = (float)Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if ( SHAKE_THRESHOLD_BENCHMARK_GRAVITY < gFrc)
            {
                final long timeNow = System.currentTimeMillis();

                // reset after 3s of inactivity
                if (msTS + SHAKE_COUNT_RES_TIME < timeNow) {
                    msCount = 0;
                }
                // ignore false shakes or very  nearby shakes
                if (msTS + NEAR_SHAKE_DETECTOR > timeNow) {
                    return;
                }

                msCount++;
                msTS = timeNow;
                mShakeListner.onShake(msCount);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No task
    }

}