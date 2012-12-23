package cn.beihangsoft.parkingsystem.strategy;

import java.util.List;

import cn.beihangsoft.parkingsystem.NoSpaceException;
import cn.beihangsoft.parkingsystem.model.ParkingPlace;

/**
 * 策略：第一个有空车位的停车场
 */
public class ChooseParkingPlaceByFirstEmptySpaceStrategy implements ChooseParkingPlaceStrategy {
	@Override
	public ParkingPlace getAvailableParkingPlace(List<ParkingPlace> parkingPlaceList) {
		if (parkingPlaceList == null || parkingPlaceList.size() == 0) {
			throw new NoSpaceException("没有停车场！");
		}

		for (ParkingPlace parkingPlace : parkingPlaceList) {
			if (parkingPlace.getEmptySpaces() > 0) {
				return parkingPlace;
			}
		}

		throw new NoSpaceException("所有停车场都没有空车位了！");
	}
}
