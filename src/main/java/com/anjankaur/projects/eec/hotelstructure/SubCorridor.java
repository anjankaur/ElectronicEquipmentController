package com.anjankaur.projects.eec.hotelstructure;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.anjankaur.projects.eec.equipments.Equipment;
import com.anjankaur.projects.eec.equipments.EquipmentTypes;
import com.anjankaur.projects.eec.sensors.MotionSensor;
import com.anjankaur.projects.eec.sensors.Sensor;

public class SubCorridor extends Corridor {

	private static Map<EquipmentTypes, Integer> powerConsumptionEquipmentMap = new EnumMap<EquipmentTypes, Integer>(
			EquipmentTypes.class);
	private List<Sensor> sensors;

	public SubCorridor(int corridorNumber) {
		super(corridorNumber, CorridorType.SUB_CORRIDOR);
		this.sensors = new ArrayList<Sensor>();

	}

	public void addSensor(Sensor s) {

		sensors.add(s);

	}

	// TODO think about throwing exception if not found
	public void removeSensor(String sensorID) {

		this.sensors.removeIf(sensor -> sensor.getSensorID().equals(sensorID));
	}

	public MotionSensor getSensor(String sensorID) throws NoSuchElementException {

		for (Sensor sensor : sensors) {

			if (sensor.getSensorID().equals(sensorID)) {

				return (MotionSensor) sensor;
			}

		}

		// Sensor not found
		throw new NoSuchElementException();

	}

	@Override
	public void printCurrentPowerConsumptionInfoOfCorridor() {

		if(this.equipments.size() == 0)
			return;
		System.out.println("");
		System.out.print("Sub Corridor: " + this.getCorridorNumber() + " ");

		for (Equipment equip : this.equipments) {
			System.out.print(equip.getEquipmentType() + ": " + equip.getState() + " ");
		}

	}

	public static void setPowerConsumptionForEquipmentType(EquipmentTypes etype, int value) {

		powerConsumptionEquipmentMap.put(etype, new Integer(value));
	}

	public static int getPowerConsumptionForEquipmentType(EquipmentTypes etype) {

		return (powerConsumptionEquipmentMap.get(etype)).intValue();
	}

}
