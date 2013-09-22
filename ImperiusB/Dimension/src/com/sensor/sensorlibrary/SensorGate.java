package com.sensor.sensorlibrary;

import java.util.Observable;

import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

/*Class: SensorGate
 *Function: To act as a gateway between Main application and sensor data
 *Author: Hrishik Mishra
 */

public class SensorGate extends Observable implements SensorEventListener, org.openintents.sensorsimulator.hardware.SensorEventListener{
	
	private Sensor sensor_m;
	private AccelerometerDataPacket dataPacket_m;
	
	private SensorManager sensorManager_m;
	private SensorManagerSimulator sensorManagerSimulator_m;
	
	//Event Handlers for the Sensor Event generated by hardware
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if ( event.sensor.getType() == Sensor.TYPE_ACCELEROMETER ){
			try{
				dataPacket_m = new AccelerometerDataPacket(3);
				dataPacket_m.setAccelerometerVector(event.values);
				setChanged();
				if ( hasChanged() )
					notifyObservers(dataPacket_m);
				
			}
			catch (Exception ex){
				Log.e(Constants.ON_SENSOR_CHANGED_TAG, ex.getMessage());
			}
			
		}
		
	}
	//Event Handlers for the Sensor Event generated by sensor simulator
	@Override
	public void onAccuracyChanged(
			org.openintents.sensorsimulator.hardware.Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
		
	}
	@Override
	public void onSensorChanged(
			org.openintents.sensorsimulator.hardware.SensorEvent event) {
		//if ( event.sensor. == Sensor.TYPE_ACCELEROMETER ){
			try{
				dataPacket_m = new AccelerometerDataPacket(3);
				dataPacket_m.setAccelerometerVector(event.values);
				setChanged();
				if ( hasChanged() )
					notifyObservers(dataPacket_m);
				
			}
			catch (Exception ex){
				Log.e(Constants.ON_SENSOR_CHANGED_TAG, ex.getMessage());
			}
			
		//}
		
	}
	
	public void registerListener(){
		if ( sensorManager_m == null)//Simulator is active
		{
		
			sensorManagerSimulator_m.registerListener(this, sensorManagerSimulator_m.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_FASTEST);
		}
		else//Hardware is active
		{
			
		}
	}
	
	
	//Constructor
	public SensorGate(SensorManager sensorManager){
		this.sensorManager_m = sensorManager;
	}
	public SensorGate(SensorManagerSimulator sensorManagerSimulator){
		sensorManagerSimulator_m = sensorManagerSimulator;
		 new RetreiveFeedTask().execute(sensorManagerSimulator_m);
	}
	
	
	
	
	
}

class RetreiveFeedTask extends AsyncTask<SensorManagerSimulator, Void, Integer> {

    private Exception exception;

    protected void onPostExecute() {
        // TODO: check this.exception 
        // TODO: do something with the feed
    }

	@Override
	protected Integer doInBackground(SensorManagerSimulator... params) {
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

			StrictMode.setThreadPolicy(policy); 
			SensorManagerSimulator sensorManager = (SensorManagerSimulator)params[0];
        	sensorManager.connectSimulator();
        	if ( sensorManager.isConnectedSimulator())
        		return Integer.valueOf(0);
        	else
        		return Integer.valueOf(-1);
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
	}

}