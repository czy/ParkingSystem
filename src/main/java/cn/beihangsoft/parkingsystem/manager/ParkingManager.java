package cn.beihangsoft.parkingsystem.manager;

import java.util.ArrayList;
import java.util.List;

import cn.beihangsoft.parkingsystem.NoCarException;
import cn.beihangsoft.parkingsystem.NoSpaceException;
import cn.beihangsoft.parkingsystem.model.Car;
import cn.beihangsoft.parkingsystem.model.ParkingPlace;
import cn.beihangsoft.parkingsystem.model.ParkingPlaceParam;
import cn.beihangsoft.parkingsystem.model.Ticket;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingBoyByMaxEmptySpacesStrategy;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingBoyStrategy;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingPlaceStrategy;

public final class ParkingManager {
	private static int UNIQUE_ID = 0;
	private int id = 0;
	private ParkingBoy self;
	private List<ParkingBoy> parkingBoyList = new ArrayList<ParkingBoy>();
	private ChooseParkingBoyStrategy chooseParkingBoyStrategy;

	public ParkingManager(List<ParkingBoy> parkingBoyList) {
		this(null, null, parkingBoyList, new ChooseParkingBoyByMaxEmptySpacesStrategy());
	}

	/**
	 * 构造方法
	 * @param parkingPlaceList 经理直接管理的停车场
	 * @param chooseParkingPlaceStrategy 经理选用停车场的策略
	 * @param parkingBoyList 经理手下的 ParkingBoy
	 * @param chooseParkingBoyStrategy 经理选用 ParkingBoy 的策略
	 */
	public ParkingManager(List<ParkingPlace> parkingPlaceList, ChooseParkingPlaceStrategy chooseParkingPlaceStrategy,
			List<ParkingBoy> parkingBoyList, ChooseParkingBoyStrategy chooseParkingBoyStrategy) {
		if (parkingPlaceList != null) {
			this.self = new ParkingBoy(parkingPlaceList, chooseParkingPlaceStrategy);
			this.parkingBoyList.add(self);
		}

		this.parkingBoyList.addAll(parkingBoyList);
		this.chooseParkingBoyStrategy = chooseParkingBoyStrategy;
		id = ++UNIQUE_ID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ParkingBoy getSelf() {
		return self;
	}

	public void setSelf(ParkingBoy self) {
		this.self = self;
	}

	public List<ParkingBoy> getParkingBoyList() {
		return parkingBoyList;
	}

	public void setParkingBoyList(List<ParkingBoy> parkingBoyList) {
		this.parkingBoyList = parkingBoyList;
	}

	public int getEmptySpaces() {
		int emptySpaces = 0;
		for (ParkingBoy parkingBoy : parkingBoyList) {
			emptySpaces += parkingBoy.getEmptySpaces();
		}

		return emptySpaces;
	}

	public int getTotalSpaces() {
		int totalSpaces = 0;
		for (ParkingBoy parkingBoy : parkingBoyList) {
			totalSpaces += parkingBoy.getTotalSpaces();
		}

		return totalSpaces;
	}

	public Ticket park(Car car) {
		ParkingPlaceParam parkingPlaceParam = new ParkingPlaceParam();
		ParkingBoy parkingBoy = chooseParkingBoyStrategy.getAvailableParkingBoy(parkingBoyList);

		try {
			Ticket ticket = parkingBoy.park(car, parkingPlaceParam);
			if ("true".equalsIgnoreCase(System.getProperty("debug"))) {
				System.out.println(String.format("获得停车凭证 %s：车辆 %s停泊在 %s管理的停车场 %s。", ticket, car,
						parkingBoy == self ? this : parkingBoy, parkingPlaceParam.getParkingPlace()));
			}

			return ticket;
		} catch (NoSpaceException ex) {
			throw ex;
		}
	}

	public Car fetch(Ticket ticket) {
		Car car = null;
		ParkingPlaceParam parkingPlaceParam = new ParkingPlaceParam();
		for (ParkingBoy parkingBoy : parkingBoyList) {
			try {
				car = parkingBoy.fetch(ticket, parkingPlaceParam);
				if ("true".equalsIgnoreCase(System.getProperty("debug"))) {
					System.out.println(String.format("使用停车凭证 %s取车：已从 %s管理的停车场 %s取出车辆 %s。", ticket,
							parkingBoy == self ? this : parkingBoy, parkingPlaceParam.getParkingPlace(), car));
				}

				return car;
			} catch (NoCarException ex) {
			}
		}

		throw new NoCarException(String.format("使用停车凭证 %s取车：没有此车或凭证无效！", ticket));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ParkingManager) {
			return id == ((ParkingManager)obj).getId();
		}

		return false;
	}

	@Override
	public String toString() {
		return "ParkingManager(" + id + ")";
	}
}
