package com.anjankaur.projects.eec.equipments;

import com.anjankaur.projects.eec.events.Event;
import com.anjankaur.projects.eec.listeners.IMotionListner;
import com.anjankaur.projects.eec.events.MotionEvent;
import com.anjankaur.projects.eec.hotelstructure.Floor;
import com.anjankaur.projects.eec.sensors.MotionSensor;


public class Light extends Equipment implements IMotionListner{
	
	
	public Light(State state, int powerConsumption) {
		super(state,powerConsumption);
		equipmentType = EquipmentTypes.LIGHT;
	}


	@Override
	public void onEventDetected(Event event) {
		
		boolean motion = ((MotionEvent) event).getMotionDetected();
		this.setState( motion ? State.ON : State.OFF);
		Floor floor = ((MotionSensor)(event.getSource())).getSensorFloor();
		floor.adjustPowerConsumption();
		
	}

}
