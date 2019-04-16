package com.anjankaur.projects.eec.sensors;

import java.util.ArrayList;

import com.anjankaur.projects.eec.events.Event;
import com.anjankaur.projects.eec.events.MotionEvent;
import com.anjankaur.projects.eec.hotelstructure.Corridor;
import com.anjankaur.projects.eec.hotelstructure.CorridorType;
import com.anjankaur.projects.eec.hotelstructure.Floor;
import com.anjankaur.projects.eec.listeners.IListener;

/**
 * 
 * It's kind of like event handler for motion event
 *
 */
public class MotionSensor extends Sensor {

	private boolean state;

	private Floor floor;
	private Corridor corridor;

	private void fireEvent() {
		sensorEvent = new MotionEvent(this);
		((MotionEvent) sensorEvent).setMotionDetected(this.state);

		// notify Listener of the event that state is changed
		this.notifyListener(sensorEvent);
	}

	/**
	 * generate default sensorID of form MS_F<num>_SC<num> for sub corridor and
	 * MS_F<num>_MC<num> for main corridor
	 */
	@Override
	protected String generateSensorID() {

		String s = "MS";
		s += "_F-" + this.floor.getFloorNumber();
		if (corridor.getCorridorType() == CorridorType.SUB_CORRIDOR)
			s = s + "_SC-" + corridor.getCorridorNumber();
		else if (corridor.getCorridorType() == CorridorType.MAIN_CORRIDOR)
			s = s + "_MC-" + corridor.getCorridorNumber();

		return s;
	}
	
	@Override
	protected void notifyListener(Event event) {
		
		for (IListener l : listeners) {
			l.onEventDetected(event);
		}

	}


	public MotionSensor(Floor floor, Corridor corridor) {

		this.listeners = new ArrayList<IListener>();
		this.floor = floor;
		this.corridor = corridor;
		this.setSensorID(generateSensorID());
		this.state = false;
	}

	// TODO change the parameter to enum
	public void updateState(boolean state) {

		// Don't do anything if state is not changed
		if (state == this.state)
			return;

		this.state = state;
		this.fireEvent();

	}

	
	
	@Override
	public void attachListener(IListener listener) {
		listeners.add(listener);

	}

	//TODO revisit, this is not right. 
	@Override
	public void detachListener(IListener listener) {
		listeners.remove(listener);

	}

	public Floor getSensorFloor() {
		return this.floor;
	}

	
	public boolean getState() {
		return this.state;
	}

}
