package exceptions;

public class ConfigurationAvailable extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7763640209069812460L;

	public ConfigurationAvailable() {
		super("Configuration already initialized.");
	}
}
