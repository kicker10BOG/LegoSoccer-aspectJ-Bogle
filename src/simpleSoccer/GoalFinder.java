package simpleSoccer;

import lejos.hardware.Button;
//import lejos.hardware.port.Port;
//import lejos.hardware.sensor.HiTechnicCompass;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class GoalFinder {
	public final SensorMode baseDir;
	public SoccerMotorMotion roboMotor;
	public float [] vals;
	public SampleProvider averageDir;
	public float goalLocation = 0;
	public float goalLocationLeftMax = 0;
	public float goalLocationRightMax = 0;
	
	public SensorMode getBaseDir() {
		return baseDir;
	}

	public float[] getVals() {
		return vals;
	}

	public SampleProvider getAverageDir() {
		return averageDir;
	}

	public float getGoalLocation() {
		return goalLocation;
	}

	public float getGoalLocationLeftMax() {
		return goalLocationLeftMax;
	}

	public float getGoalLocationRightMax() {
		return goalLocationRightMax;
	}

	public GoalFinder(SensorMode compassMode,SoccerMotorMotion motor) {
		baseDir = compassMode;
		roboMotor = motor;
		averageDir = new MeanFilter(baseDir,5);
		vals = new float[averageDir.sampleSize()];
		averageDir.fetchSample(vals, 0);
	}
	
	
	// Calibrate the goal location for the compass sensor
	public void setGoalLocation(){
		System.out.println("Set Goal Direction");
		Button.waitForAnyPress();
		averageDir.fetchSample(vals, 0);
		goalLocation = vals[0];
		
		/*
		if(goalLocation+120 > 360){
			goalLocation -= 360;
		}*/
		
		goalLocationLeftMax = goalLocation + 1;
		
		// Normalize the left goal range max 0 to 360
		if(goalLocationLeftMax > 360)
			goalLocationLeftMax -= 360;
		
		goalLocationRightMax = goalLocation - 1;
		// Normalize the right goal range max 0 to 360
		if(goalLocationRightMax < 0)
			goalLocationRightMax += 360;
		
		
		
		/*System.out.println("Move Dir");
		Delay.msDelay(4000);*/
	}
	
	// Aim the robot towards the goal
	public void turnToGoal(){
		Boolean keepLooking = true;
		averageDir.fetchSample(vals, 0);
		float dirDiff1 = goalLocation-vals[0];
		float dirDiff2 = vals[0]-goalLocation;
		if(dirDiff1 < 0)
			roboMotor.turnLeft(200);
		else
			roboMotor.turnRight(200);
		
		// Turn the robot until the front of the robot is in the direction
		// of the goal
		do{
			Delay.msDelay(50);
			averageDir.fetchSample(vals, 0);
			//System.out.println(vals[0]);
			if(Button.ENTER.isDown())
				keepLooking = false;
		}while((vals[0] > goalLocationLeftMax || vals[0] < goalLocationRightMax) && keepLooking);
		roboMotor.haltMotionMotors();
		System.out.println("Goal Dir Set:\n"+goalLocation);
		System.out.println("Left Max:\n"+goalLocationLeftMax);
		System.out.println("Right Max:\n"+goalLocationRightMax);
		System.out.println("Final:\n"+vals[0]);
		System.out.println("Final:\n"+vals.toString());
		
	}
	
}
