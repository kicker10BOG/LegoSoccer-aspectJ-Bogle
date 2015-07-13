package simpleSoccer.aspects;

public aspect Aspects {
	pointcut hasBall(): 
		this(Ballfinder bf) 
		&& get(bf.distance) 
		&& get(bf.ballInFrontDitance) 
		&& if(bf.distance < bf.ballInFrontDistance);
	
	pointcut ballLocated():
		this(Ballfinder bf)
}
