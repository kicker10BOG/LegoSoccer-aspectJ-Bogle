/* Programmer:	Taylor Harvin
 * Purpose:		Initialize the type of the player (based on button click)
 * 
 */
package simpleSoccer;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.HiTechnicCompass;

public class PlayerTypeInitializer {

	public static void main(String[] args) {
		System.out.println("Press up for\ncalibration");
		int robotChoice = Button.waitForAnyPress();
		if(robotChoice == Button.ID_UP){
			HiTechnicCompass mainCompass;
			mainCompass = new HiTechnicCompass(SensorPort.S4);
			mainCompass.startCalibration();
			Button.waitForAnyPress();
			mainCompass.stopCalibration();
			mainCompass.close();
		}
		
		LCD.clear();
		System.out.println("Choose Player");
		robotChoice = Button.waitForAnyPress();
		switch(robotChoice){
			case Button.ID_UP:
				System.out.println("Initial Kicker");
				InitialKicker robotKicker = new InitialKicker();
				//robotKicker.start();
				break;
			case Button.ID_ENTER:
				System.out.println("General Player");
				GeneralPlayer robotGeneral = new GeneralPlayer();
				robotGeneral.start();
				break;
		}
		//InitialKicker robot = new InitialKicker();
		//robot.start();
	}

}
