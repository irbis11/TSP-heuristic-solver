package com.g52aim.project.tsp.hyperheuristics;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Double.max;

/**
 *
 * @author Warren G. Jackson
 *
 * A sample hyper-heuristic using simple random
 * heuristic selection and accept improving or
 * equal moves.
 *
 */
public class SR_IE_HH extends HyperHeuristic {

	// parameter settings
	private int heuristicScoreSum = 70;
	private int[] heuristicScoreArray = new int[]{10, 10, 10, 10, 10, 10, 10};
    private double TAU = 1.02;
	private final int memorySize = 8;
	private final int candidateIndex = memorySize - 1;
	private final double IOM = 0;
	private final double DOS = 0;

	public SR_IE_HH(long seed) {
		super(seed);
	}

	@Override
	protected void solve(ProblemDomain problem) {
		// setting initial variables and initialising solution
		problem.setIntensityOfMutation(IOM);
		problem.setDepthOfSearch(DOS);
		problem.setMemorySize(memorySize);
		for (int i = 0; i < memorySize; i++) {
			problem.initialiseSolution(i);
		}
		double currentFitness;
		long iteration = 0;
		double candidateFitness;
		boolean accept;
        int heuristicIndex;
        int[] indexes;
		System.out.println("I\tf(s)\tf(s')\tAccept");
		while(!hasTimeExpired()) {
			// choose heuristic
			heuristicIndex = chooseHeuristicReinforcedLearning();
			// find parents and replacement indexes
			indexes = chooseParentsAndReplacementIndex(problem, memorySize - 1);
			currentFitness = problem.getFunctionValue(indexes[0]);

			// apply heuristic
			if (heuristicIndex == 5 || heuristicIndex == 6) {
				candidateFitness = problem.applyHeuristic(heuristicIndex, indexes[0], indexes[1], candidateIndex);
			}
			else {
				candidateFitness = problem.applyHeuristic(heuristicIndex, indexes[0], candidateIndex);
			}

			// accept or reject solution
			accept = acceptMoveUsingRRT(currentFitness, candidateFitness, problem.getBestSolutionValue());
			System.out.println(iteration + "\t" + currentFitness + "\t" + candidateFitness + "\t" + accept);
			if(accept) {
				problem.copySolution(indexes[2], candidateIndex);
				updateHeuristicScore(heuristicIndex, TRUE);
			}
			else {
				updateHeuristicScore(heuristicIndex, FALSE);
			}

			iteration++;
		}
	}

	@Override
	public String toString() {
		return "SR_IE_HH";
	}

	// reinforced learning with scores for heuristics (initial: 10, change: +/- 1, max: 15)
	private int chooseHeuristicReinforcedLearning() {
		int random = rng.nextInt(heuristicScoreSum) + 1;
		int index = 0;
		int sum = heuristicScoreArray[index];
		while (sum < random) {
			index++;
			sum += heuristicScoreArray[index];
		}
		return index;
	}

	// update scores of heuristic, between [10,15]
	private void updateHeuristicScore(int index, boolean improve) {
        if (improve) {
            if (heuristicScoreArray[index] < 15) {
                heuristicScoreArray[index]++;
                heuristicScoreSum++;
            }
        } else {
            if (heuristicScoreArray[index] > 10) {
                heuristicScoreArray[index]--;
                heuristicScoreSum--;
            }
        }
	}

	// change value of tau
	private void updateTAU(boolean improve) {
		if (improve) {
			TAU += 0.00001;
		}
		else {
			if (TAU > 1.0) {
				TAU -= 0.00001;
			}
		}
	}

	// record-to-record with adaptive parameter TAU (initial: 1.02, changes: =/- 0.00001)
	private boolean acceptMoveUsingRRT(double currentSolutionFitness, double candidateSolutionFitness, double bestSolutionFitness) {
		double tau = bestSolutionFitness*TAU;
		// if solution fitness is equal, accept solution and increase TAU (its improving escape from local optimum capability)
		if (candidateSolutionFitness == currentSolutionFitness) {
            updateTAU(TRUE);
			return false;
		}
		// if candidate fitness is better, accept solution and decrease TAU
		else if (candidateSolutionFitness <= max(currentSolutionFitness, tau)) {
            updateTAU(FALSE);
            return true;
        }
		// if candidate fitness is worst, reject solution and increase TAU
		else {
            updateTAU(TRUE);
			return false;
		}
	}

	// Essentially it finds best, worst and random solution (which is neither best or worst). For heuristic which need 2
	// parents both best and random are passed, while for heuristic with 1 parent both of them have 50% chance.
	// Replacement index have 40% of chance for being either best or random and 20% of chance for being worst.
	private int[] chooseParentsAndReplacementIndex(ProblemDomain problem, int adjustedMemorySize) {
		int[] indexes = new int[3];
		int bestIndex = 0;
		int worstIndex = 0;
		int randomIndex = -1;
		int replacementIndex;
		double randomChance = 1./(adjustedMemorySize - 2);

		for (int i = 1; i < adjustedMemorySize ; i++) {
			if (problem.getFunctionValue(bestIndex) > problem.getFunctionValue(i)) {
				bestIndex = i;
			}
			if (problem.getFunctionValue(worstIndex) < problem.getFunctionValue(i)) {
				worstIndex = i;
			}
		}
		for (int i = 0; i < adjustedMemorySize ; i++) {
			if (i != bestIndex && i != worstIndex) {
				if ((randomChance < rng.nextDouble()) || randomIndex == -1){
					randomIndex = i;
				}
			}
		}

		double randomNextInt = rng.nextDouble();
		if (0.2 < randomNextInt) {
			replacementIndex = worstIndex;
		}
		else if (0.6 < randomNextInt) {
			replacementIndex = randomIndex;
		}
		else {
			replacementIndex = bestIndex;
		}

		if (0.5 < rng.nextDouble()) {
            indexes[0] = bestIndex;
            indexes[1] = randomIndex;
		}
		else {
            indexes[1] = bestIndex;
            indexes[0] = randomIndex;
		}
		indexes[2] = replacementIndex;

		return indexes;
	}

}