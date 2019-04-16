package com.anjankaur.projects.eec.hotelstructure;

import java.util.ArrayList;
import java.util.List;

import com.anjankaur.projects.eec.equipments.AirConditioner;
import com.anjankaur.projects.eec.equipments.EquipmentTypes;
import com.anjankaur.projects.eec.equipments.State;

public class Floor {
	
	private int floorNumber;
	private List<MainCorridor> mainCorridors;
	private List<SubCorridor> subCorridors;
	
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
		mainCorridors = new ArrayList<MainCorridor>();
		subCorridors = new ArrayList<SubCorridor>();
	}
	

	public int getFloorNumber() {
		return this.floorNumber;
	}
	
	public Corridor getCorridor(int corridorNumber, CorridorType cType) throws IllegalArgumentException{
		if(corridorNumber <= 0)
			throw new IllegalArgumentException(); 
		if(cType == CorridorType.MAIN_CORRIDOR && corridorNumber <= mainCorridors.size()) 
			return mainCorridors.get(corridorNumber - 1 );
		else
			if (cType == CorridorType.SUB_CORRIDOR && corridorNumber <= subCorridors.size())
				return subCorridors.get(corridorNumber - 1);
		
		throw new IllegalArgumentException(); 
		
	}
	
	
	public void addMainCorridor(MainCorridor mc) {
		
		mainCorridors.add(mc);
	}
	
	public void addSubCorridor(SubCorridor sc) {
		subCorridors.add(sc);
		
	}
	
	
	public List<SubCorridor> getSubcorridors() {
		return this.subCorridors;
	}

	public List<MainCorridor> getMainCorridors() {

		return this.mainCorridors;
	}
	
	//TODO decouple the power consumption formula: 15 and 10
	public int getMaxAllowedPowerConsumptionOfFloor() {
		
		return (15 * mainCorridors.size()) + (10 * subCorridors.size());
	}
	
	private int getCurrentPowerConsumptionOfFloor() {
		
		int totalConsumption = 0;
		
		for(MainCorridor m: mainCorridors) {
			totalConsumption += m.getCurrentPowerConsumptionOfCorridor();
		}
		for(SubCorridor s: subCorridors) {
			totalConsumption += s.getCurrentPowerConsumptionOfCorridor();
		}
		
		return totalConsumption;
	}

	/**
	 * 
	 * @return boolean value indicating if any change was made to the equipment state
	 */
	public boolean adjustPowerConsumption(){
		
		int currentTotalConsumption = getCurrentPowerConsumptionOfFloor();
		int maxAllowedConsumption = getMaxAllowedPowerConsumptionOfFloor();
		
		boolean powerAdjusted = false;
		
		if(currentTotalConsumption > maxAllowedConsumption) {
			// Switch of the ACs of sub corridors until this is leveled
			
			for(SubCorridor s : subCorridors) {
				s.getEquipment(EquipmentTypes.AIR_CONDITIONER).setState(State.OFF);
				currentTotalConsumption = getCurrentPowerConsumptionOfFloor();
				
				if(currentTotalConsumption <=  maxAllowedConsumption) {
					powerAdjusted = true;
					break;
				}
			}
			
		}
		else if(currentTotalConsumption < maxAllowedConsumption) {
			for(SubCorridor s : subCorridors) {
				AirConditioner ac = (AirConditioner) s.getEquipment(EquipmentTypes.AIR_CONDITIONER);
				State state = ac.getState();
				
				if(state == State.ON || (currentTotalConsumption + ac.getPowerConsumption()) >  maxAllowedConsumption )
					continue;
				
				ac.setState(State.ON);
				currentTotalConsumption = getCurrentPowerConsumptionOfFloor();
				if(currentTotalConsumption ==  maxAllowedConsumption){
					powerAdjusted = true;
					break;
				}
				
			}
			
		}
		return powerAdjusted;
	}
	
	
	public void printCurrentPowerConsumptionInfoOfFloor() {
		System.out.println("\n");
		System.out.print("Floor "+ this.floorNumber);
		
		if(mainCorridors.size() == 0 && subCorridors.size() == 0) {
			//TODO throw exception
			System.out.println("No Corridors Info available \n");
			return;
		}
			
		for(MainCorridor m: mainCorridors) {
			m.printCurrentPowerConsumptionInfoOfCorridor();
		}
		for(SubCorridor s: subCorridors) {
			s.printCurrentPowerConsumptionInfoOfCorridor();
		}
	}


	
}
