package src.commons;

/**
 * 
 * Interface that is used to describe the Movida application configuration operations.
 * 
 * Allows you to select the dictionary implementation and the sorting algorithm to be used for following search operations. 
 * The possible options are in the MapImplementation and SortingAlgorithm enumerations.
 * 
 */
public interface IMovidaConfig {

	/**
	 * Select the sorting algorithm. 
	 * 
	 * If the chosen algorithm is not supported by the application the configuration does not change.
	 * 
	 * @param a select the algorithm.
	 * @return <code> true </code> if the configuration has been changed, <code> false </code> otherwise.
	 */
	public boolean setSort(SortingAlgorithm a);

	/**
	 * Select the dictionary implementation.
	 * 
	 * If the chosen dictionary is not supported by the application the configuration does not change.
	 *
	 * @param m select the implementation
	 * @return <code> true </code> if the configuration has been changed, <code> false </code> otherwise.
	 */
	public boolean setMap(MapImplementation m);
}
