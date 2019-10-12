package com.g52aim.project.tsp.interfaces;

import com.g52aim.project.tsp.instance.InitialisationMode;
import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.solution.TSPSolution;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public interface TSPInstanceInterface {

	/**
	 * A factory method to create new solutions to the TSP problem
	 * where the route is generated based on the InitialisationMode.
	 * 
	 * @param mode The way in which a solution should be initialised.
	 * @return A solution to the current TSP problem instance.
	 */
	public TSPSolution createSolution(InitialisationMode mode);
	
	/**
	 * 
	 * @return The objective function with reference to the information
	 * of this problem instance.
	 */
	public ObjectiveFunctionInterface getTSPObjectiveFunction();
	
	/**
	 * 
	 * @return The number of cities in this problem instance.
	 */
	public int getNumberOfCities();
	
	/**
	 * 
	 * @param cityId The ID of the associated city. 
	 * @return The location of the given city.
	 */
	public Location getLocationForCity(int cityId);
}
