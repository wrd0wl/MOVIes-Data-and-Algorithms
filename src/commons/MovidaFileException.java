package src.commons;

/**
 * 
 * Exception thrown in case of error while loading or saving data 
 * 
 */
public class MovidaFileException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Error loading or saving data.";
	}
}
