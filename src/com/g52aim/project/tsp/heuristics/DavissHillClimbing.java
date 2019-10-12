package com.g52aim.project.tsp.heuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 * 
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 *
 */
public class DavissHillClimbing extends HeuristicOperators implements HeuristicInterface {
	
	public DavissHillClimbing(Random random) {
		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double dos, double iom) {
		// setting initial variables
		int adjustedDOS = adjustParameter(1, 2, 3, 4, 5, 6, dos);
		ArrayList<Integer> tour = solution.getSolutionRepresentation().getRepresentationOfSolution();
		double currentFitness = solution.getObjectiveFunctionValue();
		for (int i = 0; i < adjustedDOS; i++) {
			// create random order in which cities will be swapped
			ArrayList<Integer> randomOrder = createRandomOrder(solution.getNumberOfCities());
			for (Integer randomCity : randomOrder) {
				// find next index for given position
				int[] pair = nextNumberPair(randomCity, solution.getNumberOfCities());
				// swap cities
				Collections.swap(tour, pair[0], pair[1]);
				// delta evaluation
				double candidateFitness = deltaEvaluationDavissHill(solution, currentFitness, pair[0], pair[1]);
				// if improvement, update fitness
				if (candidateFitness <= currentFitness) {
					currentFitness = candidateFitness;
				}
				// if no improvement, revert change
				else {
					Collections.swap(tour, pair[1], pair[0]);
				}
			}
		}
		solution.setObjectiveFunctionValue(currentFitness);
		return solution.getObjectiveFunctionValue();
	}

	@Override
	public boolean isCrossover() {
		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {
		return false;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return true;
	}

}