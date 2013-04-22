import lejos.nxt.*;
import lejos.robotics.Colors.Color;

public class SensorTest {
	public static void main(String[] args) 
	{
  		ColorLightSensor cs1 = new ColorLightSensor(SensorPort.S4, ColorLightSensor.TYPE_COLORGREEN);
  		ColorLightSensor cs2 = new ColorLightSensor(SensorPort.S3, ColorLightSensor.TYPE_COLORGREEN);

		while(true) {
			System.out.println("L:" + (cs1.readValue()) + " R:" + (cs2.readValue()));
		}
	}
}
