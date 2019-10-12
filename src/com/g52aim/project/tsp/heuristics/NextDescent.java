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
public class NextDescent extends HeuristicOperators implements HeuristicInterface {
	
	public NextDescent(Random random) {
		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double dos, double iom) {
		// setting initial variables
		int adjustedDOS = adjustParameter(1, 2, 3, 4, 5, 6, dos);
		ArrayList<Integer> tour = solution.getSolutionRepresentation().getRepresentationOfSolution();
		double currentFitness = solution.getObjectiveFunctionValue();
		for (int i = 0; i < adjustedDOS; i++) {
			// find random starting point
			int firstIndex = random.nextInt(solution.getNumberOfCities());
			int counter = 0;
			while (counter < solution.getNumberOfCities()) {
				// keep swapping adjacent cities
				int[] pair = nextNumberPair(firstIndex, solution.getNumberOfCities());
				Collections.swap(tour, pair[0], pair[1]);
				// delta evaluation
				double candidateFitness = deltaEvaluationAdjacentSwap(solution, currentFitness, pair[0], pair[1]);
				// if improvement, leave while loop
				if (candidateFitness < currentFitness) {
					currentFitness = candidateFitness;
					counter = solution.getNumberOfCities();
				}
				// if no improvement, revert change
				else {
					Collections.swap(tour, pair[1], pair[0]);
					counter++;
					firstIndex = pair[1];
				}
				// if no improvement after whole run, leave for loop - no more possible changes
				if (counter == solution.getNumberOfCities()) {
					i = adjustedDOS;
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