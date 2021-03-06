package cn.beihangsoft.parkingsystem.model;

public class Car {
	private static int UNIQUE_ID = 0;
	private int id = 0;

	public Car() {
		id = ++UNIQUE_ID;
	}

	public Car(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Car) {
			return id == ((Car)obj).getId();
		}

		return false;
	}

	@Override
	public String toString() {
		return "Car(" + id + ")";
	}
}
