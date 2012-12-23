package cn.beihangsoft.parkingsystem.strategy;

import java.util.List;
import cn.beihangsoft.parkingsystem.NoSpaceException;
import cn.beihangsoft.parkingsystem.manager.ParkingBoy;

/**
 * 策略：所有停车场整体空置率最高的 ParkingBoy
 */
public class ChooseParkingBoyByVacancyRateStrategy implements ChooseParkingBoyStrategy {
	@Override
	public ParkingBoy getAvailableParkingBoy(List<ParkingBoy> parkingBoyList) {
		ParkingBoy availableParkingBoy = parkingBoyList.get(0);
		for (ParkingBoy parkingBoy : parkingBoyList) {
			double vacancyRate = (double)(availableParkingBoy.getEmptySpaces()) / availableParkingBoy.getTotalSpaces();
			double newVacancyRate = (double)(parkingBoy.getEmptySpaces()) / parkingBoy.getTotalSpaces();

			if (newVacancyRate > vacancyRate) {
				availableParkingBoy = parkingBoy;
			}
		}

		if (availableParkingBoy.getEmptySpaces() == 0) {
			throw new NoSpaceException("所有停车场都没有空车位了！");
		}

		return availableParkingBoy;
	}
}
