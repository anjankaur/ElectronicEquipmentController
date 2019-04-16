package com.anjankaur.projects.ElectronicEquipmentController;

import com.anjankaur.projects.eec.EEController;
import com.anjankaur.projects.eec.hotelstructure.Corridor;
import com.anjankaur.projects.eec.hotelstructure.CorridorType;
import com.anjankaur.projects.eec.hotelstructure.Floor;
import com.anjankaur.projects.eec.hotelstructure.Hotel;
import com.anjankaur.projects.eec.hotelstructure.MainCorridor;
import com.anjankaur.projects.eec.hotelstructure.SubCorridor;
import com.anjankaur.projects.eec.sensors.MotionSensor;

import static org.junit.Assert.*;

import org.junit.*;

public class MotionSensorTest {

	Hotel hotel;
	public void initialize() {
		int numFloors = 3;
    	int numMainCorridorsPerFloor = 1;
    	int numSubCorridorsPerFloor = 2;
    	
    	hotel = Hotel.constructHotel(numFloors,numMainCorridorsPerFloor,numSubCorridorsPerFloor);
        EEController controller = new EEController(hotel);
        controller.initialize();
        
	}
	
	@Test
	public void testUpdateState() {
		initialize();
		Floor f = hotel.getFloorByNumber(1);
		Corridor c = f.getCorridor(1, CorridorType.SUB_CORRIDOR);
		MotionSensor m = new MotionSensor(f,c);
		
		assertFalse(m.getState());
		
		m.updateState(true);
		assertTrue(m.getState());
		
		m.updateState(false);
		assertFalse(m.getState());
		
	}
	
	
	@Test
	public void testSensorID() {
		Floor f = new Floor(1);
		SubCorridor sc = new SubCorridor(1);
		MainCorridor mc = new MainCorridor(2);
		
		MotionSensor sensor1 = new MotionSensor(f,sc);
		assertEquals(sensor1.getSensorID(), "MS_F-1_SC-1");
		
		MotionSensor sensor2 = new MotionSensor(f,mc);
		assertEquals(sensor2.getSensorID(), "MS_F-1_MC-2");
		
		sensor2.setSensorID("MS_F-1_MC-2_a");
		assertEquals(sensor2.getSensorID(), "MS_F-1_MC-2_a");
		
	}
	
}
