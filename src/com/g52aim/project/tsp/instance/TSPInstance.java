package com.g52aim.project.tsp.instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import com.g52aim.project.tsp.TSPObjectiveFunction;
import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.solution.SolutionRepresentation;
import com.g52aim.project.tsp.solution.TSPSolution;

/**
 * 
 * @author Warren G. Jackson
 * 
 * A TSP instance Object containing any necessary information
 * related to the respective problem instance.
 */
public class TSPInstance implements TSPInstanceInterface {
	
	private final Location[] locations;
	private final int numberOfCities;
	private final Random random;
	private ObjectiveFunctionInterface f = null;
	
	public TSPInstance(int numberOfCities, Location[] locations, Random random) {
		this.numberOfCities = numberOfCities;
		this.random = random;
		this.locations = locations;
	}

	/**
	 * 
	 */
	@Override
	public TSPSolution createSolution(InitialisationMode mode) {
		// initialize solution in random order
		ArrayList<Integer> tour = new ArrayList<>(numberOfCities);
		for (int i = 0; i < numberOfCities; i++) {
			tour.add(i);
		}
		Collections.shuffle(tour, random);
		SolutionRepresentation solutionRepresentation = new SolutionRepresentation(tour);
		getTSPObjectiveFunction();
		return new TSPSolution(solutionRepresentation, f.getObjectiveFunctionValue(solutionRepresentation), numberOfCities);
	}
	
	@Override
	public int getNumberOfCities() {
		return numberOfCities;
	}

	@Override
	public Location getLocationForCity(int cityId) {
		return locations[cityId];
	}

	@Override
	public ObjectiveFunctionInterface getTSPObjectiveFunction() {
		if(f == null) {
			this.f = new TSPObjectiveFunction(this);
		}
		return f;
	}

}