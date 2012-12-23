package cn.beihangsoft.parkingsystem.model;

/**
 * 停车凭证
 */
public class Ticket {
	private static int UNIQUE_ID = 0;
	private int id = 0;

	public Ticket() {
		id = ++UNIQUE_ID;
	}

	public Ticket(int id) {
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
		if (obj instanceof Ticket) {
			return id == ((Ticket)obj).getId();
		}

		return false;
	}

	@Override
	public String toString() {
		return "Ticket(" + id + ")";
	}
}
