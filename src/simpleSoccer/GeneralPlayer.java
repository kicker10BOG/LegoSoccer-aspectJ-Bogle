package simpleSoccer;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;
import lejos.hardware.sensor.*;

public class GeneralPlayer {
	public SoccerMotorMotion roboMotor;
	public HiTechnicCompass mainCompass;
	//public EV3GyroSensor mainCompass;
	public final SensorMode baseDir;
	public float [] vals;
	public SampleProvider averageDir;
	public float goalLocation = 0;
	public BallFinder ballFinder;
	public GoalFinder goalFinder;
	public ColorDetector colorDetector;
	
	
	//public EV3LargeRegulatedMotor a;
	
	
	public GeneralPlayer(){
		roboMotor = new SoccerMotorMotion(MotorPort.A,MotorPort.D, MotorPort.B);
		System.out.println("Press Enter for Direction");
		Button.ENTER.waitForPress();
		mainCompass = new HiTechnicCompass(SensorPort.S4);
		//mainCompass = new EV3GyroSensor(SensorPort.S4);
		
		baseDir = mainCompass.getCompassMode();
		//baseDir = (SensorMode) mainCompass.getAngleMode();
		goalFinder = new GoalFinder(baseDir,roboMotor);
		goalFinder.setGoalLocation();
		/*Delay.msDelay(1000);
		System.out.println("Turn");
		Delay.msDelay(3000);
		goalFinder.turnToGoal();*/
		ballFinder = new BallFinder(goalFinder,SensorPort.S3,SensorPort.S2,roboMotor);
		colorDetector = new ColorDetector(SensorPort.S1);
		//baseDir.fetchSample(vals, 0);
		//System.out.println(vals[0]);
		/*averageDir = new MeanFilter(baseDir,5);
		vals = new float[averageDir.sampleSize()];
		averageDir.fetchSample(vals, 0);*/
		//setBaseDirection();
		
	}
	
	public void setBaseDirection(){
		System.out.println("Hit Enter\nfor Direction");
		Button.waitForAnyPress();
		averageDir.fetchSample(vals, 0);
		goalLocation = vals[0];
		if(goalLocation+120 > 360){
			goalLocation -= 360;
		}
		System.out.println("Move Dir");
		Delay.msDelay(4000);
		/*ballFinder = new BallFinder(SensorPort.S1, roboMotor);
		ballFinder.start();*/
	}
	
	
	public void start(){
		
		// Wonder (to find ball)
		//ballFinder.searchForBall();
		// ball found, so go to the ball
		ballFinder.goToBall();
		// Turn to the goal (forward)
		goalFinder.turnToGoal();
		// Go to the goal ( go forward until red or green are hit)
		roboMotor.goForward(SoccerMotorMotion.FAST);
		//Delay.msDelay(1500);
		while(!colorDetector.inShootingRange() && !ballFinder.touchActivated()){
			Delay.msDelay(50);
		}
		roboMotor.haltMotionMotors();
		// Hit the ball
		roboMotor.aimKick();
		//goalFinder.turnToGoal();
		roboMotor.hitBall();
		// Go back to wondering for the ball
		//ballFinder.searchForBall();
		
		/***********OTHER TEST***************/
		//Delay.msDelay(1500);
		/*Boolean keep_looking = true;
		//setBaseDirection();
		roboMotor.turnLeft(SoccerMotorMotion.FAST);
		
		
		averageDir.fetchSample(vals, 0);
		goalLocation = vals[0];
		float baseDirLeft = goalLocation + 20;
		
		float baseDirRight = goalLocation - 20;
		
		do{
			averageDir.fetchSample(vals, 0);
			
			System.out.println(vals[0]);
			Delay.msDelay(50);
			if(Button.ENTER.isDown())
				keep_looking = false;
		}while(vals[0] < baseDirLeft && keep_looking);
		
		//roboMotor.stop();
		keep_looking = true;
		do{
			averageDir.fetchSample(vals, 0);
			roboMotor.turnRight(SoccerMotorMotion.MEDIUM);
			System.out.println(vals[0]);
			Delay.msDelay(50);
			if(Button.ENTER.isDown())
				keep_looking = false;
		}while(vals[0] > goalLocation && keep_looking);
		// Turn 90 degrees left
		roboMotor.stop();
		System.out.println("done");
		System.out.println(goalLocation);
		Delay.msDelay(10000);
		//roboMotor.stop();*/
	}
}
