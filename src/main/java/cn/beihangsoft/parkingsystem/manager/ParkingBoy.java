package cn.beihangsoft.parkingsystem.manager;

import java.util.List;

import cn.beihangsoft.parkingsystem.NoCarException;
import cn.beihangsoft.parkingsystem.model.Car;
import cn.beihangsoft.parkingsystem.model.ParkingPlace;
import cn.beihangsoft.parkingsystem.model.ParkingPlaceParam;
import cn.beihangsoft.parkingsystem.model.Ticket;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingPlaceStrategy;

public class ParkingBoy {
	private static int UNIQUE_ID = 0;
	private int id = 0;
	private List<ParkingPlace> parkingPlaceList;
	private ChooseParkingPlaceStrategy chooseParkingPlaceStrategy;

	public ParkingBoy(List<ParkingPlace> parkingPlaceList, ChooseParkingPlaceStrategy chooseParkingPlaceStrategy) {
		this.parkingPlaceList = parkingPlaceList;
		this.chooseParkingPlaceStrategy = chooseParkingPlaceStrategy;
		id = ++UNIQUE_ID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ParkingPlace> getParkingPlaceList() {
		return parkingPlaceList;
	}

	public void setParkingPlaceList(List<ParkingPlace> parkingPlaceList) {
		this.parkingPlaceList = parkingPlaceList;
	}

	public int getEmptySpaces() {
		int emptySpaces = 0;
		for (ParkingPlace parkingPlace : parkingPlaceList) {
			emptySpaces += parkingPlace.getEmptySpaces();
		}

		return emptySpaces;
	}

	public int getTotalSpaces() {
		int totalSpaces = 0;
		for (ParkingPlace parkingPlace : parkingPlaceList) {
			totalSpaces += parkingPlace.getTotalSpaces();
		}

		return totalSpaces;
	}

	public Ticket park(Car car, ParkingPlaceParam parkingPlaceParam) {
		ParkingPlace parkingPlace = chooseParkingPlaceStrategy.getAvailableParkingPlace(parkingPlaceList);
		parkingPlaceParam.setParkingPlace(parkingPlace);
		return parkingPlace.park(car);
	}

	public Car fetch(Ticket ticket, ParkingPlaceParam parkingPlaceParam) {
		for (ParkingPlace parkingPlace : parkingPlaceList) {
			Car car = parkingPlace.fetch(ticket);
			if (car != null) {
				parkingPlaceParam.setParkingPlace(parkingPlace);
				return car;
			}
		}

		throw new NoCarException("没有此车或凭证无效！");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ParkingBoy) {
			return id == ((ParkingBoy)obj).getId();
		}

		return false;
	}

	@Override
	public String toString() {
		return "ParkingBoy(" + id + ")";
	}
}
