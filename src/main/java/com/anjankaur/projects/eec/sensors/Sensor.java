/**
 * 
 */
package com.anjankaur.projects.eec.sensors;

import java.util.List;

import com.anjankaur.projects.eec.events.Event;
import com.anjankaur.projects.eec.listeners.IListener;



/**
 * This class will be base class for   
 * different event handling sensors 
 *
 */
public abstract class Sensor {
	

	protected List<IListener> listeners;
	protected Event sensorEvent;
	protected String sensorID;
	
	
	protected abstract void notifyListener(Event event); 
	public abstract void attachListener(IListener listener);
	public abstract void detachListener(IListener listener);
	protected abstract String generateSensorID();

	
	public void setSensorID(String id) {
		this.sensorID = id;
	}

	public String getSensorID() {
		return this.sensorID;
	}


}
