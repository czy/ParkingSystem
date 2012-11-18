package cn.beihangsoft.parkingsystem.controller;

import java.util.HashMap;
import java.util.Map;

import cn.beihangsoft.parkingsystem.model.Car;
import cn.beihangsoft.parkingsystem.model.ParkingArea;


public final class ParkingController {
	Map<String, Integer> parkingMap;
	ParkingArea parkingArea;
	

	public ParkingController(ParkingArea parkingArea) {
		parkingArea=new ParkingArea();
		parkingMap = new HashMap<String, Integer>();
	}

	public int park(Car car) {
		if (parkingMap.size() == parkingArea.getTotalSlots()) {
			return -1;
		}
		int parkNum = parkingMap.size() + 1;
		parkingMap.put(car.getPlateNumber(), parkNum);
		return parkNum;
	}

	public int getFreeNum() {
		return parkingArea.getTotalSlots()- parkingMap.size();
	}

	public int fetch(Car car) {
		String carNum=car.getPlateNumber();
		if (parkingMap.containsKey(carNum)) {
			int parkNum = parkingMap.get(carNum);
			parkingMap.remove(carNum);
			return parkNum;
		}
		return -1;
	}
}
