package cn.beihangsoft.parkingsystem;

import org.junit.Before;
import org.junit.Test;

import cn.beihangsoft.parkingsystem.controller.ParkingController;
import cn.beihangsoft.parkingsystem.model.Car;
import cn.beihangsoft.parkingsystem.model.ParkingArea;
import static junit.framework.Assert.*;

public class ParkingSystemTest {
	ParkingController parkingSystem;

	@Before
	public void initParkingSystem() throws Exception {
		ParkingArea parkingArea=new ParkingArea();
		parkingSystem = new ParkingController(parkingArea);
	}

	@Test
	public void testParkSuccess() {
		Car car=new Car();
		car.setCarNum("111111");
		int parkNum = parkingSystem.park(car);
		assertTrue(parkNum != -1);

	}

	@Test
	public void testParkFalse() {
		for (int i = 0; i < 100; i++) {
			Car car=new Car();
			car.setCarNum(111100 + i + "");
			parkingSystem.park(car);

		}
		Car car=new Car();
		car.setCarNum("222222");
		int parkNum = parkingSystem.park(car);
		assertEquals(parkNum, -1);

	}

	@Test
	public void testGetFreeNum() {
		Car car=new Car();
		car.setCarNum("222222");
		parkingSystem.park(car);
		int freeNum = parkingSystem.getFreeNum();
		assertEquals(freeNum, 99);
	}

	@Test
	public void testFetchSuccess() {
		Car car=new Car();
		car.setCarNum("222222");
		Car car2=new Car();
		car2.setCarNum("333333");
		int parkNumB = parkingSystem.park(car);
		int parkNum = parkingSystem.fetch(car);
		assertEquals(parkNum, parkNumB);
	}

	@Test
	public void testFetchFalse() {
		Car car=new Car();
		car.setCarNum("222222");
		Car car2=new Car();
		car2.setCarNum("333333");
		parkingSystem.park(car);
		int parkNum = parkingSystem.fetch(car2);
		assertEquals(parkNum, -1);
	}

	@Test
	public void testFetchFalse2() {
		Car car=new Car();
		car.setCarNum("222222");
		parkingSystem.park(car);
		parkingSystem.fetch(car);
		int parkNum = parkingSystem.fetch(car);
		assertTrue(parkNum == -1);
	}
}
