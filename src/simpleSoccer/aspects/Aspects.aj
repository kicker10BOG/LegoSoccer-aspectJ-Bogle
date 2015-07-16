package simpleSoccer.aspects;

import simpleSoccer.BallFinder;
import simpleSoccer.GoalFinder;

public aspect Aspects {
	pointcut hasBall(BallFinder bf): 
		this(bf) 
		&& if(bf.getSample()[0] < bf.getBallInFrontDistance());
	
	pointcut ballLocated(BallFinder bf):
		this(bf)
		&& if(!bf.isBallFound());
	
	pointcut ballInFront(BallFinder bf):
		this(bf); // Needs some work, may require different sensors and code. 
	
	pointcut facingGoal(GoalFinder gf):
		this(gf)
		&& if(gf.getVals()[0] > gf.getGoalLocationLeftMax() 
				&& gf.getVals()[0] < gf.getGoalLocationRightMax());
	
	/*
	 * Other predicates cannot be defined right now, with current sensors and code:
	 *   oppHasBall
	 *   teamHasBall
	 *   shotLaneCovered
	 *   open
	 *   canSeeBall
	 *   teamMateOpen
	 *   betweenBallAndGoal
	 *   oppIsOpen
	 */
}
