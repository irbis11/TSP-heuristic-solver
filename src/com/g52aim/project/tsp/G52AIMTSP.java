package com.g52aim.project.tsp;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.g52aim.project.tsp.heuristics.*;
import com.g52aim.project.tsp.hyperheuristics.SR_IE_HH;
import com.g52aim.project.tsp.instance.InitialisationMode;
import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.instance.TSPInstance;
import com.g52aim.project.tsp.instance.reader.TSPInstanceReader;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;
import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import com.g52aim.project.tsp.solution.TSPSolution;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public class G52AIMTSP extends ProblemDomain {

	// variables initialization
	private String[] instanceFiles = new String[]{"square", "circle", "plus", "dj38", "T81", "u724"};
	private static TSPSolution bestSolution;
	private TSPSolution[] solutionPopulation;
	private static TSPInstance problemInstance;
	private TSPObjectiveFunction objectiveFunction;

	private final int[] mutations = new int[]{0, 1, 2};
	private final int[] localSearches = new int[]{3, 4};
	private final int[] crossovers = new int[]{5, 6};

	public G52AIMTSP(long seed) {
		super(seed);
	}
	
	public TSPSolutionInterface getSolution(int index) {
		return solutionPopulation[index];
	}
	
	public TSPSolutionInterface getBestSolution() {
		return bestSolution;
	}

	@Override
	public double applyHeuristic(int hIndex, int currentIndex, int candidateIndex) {
		// apply heuristic based on index
		TSPSolution tempSolution = solutionPopulation[currentIndex].clone();

		if (hIndex == 0) {
			AdjacentSwap adjacentSwap = new AdjacentSwap(rng);
			adjacentSwap.setObjectiveFunction(objectiveFunction);
			adjacentSwap.apply(tempSolution, depthOfSearch, intensityOfMutation);
		}
		else if (hIndex == 1) {
			TwoOpt twoOpt = new TwoOpt(rng);
			twoOpt.setObjectiveFunction(objectiveFunction);
			twoOpt.apply(tempSolution, depthOfSearch, intensityOfMutation);
		}
		else if (hIndex == 2) {
			Reinsertion reinsertion = new Reinsertion(rng);
			reinsertion.setObjectiveFunction(objectiveFunction);
			reinsertion.apply(tempSolution, depthOfSearch, intensityOfMutation);
		}
		else if (hIndex == 3) {
			NextDescent nextDescent = new NextDescent(rng);
			nextDescent.setObjectiveFunction(objectiveFunction);
			nextDescent.apply(tempSolution, depthOfSearch, intensityOfMutation);
		}
		else if (hIndex == 4) {
			DavissHillClimbing davissHillClimbing = new DavissHillClimbing(rng);
			davissHillClimbing.setObjectiveFunction(objectiveFunction);
			davissHillClimbing.apply(tempSolution, depthOfSearch, intensityOfMutation);
		}

		solutionPopulation[candidateIndex] = tempSolution.clone();
		updateBestSolution(candidateIndex);
		return solutionPopulation[candidateIndex].getObjectiveFunctionValue();
	}

	@Override
	public double applyHeuristic(int hIndex, int parent1Index, int parent2Index, int candidateIndex) {
		// apply heuristic based on index
		if (hIndex == 5) {
			OX ox = new OX(rng);
			ox.setObjectiveFunction(objectiveFunction);
			ox.apply(solutionPopulation[parent1Index], solutionPopulation[parent2Index], solutionPopulation[candidateIndex], depthOfSearch, intensityOfMutation);
		}
		else if (hIndex == 6) {
			PMX pmx = new PMX(rng);
			pmx.setObjectiveFunction(objectiveFunction);
			pmx.apply(solutionPopulation[parent1Index], solutionPopulation[parent2Index], solutionPopulation[candidateIndex], depthOfSearch, intensityOfMutation);
		}

		updateBestSolution(candidateIndex);
		return solutionPopulation[candidateIndex].getObjectiveFunctionValue();
	}

	@Override
	public String bestSolutionToString() {
		return getBestSolution().toString();
	}

	@Override
	public boolean compareSolutions(int a, int b) {
		return solutionPopulation[a].getObjectiveFunctionValue() < solutionPopulation[b].getObjectiveFunctionValue();
	}

	@Override
	public void copySolution(int a, int b) {
		solutionPopulation[a] = solutionPopulation[b].clone();
	}

	@Override
	public double getBestSolutionValue() {
		return bestSolution.getObjectiveFunctionValue();
	}
	
	@Override
	public double getFunctionValue(int index) {
		return solutionPopulation[index].getObjectiveFunctionValue();
	}

	@Override
	public int[] getHeuristicsOfType(HeuristicType type) {
		if (type == HeuristicType.LOCAL_SEARCH) {
			return localSearches;
		}
		else if (type == HeuristicType.CROSSOVER) {
			return  crossovers;
		}
		else if (type == HeuristicType.MUTATION) {
			return mutations;
		}
		return null;	
	}

	@Override
	public int[] getHeuristicsThatUseDepthOfSearch() {
		return localSearches;
	}

	@Override
	public int[] getHeuristicsThatUseIntensityOfMutation() {
		return new int[] {0, 1, 2, 5, 6};
	}

	@Override
	public int getNumberOfHeuristics() {
		if (mutations == null) {
			return 0;
		}
		return mutations.length + localSearches.length + crossovers.length;
	}

	@Override
	public int getNumberOfInstances() {
		if (instanceFiles == null) {
			return 0;
		}
		return instanceFiles.length;
	}

	@Override
	public void initialiseSolution(int index) {
		if (index >= solutionPopulation.length) {
			return;
		}

		this.solutionPopulation[index] = problemInstance.createSolution(InitialisationMode.RANDOM);
		updateBestSolution(index);
	}

	@Override
	public void loadInstance(int instanceId) {
		String SEP = FileSystems.getDefault().getSeparator();
		String instanceName = "instances" + SEP + "tsp" + SEP + instanceFiles [instanceId] + ".tsp";
		TSPInstanceReader reader = new TSPInstanceReader();
		problemInstance = (TSPInstance) reader.readTSPInstance(Paths.get(instanceName), rng);
		objectiveFunction = new TSPObjectiveFunction(problemInstance);
	}

	@Override
	public void setMemorySize(int size) {
		solutionPopulation = new TSPSolution[size];
	}

	@Override
	public String solutionToString(int index) {
		return solutionPopulation[index].toString();
	}

	@Override
	public String toString() {
		String username = "ezymk2";
		return username +  "'s TSP";
	}

	private void updateBestSolution(int index) {
		if (bestSolution == null || solutionPopulation[index].getObjectiveFunctionValue() < bestSolution.getObjectiveFunctionValue()) {
			bestSolution = solutionPopulation[index].clone();
		}
	}

	public Location[] getLocations(TSPSolution solution) {
		Location[] route = new Location[solution.getNumberOfCities()];
		ArrayList temp = solution.getSolutionRepresentation().getRepresentationOfSolution();
		for (int i = 0; i < solution.getNumberOfCities(); i++) {
			route[i] = problemInstance.getLocationForCity((Integer) temp.get(i));
		}
		return route;
	}

	/**
	 * You can use this for testing :)
	 *  
	 * @param args
	 */
	public static void main(String [] args) {
		long seed = 527893L;
		long timeLimit = 10_000;
		G52AIMTSP tsp = new G52AIMTSP(seed);
		HyperHeuristic hh = new SR_IE_HH(seed);
		tsp.loadInstance( 0 );
		hh.setTimeLimit(timeLimit);
		hh.loadProblemDomain(tsp);
		hh.run();
		
		double best = hh.getBestSolutionValue();
		System.out.println(best);

		List<Location> routeLocations = new ArrayList<>();
		for (Integer city : bestSolution.getSolutionRepresentation().getRepresentationOfSolution()) {
			routeLocations.add(problemInstance.getLocationForCity(city));
		}
		SolutionPrinter.printSolution(routeLocations);
	}

}