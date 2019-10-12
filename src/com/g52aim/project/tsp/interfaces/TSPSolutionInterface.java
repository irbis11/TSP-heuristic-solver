package com.g52aim.project.tsp.interfaces;

import com.g52aim.project.tsp.solution.SolutionRepresentation;

/**
 * 
 * @author Warren G. Jackson
 * 
 * Describes and contains information about a solution to a TSP problem.
 * 
 * Note that a TSP solution should implement a DEEP CLONE of the solution
 * so that a solution can be copied between solution indices in the problem domain.
 */
public interface TSPSolutionInterface extends Cloneable {
	
	/**
	 * Gets the objective value of the current solution.
	 * @return The objective value of the current solution.
	 */
	public double getObjectiveFunctionValue();
	
	/**
	 * Sets the objective value of the current solution.
	 * @param objectiveFunctionValue The objective value of the current solution.
	 */
	public void setObjectiveFunctionValue(double objectiveFunctionValue);
	
	/**
	 * Gets a representation of the solution to the problem.
	 * @return A representation of the solution.
	 */
	public SolutionRepresentation getSolutionRepresentation();
	
	/**
	 * 
	 * @return The number of cities in this instance related to this solution.
	 */
	public int getNumberOfCities();

	/**
	 * 
	 * @return A NEW "TSPSolutionInterface" which has the same (but independent) solution information.
	 */
	public TSPSolutionInterface clone();

}
