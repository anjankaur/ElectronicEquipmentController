package com.anjankaur.projects.ElectronicEquipmentController;

import com.anjankaur.projects.eec.EEController;
import com.anjankaur.projects.eec.equipments.EquipmentTypes;
import com.anjankaur.projects.eec.equipments.State;
import com.anjankaur.projects.eec.hotelstructure.CorridorType;
import com.anjankaur.projects.eec.hotelstructure.Floor;
import com.anjankaur.projects.eec.hotelstructure.Hotel;
import com.anjankaur.projects.eec.hotelstructure.MainCorridor;
import com.anjankaur.projects.eec.hotelstructure.SubCorridor;
import com.anjankaur.projects.eec.sensors.MotionSensor;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.xml.bind.ValidationException;

import org.junit.*;


/**
 * FullRunTest
 */
public class FullRunTest
    
{	
	public void performSensorOperation(Hotel hotel, SensorInput sInput, int sensorFloor, int sensorSubCor) {
		
		 SubCorridor s = (SubCorridor)(hotel.getFloorByNumber(sensorFloor).getCorridor(sensorSubCor, CorridorType.SUB_CORRIDOR));
	        String id = "MS_F-"+sensorFloor+"_SC-"+sensorSubCor;
	        
		 	MotionSensor s1 = s.getSensor(id);
	        if(sInput == SensorInput.MOTION)
	        	s1.updateState(true);
	        else if(sInput == SensorInput.NO_MOTION)
	        	s1.updateState(false);
	        
	}
    
	/*
	 * Just a sanity run
	 */
    @Test
    public void sanityRun() throws ValidationException  {
    	
    	int numFloors = 3;
    	int numMainCorridorsPerFloor = 2;
    	int numSubCorridorsPerFloor = 3;
    	
    	Hotel hotel = Hotel.constructHotel(numFloors,numMainCorridorsPerFloor,numSubCorridorsPerFloor);
        EEController controller = new EEController(hotel);
        controller.initialize();
        
        // Default state
        controller.printCurrentPowerConsumptionInfoOfHotel();
        
        performSensorOperation(hotel, SensorInput.MOTION, 2, 2);
        controller.printCurrentPowerConsumptionInfoOfHotel();
        
        performSensorOperation(hotel, SensorInput.MOTION, 2, 3);
        controller.printCurrentPowerConsumptionInfoOfHotel();
        
        performSensorOperation(hotel, SensorInput.MOTION, 2, 1);
        controller.printCurrentPowerConsumptionInfoOfHotel();
        
        performSensorOperation(hotel, SensorInput.NO_MOTION, 1, 1);
        controller.printCurrentPowerConsumptionInfoOfHotel();
        
        performSensorOperation(hotel, SensorInput.NO_MOTION, 2, 2);
        controller.printCurrentPowerConsumptionInfoOfHotel();
       
    }
    
    @Test
    public void testEnergySave() {
    	int numFloors = 2;
    	int numMainCorridorsPerFloor = 1;
    	int numSubCorridorsPerFloor = 2;
    	
    	Hotel hotel = Hotel.constructHotel(numFloors,numMainCorridorsPerFloor,numSubCorridorsPerFloor);
        EEController controller = new EEController(hotel);
        controller.initialize();
        
        
        List<Floor> hotelFloors = hotel.getFloors();
        
        for (Floor f : hotelFloors) {
        	List<SubCorridor> subC = f.getSubcorridors();
        	List<MainCorridor> mainC = f.getMainCorridors();
        	for(SubCorridor sc: subC) {
        		assertEquals(sc.getEquipment(EquipmentTypes.LIGHT).getState(),State.OFF);
        		assertEquals(sc.getEquipment(EquipmentTypes.AIR_CONDITIONER).getState(),State.ON);
        	}
        	for(MainCorridor mc: mainC) {
        		assertEquals(mc.getEquipment(EquipmentTypes.LIGHT).getState(),State.ON);
        		assertEquals(mc.getEquipment(EquipmentTypes.AIR_CONDITIONER).getState(),State.ON);
        	}
        }
        
        int fNum = 1, scNum = 2;
        
        performSensorOperation(hotel, SensorInput.MOTION, fNum, scNum);
        
        SubCorridor sub1 = (SubCorridor)(hotelFloors.get(fNum - 1).getCorridor(scNum, CorridorType.SUB_CORRIDOR));
        
        assertEquals(sub1.getEquipment(EquipmentTypes.LIGHT).getState(),State.ON);
        assertEquals(sub1.getEquipment(EquipmentTypes.AIR_CONDITIONER).getState(),State.ON);
        
        SubCorridor sub2 = (SubCorridor)(hotelFloors.get(fNum - 1).getCorridor(scNum - 1, CorridorType.SUB_CORRIDOR));
        assertEquals(sub2.getEquipment(EquipmentTypes.LIGHT).getState(),State.OFF);
        assertEquals(sub2.getEquipment(EquipmentTypes.AIR_CONDITIONER).getState(),State.OFF);
        
        
        performSensorOperation(hotel, SensorInput.NO_MOTION, fNum, scNum);
        
		assertEquals(sub2.getEquipment(EquipmentTypes.LIGHT).getState(), State.OFF);
		assertEquals(sub2.getEquipment(EquipmentTypes.AIR_CONDITIONER).getState(), State.ON);
		
		assertEquals(sub1.getEquipment(EquipmentTypes.LIGHT).getState(), State.OFF);
		assertEquals(sub1.getEquipment(EquipmentTypes.AIR_CONDITIONER).getState(), State.ON);
        
    	
    }
}
