package simulator.control;

public class DifferentStructs_Exception extends Exception{

	private static final long serialVersionUID = -8943985273521923691L;

	public DifferentStructs_Exception(String message,int step) {
		super(message+ " en el paso " + step);
	}

	
}
