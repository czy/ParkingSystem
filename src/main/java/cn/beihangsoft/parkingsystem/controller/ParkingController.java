package cn.beihangsoft.parkingsystem.controller;

import java.util.HashMap;
import java.util.Map;


public final class ParkingController {
	private final int MAX_NUM = 100;
	Map<String, Integer> parkingMap;

	public ParkingController() {
		parkingMap = new HashMap<String, Integer>();
	}

	public int park(String carNum) {
		if (parkingMap.size() == MAX_NUM) {
			return -1;
		}
		int parkNum = parkingMap.size() + 1;
		parkingMap.put(carNum, parkNum);
		return parkNum;
	}

	public int getFreeNum() {
		return MAX_NUM - parkingMap.size();
	}

	public int fetch(String carNum) {
		if (parkingMap.containsKey(carNum)) {
			int parkNum = parkingMap.get(carNum);
			parkingMap.remove(carNum);
			return parkNum;
		}
		return -1;
	}
}
