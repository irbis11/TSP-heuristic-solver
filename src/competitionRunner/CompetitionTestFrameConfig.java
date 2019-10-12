package competitionRunner;

import com.g52aim.hyflex.HyFlexTestFrame;

public class CompetitionTestFrameConfig extends HyFlexTestFrame {

	private final long RUN_TIME_IN_SECONDS = 300;
	protected final String[] PROBLEM_DOMAINS = { "BP", "FS", "PS", "SAT", "TSP", "VRP"};
	private final int[][] INSTANCE_IDs = {{1,7,9,10,11},{1,3,8,10,11},{5,8,9,10,11},{3,4,5,10,11},{0,2,6,7,8},{1,2,5,6,9}};
	
	@Override
	public String[] getDomains() {
		return this.PROBLEM_DOMAINS;
	}

	@Override
	public int[][] getInstanceIDs() {
		return this.INSTANCE_IDs;
	}

	@Override
	public long getRunTime() {
		return (MILLISECONDS_IN_TEN_MINUTES * RUN_TIME_IN_SECONDS) / 600;
	}

}