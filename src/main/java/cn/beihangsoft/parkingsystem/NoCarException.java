package cn.beihangsoft.parkingsystem;

public class NoCarException extends RuntimeException {
	private static final long serialVersionUID = -4195171753597814035L;

	public NoCarException() {
		super();
	}

	public NoCarException(String message) {
		super(message);
	}
}
