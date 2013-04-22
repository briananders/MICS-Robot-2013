import lejos.nxt.*;
import lejos.robotics.Colors.Color;

public class UltrasonicTest {
	public static void main(String[] args) 
	{
  		//ColorLightSensor cs1 = new ColorLightSensor(SensorPort.S1, ColorLightSensor.TYPE_COLORGREEN);
  		//ColorLightSensor cs2 = new ColorLightSensor(SensorPort.S2, ColorLightSensor.TYPE_COLORGREEN);
  		//ColorLightSensor cs3 = new ColorLightSensor(SensorPort.S3, ColorLightSensor.TYPE_COLORGREEN);

  		UltrasonicSensor us1 = new UltrasonicSensor(SensorPort.S1);
		UltrasonicSensor us2 = new UltrasonicSensor(SensorPort.S2);

		while(true) {
			//System.out.println("S1:" + cs1.readValue() + " S2:" + cs2.readValue() + " S3:" + cs3.readValue());
			System.out.println("L:" + us1.getDistance() + " R:" + us2.getDistance());
		}
	}
}
