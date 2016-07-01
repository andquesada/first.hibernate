package exceptions;

public class NoConfigurationAvailable extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4134087423038139925L;
	
	public NoConfigurationAvailable() {
		super("Configuration not initialized.");
	}
}
