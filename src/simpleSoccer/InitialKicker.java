/* Programmer: Taylor Harvin
 * Purpose:	   This is the initial kicker for the temp soccer project.
 */
package simpleSoccer;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.HiTechnicCompass;
import lejos.utility.Delay;


public class InitialKicker {
	public final int AT_BALL = 0;
	public SoccerMotorMotion roboMotor;
	public BallFinder ballFinder;
	public GoalFinder goalFinder;
	public HiTechnicCompass mainCompass;
	
	public InitialKicker(){
		roboMotor = new SoccerMotorMotion(MotorPort.A,MotorPort.D,MotorPort.B);
		
		//goalFinder = new GoalFinder();
		ballFinder = new BallFinder(goalFinder,SensorPort.S3,SensorPort.S2,roboMotor);
		ballFinder.goToBall();
	}
	
	public void start(){
		Delay.msDelay(1500);
		roboMotor.goForward(SoccerMotorMotion.FAST);
		Delay.msDelay(5000);
		roboMotor.stop();
	}
	
}
