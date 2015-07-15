package simpleSoccer.aspects;

public aspect Aspects {
	pointcut hasBall(BallFinder bf): 
		this(bf) 
		&& get(bf.getDistance()) 
		&& get(bf.getBallInFrontDitance()) 
		&& if(bf.getDistance() < bf.getBallInFrontDistance());
	
	pointcut ballLocated(BallFinder bf):
		this(bf)
		&& if(!bf.isBallfound());
	
	pointcut ballInFront(BallFinder bf):
		this(bf); // Needs some work for sure
	
	pointcut facingGoal(GoalFinder gf):
		this(gf)
		&& if(gf.getVals[0] > gf.getGoalLocationLeftMax() && gf.getVals[0] < gf.getGoalLocationRigtMax());
}
