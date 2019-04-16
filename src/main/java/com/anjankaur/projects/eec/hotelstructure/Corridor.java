package com.anjankaur.projects.eec.hotelstructure;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.anjankaur.projects.eec.equipments.Equipment;
import com.anjankaur.projects.eec.equipments.EquipmentTypes;
import com.anjankaur.projects.eec.equipments.State;

public abstract class Corridor {
	
	private int corridorNumber;
	
    protected List<Equipment> equipments;
    protected CorridorType corridorType;
    
    public Corridor(int num, CorridorType corridorType) {
    	this.corridorNumber = num;
    	this.corridorType = corridorType;
    	this.equipments = new ArrayList<Equipment>();
    }
    public int getCorridorNumber() {
		
		return corridorNumber;
	}
    
    public CorridorType getCorridorType() {
    	return this.corridorType;
    }
    
    public void setCorridorNumber(int num) {
    	this.corridorNumber = num;
    }
    
    public void addEquipment(Equipment equip) {
		this.equipments.add(equip);
	}
	
	public void removeEquipment(EquipmentTypes etype) {
		this.equipments.removeIf(equip -> equip.getEquipmentType().equals(etype));
	}
    
	/**
	 * Assumption: Every corridor has just 1 equipment of a type
	 * If this assumption is to be ignored, we can think of keeping an index and modify accordingly
	 * @param etype
	 * @return Equipment
	 */
	public Equipment getEquipment(EquipmentTypes etype) throws NoSuchElementException {
		
		for(Equipment equip: this.equipments) {
			if(equip.getEquipmentType().equals(etype))
				return equip;
			
		}
		// Equipment of specific type not found
		throw new NoSuchElementException();
		
	}
	
	public int getCurrentPowerConsumptionOfCorridor() {
		int consumption = 0;
		
		for(Equipment equip: equipments) {
			if(equip.getState().equals(State.ON))
				consumption += equip.getPowerConsumption();
		}
		return consumption;
	}
	
    
    public abstract void printCurrentPowerConsumptionInfoOfCorridor();

}
