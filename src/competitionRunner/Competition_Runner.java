package competitionRunner;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import SAT.SAT;
import BinPacking.*;
import com.g52aim.project.tsp.hyperheuristics.SR_IE_HH;
import FlowShop.*;
import PersonnelScheduling.*;
import travelingSalesmanProblem.*;
import VRP.*;

public class Competition_Runner {
	
	private final CompetitionTestFrameConfig config;
	private final int TOTAL_RUNS;
	private final String[] DOMAINS;
	private final int[][] INSTANCE_IDs;
	private final long RUN_TIME;
	private final long[] SEEDS;
	
	private Competition_Runner(CompetitionTestFrameConfig config) {
		this.config = config;
		this.TOTAL_RUNS = config.getTotalRuns();
		this.DOMAINS = config.PROBLEM_DOMAINS;
		this.INSTANCE_IDs = config.getInstanceIDs();
		this.SEEDS = config.getSeeds();
		this.RUN_TIME = config.getRunTime();
	}

	private void runTests() {
		String hyperHeuristicName = null;
		String problemDomain = null;
		double[] bestSolutionFitness_s = new double[TOTAL_RUNS];

		for (int j = 0; j < DOMAINS.length; j++) {
			System.out.println(DOMAINS[j]);
			for (int i = 0; i < INSTANCE_IDs[j].length; i++) {
				int instanceID = INSTANCE_IDs[j][i];
				for (int run = 0; run < TOTAL_RUNS; run++) {
					long seed = SEEDS[run];
					HyperHeuristic hh = new SR_IE_HH(seed);
					ProblemDomain problem = null;

					if(j == 0) {
						problem = new BinPacking(seed);
					}
					else if(j == 1) {
						problem = new FlowShop(seed);
					}
					else if(j == 2) {
						problem = new PersonnelScheduling(seed);
					}
					else if(j == 3) {
						problem = new SAT(seed);
					}
					else if(j == 4) {
						problem = new TSP(seed);
					}
					else if(j == 5) {
						problem = new VRP(seed);
					}

					assert problem != null;
					problem.loadInstance(instanceID);
					hh.setTimeLimit(RUN_TIME);
					hh.loadProblemDomain(problem);
					hh.run();

					hyperHeuristicName = hh.toString();
					problemDomain = problem.toString();
					bestSolutionFitness_s[run] = hh.getBestSolutionValue();
					System.out.println("Instance ID: " + instanceID + "\tTrial: " + run + "\tf(s_{best}) = " + hh.getBestSolutionValue());
				}

				//print or save results
				StringBuilder sb = new StringBuilder();
				sb.append(hyperHeuristicName).append(",").append(RUN_TIME).append(",").append(problemDomain).append(",").append(instanceID);
				for (double ofv : bestSolutionFitness_s) {
					sb.append(",").append(ofv);
				}
				config.saveData(hyperHeuristicName + ".csv", sb.toString());
			}
		}
	}
	
	public static void main(String [] args) {
		new Competition_Runner(new CompetitionTestFrameConfig()).runTests();
	}

}