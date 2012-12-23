package cn.beihangsoft.parkingsystem.strategy;

import java.util.List;
import cn.beihangsoft.parkingsystem.model.ParkingPlace;

public interface ChooseParkingPlaceStrategy {
	ParkingPlace getAvailableParkingPlace(List<ParkingPlace> parkingPlaceList);
}
