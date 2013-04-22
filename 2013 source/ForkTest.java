import lejos.nxt.*;
import lejos.robotics.navigation.*;
import java.io.File;
import java.util.*;

public class ForkTest {
	public static void main(String[] args) {
			Pilot pilot = new TachoPilot(1.9527f, 4.9f, Motor.A, Motor.C);
			IntStack stackx = new IntStack(56);
			IntStack stacky = new IntStack(56);
			int curx, cury = 0;
			int face = 2;// 0 is north, 1 east, 2 south, 3 west
			boolean found = false;
			int DIST = 12;
			float BACKUP = -3.5f;
			float FORWARD = 8.6f;
			int DEG = 90;
			int THRESH = 37;
			int UTHRESH = 26;

			ColorLightSensor cs1 = new ColorLightSensor(SensorPort.S3, ColorLightSensor.TYPE_COLORGREEN); //Left
			ColorLightSensor cs2 = new ColorLightSensor(SensorPort.S4, ColorLightSensor.TYPE_COLORGREEN); //Right

			//Motor fork = new Motor(Motor.B);



			pilot.rotate(-45);

			Motor.B.setSpeed(720);
			Motor.B.forward();
			
			pause(2000);
			Motor.B.stop();

			pilot.travel(6);

			Motor.B.setSpeed(100);

			Motor.B.rotate(-160);

			pilot.travel(-6);

			pilot.rotate(45);
	}

	public void flagAlign() {
		/*pilot.setMoveSpeed(4);
		turn(2);
		pilot.travel(DIST, true);
		while (pilot.isMoving()) {
			if (cs1.readValue() > THRESH) {  
				pilot.stop();
				align();
				pilot.travel(BACKUP);
			}
			if (cs2.readValue() > THRESH) {
				pilot.stop();
				align();
				pilot.travel(-3);
			}
		}
		turn(3);
		pilot.travel(DIST, true);
		while (pilot.isMoving()) {
			if (cs1.readValue() > THRESH) {
				pilot.stop();
				align();
				pilot.travel(BACKUP);
			}
			if (cs2.readValue() > THRESH) {
				pilot.stop();
				align();
				pilot.travel(-3);
			}
		}*/


	}

	public static void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}
}
