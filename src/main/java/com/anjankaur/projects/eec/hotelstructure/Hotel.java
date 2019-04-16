package com.anjankaur.projects.eec.hotelstructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.xml.bind.ValidationException;

import com.anjankaur.projects.eec.EEController;

/**
 * 
 * Hotel class
 *
 */
public class Hotel {

	private List<Floor> floors;
	private EEController controller;

	/**
	 * Making constructor private, Use constructHotel() API to make instance of this
	 * class
	 */
	private Hotel() {
		this.floors = new ArrayList<Floor>();
	}

	public void addFloor(Floor f) {
		floors.add(f);
	}

	public Floor getFloorByNumber(int flr) {
		return floors.get(flr - 1);
	}

	public List<Floor> getFloors() {
		return this.floors;
	}

	/**
	 * Print current status of electrical equipment
	 */
	public void printCurrentPowerConsumptionInfo() throws ValidationException {
		
		//TODO a better way to do this
		if(! this.controller.isInitialised())
			throw new ValidationException("EEController not initialised!!");
		for (Floor f : floors)
			f.printCurrentPowerConsumptionInfoOfFloor();

	}

	
	//TODO, maybe create different class to hold all the parameters, and pass it as an object
	/**
	 * Method to create hotel instance
	 * 
	 * @param numFloors
	 * @param numMainCorridorsPerFloor
	 * @param numSubCorridorsPerFloor
	 * @return Hotel instance
	 */
	public static Hotel constructHotel(int numFloors, int numMainCorridorsPerFloor, int numSubCorridorsPerFloor) {
		Hotel hotel = new Hotel();

		// Adding Floors
		for (int i = 1; i <= numFloors; i++) {
			Floor f = new Floor(i);
			
			//Adding Corridors
			IntStream.rangeClosed(1, numMainCorridorsPerFloor).forEach(j -> {
				MainCorridor mc = new MainCorridor(j); 				
				f.addMainCorridor(mc);
				});
			
			
			IntStream.rangeClosed(1, numSubCorridorsPerFloor).forEach(j -> {
				SubCorridor sc = new SubCorridor(j); 
				f.addSubCorridor(sc);
				});

			hotel.addFloor(f);
		}

		return hotel;
	}

	public void setController(EEController eeController) {
		
		this.controller = eeController;
	}
}
