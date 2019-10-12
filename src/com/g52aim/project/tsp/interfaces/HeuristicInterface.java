package com.g52aim.project.tsp.interfaces;

/**
 * 
 * @author Warren G. Jackson
 * 
 * Base class for all TSP heuristics.
 *
 */
public interface HeuristicInterface {

	/**
	 * Applies the current heuristic to the solution <code>solution</code>
	 * and updates the objective value of the solution.
	 * @param solution
	 * @param f
	 */
	public double apply(TSPSolutionInterface solution, double depthOfSearch, double intensityOfMutation);
	
	/**
	 * 
	 * @return Whether or not the current heuristic is a crossover heuristic.
	 */
	public boolean isCrossover();
	
	/**
	 * 
	 * @return Whether or not the current heuristic uses the intensity of mutation setting.
	 */
	public boolean usesIntensityOfMutation();
	
	/**
	 * 
	 * @return Whether or not the current heuristic uses the depth of search setting.
	 */
	public boolean usesDepthOfSearch();
	
	/**
	 * 
	 * Sets the objective function to be used by the current heuristic.
	 * @param f The objective function.
	 */
	public void setObjectiveFunction(ObjectiveFunctionInterface f);
}
