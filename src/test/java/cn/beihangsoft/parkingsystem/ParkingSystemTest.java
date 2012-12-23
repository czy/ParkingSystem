package cn.beihangsoft.parkingsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import cn.beihangsoft.parkingsystem.manager.ParkingBoy;
import cn.beihangsoft.parkingsystem.manager.ParkingManager;
import cn.beihangsoft.parkingsystem.model.Car;
import cn.beihangsoft.parkingsystem.model.ParkingPlace;
import cn.beihangsoft.parkingsystem.model.Ticket;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingBoyByMaxEmptySpacesStrategy;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingPlaceByFirstEmptySpaceStrategy;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingPlaceByMaxEmptySpacesStrategy;
import cn.beihangsoft.parkingsystem.strategy.ChooseParkingPlaceByVacancyRateStrategy;
import static junit.framework.Assert.*;

public class ParkingSystemTest {
	private List<Ticket> ticketList;
	private ParkingBoy stupidParkingBoy;
	private ParkingBoy smartParkingBoy;
	private ParkingBoy geniusParkingBoy;
	private ParkingManager parkingManager;

	@Before
	public void initParkingSystem() throws Exception {
		stupidParkingBoy = new ParkingBoy(Arrays.asList(new ParkingPlace(10), new ParkingPlace(20)),
				new ChooseParkingPlaceByFirstEmptySpaceStrategy());
		smartParkingBoy = new ParkingBoy(Arrays.asList(new ParkingPlace(20), new ParkingPlace(30)),
				new ChooseParkingPlaceByMaxEmptySpacesStrategy());
		geniusParkingBoy = new ParkingBoy(Arrays.asList(new ParkingPlace(30), new ParkingPlace(40)),
				new ChooseParkingPlaceByVacancyRateStrategy());

		ticketList = new ArrayList<Ticket>();
		List<ParkingPlace> parkingPlaceList = Arrays.asList(new ParkingPlace(40), new ParkingPlace(50),
				new ParkingPlace(60));
		List<ParkingBoy> parkingBoyList = Arrays.asList(stupidParkingBoy, smartParkingBoy, geniusParkingBoy);
		parkingManager = new ParkingManager(parkingPlaceList, new ChooseParkingPlaceByVacancyRateStrategy(),
				parkingBoyList, new ChooseParkingBoyByMaxEmptySpacesStrategy());
	}

	@Test
	public void testParkSuccess() {
		Car car = new Car();
		int emptySpaces = parkingManager.getEmptySpaces();
		parkingManager.park(car);
		assertEquals(emptySpaces - 1, parkingManager.getEmptySpaces());
	}

	@Test
	public void testFetchSuccess() {
		Car car = new Car();
		Ticket ticket = (parkingManager.park(car));
		assertSame(car, parkingManager.fetch(ticket));
	}

	@Test(expected = NoCarException.class)
	public void testFetchFail() {
		parkingManager.fetch(new Ticket());
	}

	@Test(expected = NoSpaceException.class)
	public void testNoSpaceException() {
		int emptySpaces = parkingManager.getEmptySpaces();
		for (int i = 0; i < emptySpaces; i++) {
			parkingManager.park(new Car());
		}

		parkingManager.park(new Car());
	}

	@Test
	public void testFetchAllCarsSuccess() {
		int emptySpaces = parkingManager.getEmptySpaces();
		for (int i = 0; i < emptySpaces; i++) {
			ticketList.add(parkingManager.park(new Car()));
		}

		for (Ticket ticket : ticketList) {
			parkingManager.fetch(ticket);
		}

		assertEquals(parkingManager.getTotalSpaces(), parkingManager.getEmptySpaces());
	}

	@Test
	public void testChooseParkingPlaceByFirstEmptySpaceStrategy() {
		parkingManager = new ParkingManager(Arrays.asList(stupidParkingBoy));
		List<ParkingPlace> parkingPlaceList = stupidParkingBoy.getParkingPlaceList();
		for (int i = 0; i < parkingPlaceList.get(0).getTotalSpaces(); i++) {
			parkingManager.park(new Car());
		}

		assertEquals(stupidParkingBoy.getEmptySpaces(), parkingPlaceList.get(1).getEmptySpaces());
	}

	@Test
	public void testChooseParkingPlaceByMaxEmptySpacesStrategy() {
		parkingManager = new ParkingManager(Arrays.asList(smartParkingBoy));
		List<ParkingPlace> parkingPlaceList = smartParkingBoy.getParkingPlaceList();
		for (int i = 0; i < parkingPlaceList.get(1).getTotalSpaces() - parkingPlaceList.get(0).getTotalSpaces(); i++) {
			parkingManager.park(new Car());
		}

		assertEquals(parkingPlaceList.get(0).getEmptySpaces(), parkingPlaceList.get(0).getTotalSpaces());
	}

	@Test
	public void testChooseParkingPlaceByVacancyRateStrategy() {
		parkingManager = new ParkingManager(Arrays.asList(geniusParkingBoy));
		List<ParkingPlace> parkingPlaceList = geniusParkingBoy.getParkingPlaceList();
		for (int i = 0; i < parkingManager.getTotalSpaces() / 2; i++) {
			parkingManager.park(new Car());
		}

		assertEquals(parkingPlaceList.get(0).getVacancyRate(), parkingPlaceList.get(1).getVacancyRate());
	}
}
