package cn.beihangsoft.sep;

import org.junit.Before;
import org.junit.Test;

import cn.beihangsoft.sep.ParkingSystem;
import static junit.framework.Assert.*;

public class ParkingSystemTest {
	ParkingSystem parkingSystem;

	@Before
	public void initParkingSystem() throws Exception {
		parkingSystem = new ParkingSystem();
	}

	@Test
	public void testParkSuccess() {
		int parkNum = parkingSystem.park("111111");
		assertTrue(parkNum != -1);

	}

	@Test
	public void testParkFalse() {
		for (int i = 0; i < 100; i++) {
			parkingSystem.park(111100 + i + "");

		}
		int parkNum = parkingSystem.park("222222");
		assertEquals(parkNum, -1);

	}

	@Test
	public void testGetFreeNum() {
		parkingSystem.park("111111");
		int freeNum = parkingSystem.getFreeNum();
		assertEquals(freeNum, 99);
	}

	@Test
	public void testFetchSuccess() {
		int parkNumB=parkingSystem.park("111111");
		int parkNum = parkingSystem.fetch("111111");
		assertEquals(parkNum,parkNumB);
	}

	@Test
	public void testFetchFalse() {
		parkingSystem.park("111111");
		int parkNum = parkingSystem.fetch("333333");
		assertEquals(parkNum, -1);
	}
	@Test
	public void testFetchFalse2() {
		parkingSystem.park("111111");
		parkingSystem.fetch("111111");
		int parkNum = parkingSystem.fetch("111111");
		assertTrue(parkNum==-1);
	}
}
