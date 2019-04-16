package com.anjankaur.projects.eec.equipments;

/**
 * Base class for any type of Electrical equipment
 */
public abstract class Equipment {
	
	protected EquipmentTypes equipmentType;
	private State state;
	private int powerConsumption;
	
	Equipment(State state, int powerConsumption){
		this.state = state;
		this.setPowerConsumption(powerConsumption);
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return this.state;
	}

	public int getPowerConsumption() {
		return powerConsumption;
	}

	public void setPowerConsumption(int powerConsumption) {
		this.powerConsumption = powerConsumption;
	}
	
	public EquipmentTypes getEquipmentType() {
		return this.equipmentType;
	}
	
	
}
