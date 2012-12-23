package cn.beihangsoft.parkingsystem;

public class NoSpaceException extends RuntimeException {
	private static final long serialVersionUID = 908448732754664741L;

	public NoSpaceException() {
		super();
	}

	public NoSpaceException(String message) {
		super(message);
	}
}
