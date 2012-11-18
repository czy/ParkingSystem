package cn.beihangsoft.parkingsystem.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import cn.beihangsoft.parkingsystem.model.Car;
import cn.beihangsoft.parkingsystem.model.ParkingArea;

public final class ParkingController {
	Map<String, Integer> parkingMap;
	ParkingArea parkingArea;
	HashMap<Integer,Boolean> map;

	public ParkingController(int totalSlots) {
		this.parkingArea = new ParkingArea(totalSlots);
		parkingMap = new HashMap<String, Integer>();
		map=new HashMap<Integer,Boolean>();
		for(int i=1;i<parkingArea.getTotalSlots()+1;i++){
			map.put(i, true);
		}
	}

	public int park(Car car) {
		if (parkingMap.size() == parkingArea.getTotalSlots()) {
			return 0;
		}
		Random rad=new Random();
		int parkNum=0;
		while(map.size()>0){
			parkNum=rad.nextInt(parkingArea.getTotalSlots())+1;
			if(map.get(parkNum)!=null){
				map.remove(parkNum);
				break;
			}
		}
		parkingMap.put(car.getPlateNumber(), parkNum);
		return parkNum;
	}

	public int getFreeNum() {
		return parkingArea.getTotalSlots() - parkingMap.size();
	}

	public int fetch(Car car) {
		String carNum = car.getPlateNumber();
		if (parkingMap.containsKey(carNum)) {
			int parkNum = parkingMap.get(carNum);
			parkingMap.remove(carNum);
			map.put(parkNum, true);
			return parkNum;
		}
		return 0;
	}
}
