package cn.beihangsoft.parkingsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cn.beihangsoft.parkingsystem.manager.ParkingBoy;
import cn.beihangsoft.parkingsystem.manager.ParkingManager;
import cn.beihangsoft.parkingsystem.model.Car;
import cn.beihangsoft.parkingsystem.model.ParkingPlace;
import cn.beihangsoft.parkingsystem.model.Ticket;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingBoyByMaxEmptySpacesStrategy;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingPlaceByFirstEmptySpaceStrategy;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingPlaceByMaxEmptySpacesStrategy;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingPlaceByVacancyRateStrategy;

/**
 * 停车场管理系统模拟类
 */
public class Simulator {
	public static void main(String[] args) {
		System.setProperty("debug", "true");

		int totalCars = 301;
		List<Car> carList = new ArrayList<Car>();
		List<Ticket> ticketList = new ArrayList<Ticket>();

		ParkingBoy stupidParkingBoy = new ParkingBoy(Arrays.asList(new ParkingPlace(10), new ParkingPlace(20)),
				new ChooseParkingPlaceByFirstEmptySpaceStrategy());
		ParkingBoy smartParkingBoy = new ParkingBoy(Arrays.asList(new ParkingPlace(20), new ParkingPlace(30)),
				new ChooseParkingPlaceByMaxEmptySpacesStrategy());
		ParkingBoy geniusParkingBoy = new ParkingBoy(Arrays.asList(new ParkingPlace(30), new ParkingPlace(40)),
				new ChooseParkingPlaceByVacancyRateStrategy());

		List<ParkingPlace> parkingPlaceList = Arrays.asList(new ParkingPlace(40), new ParkingPlace(50),
				new ParkingPlace(60));
		List<ParkingBoy> parkingBoyList = Arrays.asList(stupidParkingBoy, smartParkingBoy, geniusParkingBoy);
		ParkingManager parkingManager = new ParkingManager(parkingPlaceList,
				new ChooseParkingPlaceByVacancyRateStrategy(), parkingBoyList,
				new ChooseParkingBoyByMaxEmptySpacesStrategy());

		for (int i = 0; i < totalCars; i++) {
			carList.add(new Car());
		}

		System.out.println("====================================开始泊车====================================");
		for (Car car : carList) {
			try {
				Ticket ticket = parkingManager.park(car);
				ticketList.add(ticket);
			} catch (NoSpaceException ex) {
				System.out.println(String.format("车辆 %s停泊失败：%s", car, ex.getMessage()));
			}
		}
		System.out.println("====================================泊车结束====================================");

		// 伪造停车凭证
		ticketList.add(new Ticket());

		System.out.println("====================================开始取车====================================");
		for (Ticket ticket : ticketList) {
			try {
				parkingManager.fetch(ticket);
			} catch (NoCarException ex) {
				System.out.println(ex.getMessage());
			}
		}
		System.out.println("====================================取车结束====================================");
	}
}
