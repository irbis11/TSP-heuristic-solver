package com.g52aim.project.tsp.solution;

import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 * 
 * @author Warren G. Jackson
 * 
 * An implementation of the TSPSolutionInterface which
 * describes and contains information about a solution to a TSP problem.
 *
 */
public class TSPSolution implements TSPSolutionInterface {

	private SolutionRepresentationInterface<?> representation;
	private double objectiveFunctionValue;
	private int numberOfCities;
	
	public TSPSolution(SolutionRepresentationInterface<?> representation, double objectiveFunctionValue, int numberOfCities) {
		this.representation = representation;
		this.objectiveFunctionValue = objectiveFunctionValue;
		this.numberOfCities = numberOfCities;
	}

	@Override
	public SolutionRepresentation getSolutionRepresentation() {
		return (SolutionRepresentation)representation;
	}

	@Override
	public int getNumberOfCities() {
		return representation.getNumberOfCities();
	}
	
	@Override
	public double getObjectiveFunctionValue() {
		return objectiveFunctionValue;
	}

	@Override
	public void setObjectiveFunctionValue(double objectiveFunctionValue) {
		this.objectiveFunctionValue = objectiveFunctionValue;
	}

	@Override
	public TSPSolution clone() {
		return new TSPSolution(representation.clone(), objectiveFunctionValue, numberOfCities);
	}

}