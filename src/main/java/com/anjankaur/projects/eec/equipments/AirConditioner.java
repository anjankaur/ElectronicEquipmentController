/**
 * 
 */
package com.anjankaur.projects.eec.equipments;

/**
 * @author ankaur
 *
 */
public class AirConditioner extends Equipment{
	
	public AirConditioner(State state, int powerConsumption) {
		super(state,powerConsumption);
		equipmentType = EquipmentTypes.AIR_CONDITIONER;
	}
	
	
}
