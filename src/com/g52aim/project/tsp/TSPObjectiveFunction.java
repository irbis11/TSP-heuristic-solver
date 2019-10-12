package com.g52aim.project.tsp;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.solution.SolutionRepresentation;
import java.util.ArrayList;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public class TSPObjectiveFunction implements ObjectiveFunctionInterface {
	
	private final TSPInstanceInterface instance;
	
	public TSPObjectiveFunction(TSPInstanceInterface instance) {
		this.instance = instance;
	}

	@Override
	public double getObjectiveFunctionValue(SolutionRepresentation solution) {
		// calculate fitness value for whole solution
		double cost = 0.0;
		ArrayList<Integer> solutionRepresentation = solution.getRepresentationOfSolution();
		for (int i = 0; i < solution.getNumberOfCities(); i++) {
			int location_a = solutionRepresentation.get(i);
			int location_b;
			if (i + 1 >= solution.getNumberOfCities()){
				location_b = solutionRepresentation.get(0);
			}
			else {
				location_b = solutionRepresentation.get(i + 1);
			}
			cost += getCost(location_a, location_b);
		}
		return cost;
	}

	@Override
	public double getCost(int location_a, int location_b) {
		// get distance between 2 cities
		double aX = instance.getLocationForCity(location_a).getX();
		double aY = instance.getLocationForCity(location_a).getY();
		double bX = instance.getLocationForCity(location_b).getX();
		double bY = instance.getLocationForCity(location_b).getY();
		return (int) Math.rint(Math.sqrt((aX - bX)*(aX - bX) + (aY - bY)*(aY - bY)));
	}

}