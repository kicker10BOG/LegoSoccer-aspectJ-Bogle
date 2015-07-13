/* Programmer:	Taylor Harvin
 * Purpose:		This controls the basic motion of a soccer robot
 * 
 */
package simpleSoccer;

import lejos.hardware.motor.*;
import lejos.hardware.port.Port;
import lejos.robotics.DCMotor;
import lejos.utility.Delay;

public class SoccerMotorMotion {
	
	// Speed constants
	public static int FAST = 700;
	public static int MEDIUM = 525;
	public static int SLOW = 125;
	
	public final float baseArmPos;
	public float curArmPos;

	
	// Motor associations
	public EV3LargeRegulatedMotor leftMotor;
	public EV3LargeRegulatedMotor rightMotor;
	public EV3MediumRegulatedMotor armMotor;
	
	public SoccerMotorMotion(Port left, Port right, Port arm){
		leftMotor = new EV3LargeRegulatedMotor(left);
		rightMotor = new EV3LargeRegulatedMotor(right);
		armMotor = new EV3MediumRegulatedMotor(arm);
		baseArmPos = armMotor.getPosition();
		curArmPos = baseArmPos;
	}
	
	public void goForward(int speed){
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.forward();
	}
	
	public void goBackward(int speed){
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.backward();
		rightMotor.backward();
	}
	
	public void turnLeft(int speed){
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.backward();
		rightMotor.forward();
	}
	
	public void turnRight(int speed){
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.backward();
	}
	
	public void killMotors(){
		leftMotor.stop();
		rightMotor.stop();
	}
	
	public void haltMotionMotors(){
		leftMotor.flt();
		rightMotor.flt();
	}
	
	public void stop(){
		haltMotionMotors();
		leftMotor.close();
		rightMotor.close();
	}
	
	public void hitBall(){
		goForward(300);
		Delay.msDelay(700);
		armMotor.setSpeed(7000);
		armMotor.rotate(-120);
		//returnArm();
	}
	
	public void closeArm(){
		armMotor.setSpeed(7000);
		armMotor.rotate(100);
	}
	
	public void openArmWithBall(){
		armMotor.setSpeed(7000);
		armMotor.rotate(-45);
		//Delay.msDelay(2000);
		System.out.println(armMotor.getPosition());
	}
	
	
	
	public void grabBall(){
		//System.out.println(baseArmPos);
		//Delay.msDelay(2000);
		armMotor.setSpeed(7000);
		armMotor.rotate(45);
		Delay.msDelay(100);
		curArmPos = armMotor.getPosition();
		//Delay.msDelay(2000);
		
	}
	
	
	public void aimKick(){
		this.haltMotionMotors();
		goBackward(SoccerMotorMotion.SLOW);
		Delay.msDelay(500);
		this.haltMotionMotors();
		openArmWithBall();
		Delay.msDelay(1000);
		goBackward(SoccerMotorMotion.SLOW);
		Delay.msDelay(1200);
		this.haltMotionMotors();
		closeArm();
	}
	
}
