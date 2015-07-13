package simpleSoccer;

import java.util.Random;

import lejos.hardware.Button;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.UARTPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class BallFinder {
	//EV3IRSensor irSensor;
	EV3UltrasonicSensor sonarSensor;
	SampleProvider distance;
	SampleProvider touch;
	float[] sample;
	float[] touchSample;
	public SoccerMotorMotion roboMotor;
	public boolean searchMode;
	private boolean keepCheckingForBall = true;
	private boolean ballFound = false;
	private final float ballInFrontDistance = (float) 0.05;
	private final float objectDetectedDistance = (float) 1.5;
	private EV3TouchSensor frontTouch;
	public boolean wallHit = false;
	private boolean atBall = false;
	private boolean goToBall = false;
	private GoalFinder goalFinder;
	
	public BallFinder(GoalFinder newGoalFinder, Port touchPort,Port sonarPort, SoccerMotorMotion newRoboMotor){
		sonarSensor = new EV3UltrasonicSensor(sonarPort);
		distance= sonarSensor.getDistanceMode();
		sample = new float[distance.sampleSize()];
		roboMotor = newRoboMotor;
		searchMode = false;
		frontTouch = new EV3TouchSensor(touchPort);
		touch = frontTouch.getTouchMode();
		touchSample = new float[touch.sampleSize()];
		goalFinder = newGoalFinder;
	}
	
	public void searchForBall(){
		searchMode = true;
		Random rand = new Random();
		int randDir = rand.nextInt(4) + 1;
		while(searchMode){
			switch(randDir){
				case 1:
					roboMotor.goForward(SoccerMotorMotion.FAST);
					break;
				case 2:
					roboMotor.goBackward(SoccerMotorMotion.FAST);
					break;
				case 3:
					roboMotor.turnLeft(SoccerMotorMotion.FAST);
					break;
				case 4:
					roboMotor.turnRight(SoccerMotorMotion.FAST);
					break;
			}
			detectBall();
			Delay.msDelay(1000);
		}
	}
	
	// Object detected -- go to it unless it moves away or a wall is hit
	public void goToBall(){
		searchMode = false;
		float tempSample = 0;
		goToBall = true;
		roboMotor.goForward(SoccerMotorMotion.MEDIUM);
		Delay.msDelay(500);
		// Go close to the ball until
		while(!touchActivated() && goToBall){
			tempSample = fetchSonarVal();
			if(/*tempSample > objectDetectedDistance ||*/ tempSample < ballInFrontDistance )
				goToBall = false;
		}
		// The ball is in front of the robot
		// Grab ball
		if(tempSample < ballInFrontDistance){
			roboMotor.grabBall();
		}
		System.out.println(tempSample);
		System.out.println("End Goto Ball");
		roboMotor.haltMotionMotors();
	}
	
	
	public float fetchSonarVal(){
		distance.fetchSample(sample, 0);
		System.out.println(sample[0]);
		Delay.msDelay(50);
		return sample[0];
	}
	
	public void detectBall(){
		do{
			distance.fetchSample(sample, 0);
			System.out.println(sample[0]);
			Delay.msDelay(50);
			if(Button.ENTER.isDown())
				keepCheckingForBall = false;
			if(sample[0] > 7){
				ballFound = true;
			}
		}while(keepCheckingForBall && !ballFound);
		roboMotor.killMotors();
		searchMode = false;
	}
	
	public boolean touchActivated(){
		touch.fetchSample(touchSample, 0);
		if(touchSample[0] == 1)
			return true;
		else
			return false;
	}
	
	// Start the check for a wall hit
	public void wallHit(){
		wallHit = false;
		//while(!wallHit){
			touch.fetchSample(touchSample, 0);
			if(touchSample[0] == 1){
				wallHit = true;
			}
		//}
	}
	
	
	public void start(){
		Boolean keepChecking = true;
		roboMotor.goForward(SoccerMotorMotion.MEDIUM);
		do{
			distance.fetchSample(sample, 0);
			System.out.println(sample[0]);
			Delay.msDelay(50);
			if(Button.ENTER.isDown())
				keepChecking = false;
		}while(keepChecking && sample[0] > 7);
		roboMotor.goBackward(SoccerMotorMotion.SLOW);
		Delay.msDelay(1000);
		roboMotor.goForward(SoccerMotorMotion.FAST);
		Delay.msDelay(1000);
		roboMotor.hitBall();
		System.out.println("Done");
		roboMotor.stop();
		Delay.msDelay(10000);
		
	}
}
