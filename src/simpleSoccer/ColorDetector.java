package simpleSoccer;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;

public class ColorDetector {
	public EV3ColorSensor colorSensor;
	public SampleProvider colorDataProvider;
	public float[] colorSample;
	
	public ColorDetector(Port colorSensorPort) {
		colorSensor = new EV3ColorSensor(colorSensorPort);
		colorDataProvider = colorSensor.getColorIDMode();
		colorSample = new float[colorDataProvider.sampleSize()];
	}
	
	public boolean inShootingRange(){
		colorDataProvider.fetchSample(colorSample, 0);
		if(colorSample[0] == Color.GREEN /*|| colorSample[0] == Color.RED*/)
			return true;
		else
			return false;
	}

}
