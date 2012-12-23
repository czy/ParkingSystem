package cn.beihangsoft.parkingsystem.model;

import java.util.HashMap;
import java.util.Map;

public class ParkingPlace {
	private static int UNIQUE_ID = 0;
	private int id = 0;
	private int totalSpaces;
	private Map<Ticket, Car> placeCarMap = new HashMap<Ticket, Car>();

	public ParkingPlace() {
	}

	public ParkingPlace(int totalSpaces) {
		this.totalSpaces = totalSpaces;
		id = ++UNIQUE_ID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmptySpaces() {
		return totalSpaces - placeCarMap.size();
	}

	public int getTotalSpaces() {
		return totalSpaces;
	}

	public void setTotalSpaces(int totalSpaces) {
		this.totalSpaces = totalSpaces;
	}

	public double getVacancyRate() {
		return (double)(totalSpaces - placeCarMap.size()) / totalSpaces;
	}

	public Ticket park(Car car) {
		if (placeCarMap.size() == totalSpaces) {
			return null;
		}

		Ticket ticket = new Ticket();
		placeCarMap.put(ticket, car);
		return ticket;
	}

	public Car fetch(Ticket ticket) {
		Car car = placeCarMap.get(ticket);
		placeCarMap.remove(ticket);

		return car;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ParkingPlace) {
			return id == ((ParkingPlace)obj).getId();
		}

		return false;
	}

	@Override
	public String toString() {
		return "ParkingPlace(" + id + ")";
	}
}
