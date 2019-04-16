package com.anjankaur.projects.eec.hotelstructure;


import java.util.EnumMap;
import java.util.Map;

import com.anjankaur.projects.eec.equipments.Equipment;
import com.anjankaur.projects.eec.equipments.EquipmentTypes;


public class MainCorridor extends Corridor {
	
	private static Map<EquipmentTypes, Integer> powerConsumptionEquipmentMap = new EnumMap<EquipmentTypes, Integer>(EquipmentTypes.class);
	
	
	public MainCorridor(int corridorNumber) {
		
		super(corridorNumber, CorridorType.MAIN_CORRIDOR);
		
	}
	
	
	@Override
	public void printCurrentPowerConsumptionInfoOfCorridor() {
		if(this.equipments.size() == 0)
			return;
		System.out.println("");
		System.out.print("Main Corridor: "+ this.getCorridorNumber() + " ");
		
		for(Equipment equip: this.equipments) {
			System.out.print(equip.getEquipmentType()+": "+ equip.getState() + " ");		
		}
		
	}
	
	public static void setPowerConsumptionForEquipmentType(EquipmentTypes etype, int value) {
		
		powerConsumptionEquipmentMap.put(etype, new Integer(value));
	}
	
	public static int getPowerConsumptionForEquipmentType(EquipmentTypes etype){
		
		return (powerConsumptionEquipmentMap.get(etype)).intValue();
	}
	
	
}
