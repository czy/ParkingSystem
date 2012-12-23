package cn.beihangsoft.parkingsystem.strategy;

import java.util.List;
import cn.beihangsoft.parkingsystem.manager.ParkingBoy;

public interface ChooseParkingBoyStrategy {
	public ParkingBoy getAvailableParkingBoy(List<ParkingBoy> parkingBoys);
}
