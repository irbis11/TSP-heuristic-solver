package com.g52aim.project.tsp.interfaces;

/**
 * 
 * @author Warren G. Jackson
 *
 * @param <T> The data structure that will be used for representing the solution.
 */
public interface SolutionRepresentationInterface<T> extends Cloneable {
	
	/**
	 * 
	 * @return The representation of the solution.
	 */
	public T getRepresentationOfSolution();
	
	/**
	 * Sets the solution based on the given representation.
	 * @param solutionRepresentation The representation of the solution.
	 */
	public void setRepresentationOfSolution(T solutionRepresentation);
	
	/**
	 * 
	 * @return The number of cities.
	 */
	public int getNumberOfCities();
	
	/**
	 * 
	 * @return A NEW "SolutionRepresentationInterface" which has the same (but independent) solution representation.
	 */
	public SolutionRepresentationInterface<T> clone();
}
