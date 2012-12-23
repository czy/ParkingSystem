package cn.beihangsoft.parkingsystem.strategy;

import java.util.List;

import cn.beihangsoft.parkingsystem.NoSpaceException;
import cn.beihangsoft.parkingsystem.manager.ParkingBoy;

/**
 * 策略：拥有空车位数量最多的 ParkingBoy
 */
public class ChooseParkingBoyByMaxEmptySpacesStrategy implements ChooseParkingBoyStrategy {
	@Override
	public ParkingBoy getAvailableParkingBoy(List<ParkingBoy> parkingBoys) {
		ParkingBoy availableParkingBoy = parkingBoys.get(0);
		for (ParkingBoy parkingBoy : parkingBoys) {
			if (availableParkingBoy.getEmptySpaces() < parkingBoy.getEmptySpaces())
				availableParkingBoy = parkingBoy;
		}

		if (availableParkingBoy.getEmptySpaces() == 0) {
			throw new NoSpaceException("所有停车场都没有空车位了！");
		}

		return availableParkingBoy;
	}
}
