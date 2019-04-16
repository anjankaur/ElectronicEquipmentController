package com.anjankaur.projects.eec;

import java.util.List;

import javax.xml.bind.ValidationException;

import com.anjankaur.projects.eec.equipments.AirConditioner;
import com.anjankaur.projects.eec.equipments.EquipmentTypes;
import com.anjankaur.projects.eec.equipments.Light;
import com.anjankaur.projects.eec.equipments.State;
import com.anjankaur.projects.eec.hotelstructure.Floor;
import com.anjankaur.projects.eec.hotelstructure.Hotel;
import com.anjankaur.projects.eec.hotelstructure.MainCorridor;
import com.anjankaur.projects.eec.hotelstructure.SubCorridor;
import com.anjankaur.projects.eec.sensors.MotionSensor;
import com.anjankaur.projects.eec.sensors.Sensor;

/**
 * Main controller class
 *
 */
public class EEController 
{
	private Hotel hotel;
	boolean initialised;
	
	public EEController(Hotel hotel) {
		
		this.hotel = hotel;
		this.initialised = false;
	}
	
	/**
	 * This API adds and sets the initial state of the equipments in the hotel
	 * So this API must be called prior to any input is sent to sensors
	 */
	public void initialize() {
		
		setDefaultPowerConsumption();
		addEquipmentsToCorridors();
		attachSensorsToCorridors();
		this.initialised = true;
		
		//TODO figure out a better way for hotel and controller interaction
		hotel.setController(this);
	}

	private void setDefaultPowerConsumption() {
		
		/*
		 * Setting default values for power consumption 
		 * in Main corridor and sub corridor
		 */
		MainCorridor.setPowerConsumptionForEquipmentType(EquipmentTypes.LIGHT, 5);
		MainCorridor.setPowerConsumptionForEquipmentType(EquipmentTypes.AIR_CONDITIONER, 10);
		SubCorridor.setPowerConsumptionForEquipmentType(EquipmentTypes.LIGHT, 5);
		SubCorridor.setPowerConsumptionForEquipmentType(EquipmentTypes.AIR_CONDITIONER, 10);
	
	}

	/**
	 * This API will add sensors to corridor
	 * Currently motion sensors to subCorridors
	 * All adds listeners to the sensors
	 *	
	 */
	private void attachSensorsToCorridors() {
		
		List<Floor> floors = hotel.getFloors();
		
		for(Floor f: floors){
			List<SubCorridor> subs = f.getSubcorridors();
			subs.forEach(subCorridor -> {
				
				Sensor sensor = new MotionSensor(f, subCorridor);
				Light light = (Light) subCorridor.getEquipment(EquipmentTypes.LIGHT);
				
				sensor.attachListener(light);
				
				subCorridor.addSensor(sensor);
				
			});
		}
	}

	/**
	 * Add Equipments to corridors, with initial state
	 */
	private void addEquipmentsToCorridors() {
		List<Floor> floors = hotel.getFloors();
		for(Floor f: floors){
			List<SubCorridor> subs = f.getSubcorridors();
			subs.forEach(subCorridor -> {
				Light l = new Light(State.OFF,
						SubCorridor.getPowerConsumptionForEquipmentType(EquipmentTypes.LIGHT));
				subCorridor.addEquipment(l);
				
				AirConditioner ac = new AirConditioner(State.ON,
						SubCorridor.getPowerConsumptionForEquipmentType(EquipmentTypes.AIR_CONDITIONER));
				subCorridor.addEquipment(ac);
				
			});
			
			List<MainCorridor> mainCorridors = f.getMainCorridors();
			mainCorridors.forEach(mainCorridor -> {
				Light l = new Light(State.ON,
						MainCorridor.getPowerConsumptionForEquipmentType(EquipmentTypes.LIGHT));
				mainCorridor.addEquipment(l);
				
				AirConditioner ac = new AirConditioner(State.ON,
						MainCorridor.getPowerConsumptionForEquipmentType(EquipmentTypes.AIR_CONDITIONER));
				mainCorridor.addEquipment(ac);
				
			});
		}
		
	}
	
	public void printCurrentPowerConsumptionInfoOfHotel() throws ValidationException {
		
		System.out.print("\n\nCurrent State:");
		hotel.printCurrentPowerConsumptionInfo();
		
	}

	public boolean isInitialised() {
		
		return initialised;
	}
    
   
}
