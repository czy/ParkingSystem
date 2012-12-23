package cn.beihangsoft.parkingsystem.strategy;

import java.util.List;

import cn.beihangsoft.parkingsystem.NoSpaceException;
import cn.beihangsoft.parkingsystem.model.ParkingPlace;

/**
 * 策略：空车位数量最多的停车场
 */
public class ChooseParkingPlaceByMaxEmptySpacesStrategy implements ChooseParkingPlaceStrategy {
	@Override
	public ParkingPlace getAvailableParkingPlace(List<ParkingPlace> parkingPlaceList) {
		if (parkingPlaceList == null || parkingPlaceList.size() == 0) {
			throw new NoSpaceException("没有停车场！");
		}

		ParkingPlace availableParkingPlace = parkingPlaceList.get(0);
		for (ParkingPlace parkingPlace : parkingPlaceList) {
			if (parkingPlace.getEmptySpaces() > availableParkingPlace.getEmptySpaces()) {
				availableParkingPlace = parkingPlace;
			}
		}

		if (availableParkingPlace.getEmptySpaces() == 0) {
			throw new NoSpaceException("所有停车场都没有空车位了！");
		}

		return availableParkingPlace;
	}
}
