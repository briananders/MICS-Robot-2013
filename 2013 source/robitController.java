import lejos.nxt.*;
import lejos.robotics.navigation.*;
import java.io.File;
import java.util.*;

public class robitController {
	Pilot pilot = new TachoPilot(1.9527f, 4.9f, Motor.A, Motor.C);
	IntStack stackx = new IntStack(56);
	IntStack stacky = new IntStack(56);
	int curx, cury = 0;
	int face = 2;// 0 is north, 1 east, 2 south, 3 west
	boolean found = false;
	int DIST = 12;
	float BACKUP = -3.7f;
	float FORWARD = 8.5f;
	int DEG = 90;
	int THRESH = 29;
	int UTHRESH = 26;

	ColorLightSensor csRight = new ColorLightSensor(SensorPort.S3, ColorLightSensor.TYPE_COLORGREEN); //Left
	ColorLightSensor csLeft = new ColorLightSensor(SensorPort.S4, ColorLightSensor.TYPE_COLORGREEN); //Right
	
    UltrasonicSensor usLeft = new UltrasonicSensor(SensorPort.S1); //Left
	UltrasonicSensor usRight = new UltrasonicSensor(SensorPort.S2); //Right

	public void startAlign() {
		pilot.setMoveSpeed(4);
		turn(3);
		pilot.travel(DIST, true);
		while (pilot.isMoving()) {
			if ((csLeft.readValue()) > THRESH) {  
				pilot.stop();
				align();
				pilot.travel(BACKUP);
			}
			if ((csRight.readValue()) > (THRESH+2)) {
				pilot.stop();
				align();
				pilot.travel(-3);
			}
		}
		turn(0);
		pilot.travel(DIST, true);
		while (pilot.isMoving()) {
			if ((csLeft.readValue()) > THRESH) {
				pilot.stop();
				align();
				pilot.travel(BACKUP);
			}
			if ((csRight.readValue()) > (THRESH+2)) {
				pilot.stop();
				align();
				pilot.travel(-3);
			}
		}
		turn(1);
		pilot.setMoveSpeed(4);
	}

	public void align() {
		pilot.stop();
		pilot.setMoveSpeed(4); //was at 5

		if ((csLeft.readValue()) < THRESH) //left sensor sees black
			Motor.A.forward(); //left motor goes forward
		else
			Motor.C.forward(); //right sensor must be on black, so right motor goes forward.

		while ((csLeft.readValue()) < THRESH || (csRight.readValue()) < (THRESH+2)) {

			if ((csLeft.readValue()) > THRESH) 	//sensor sees white
				Motor.A.stop();				//corresponding motor stops
			//if ((csLeft.readValue()) < THRESH)   //sensor sees black
				//Motor.A.forward();			//corresponding motor drives

			if ((csRight.readValue()) > (THRESH+2)) 	//sensor sees white
				Motor.C.stop();				//corresponding motor stops
			//if ((csRight.readValue()) < THRESH)	//sensor sees black
				//Motor.C.forward();			//corresponding motor drives

			if (!Motor.A.isMoving() && !Motor.C.isMoving())
				break;
		}

		pilot.stop();

		//reverse align maybe

		pilot.setMoveSpeed(4);
	}

	public void next() {
		pilot.travel(FORWARD, false);
		changeCoor();
		found = isFound();
	}

	public void backup() {
		pilot.travel((float) -1.5);
	}

	public boolean move() {
		if(usLeft.getDistance() < UTHRESH || usRight.getDistance() < UTHRESH)
			return true;
		pilot.travel(DIST, true);
		while (pilot.isMoving()) {
			if ((csLeft.readValue()) > THRESH || (csRight.readValue()) > (THRESH+2)) {
				pilot.stop();
				break;
				//Found white line, travel through and mark as door below.
			} 
		}
		stackx.push(getX());
		stacky.push(getY());

		pilot.stop();
		//pause(100);
		return false; // false, we will mark a door.
	}

	public void moveBack() {
		pilot.travel(DIST, true);
		while (pilot.isMoving()) {
			if ((csLeft.readValue()) > THRESH || (csRight.readValue()) > (THRESH+2)) {
				pilot.stop();
				break;
			}
		}
		align();
		next();
	}

	public void turn(int dir) {
		float rotate = (getFace() - dir) * DEG;
		if (rotate == 270)
			rotate = -90;
		if (rotate == -270)
			rotate = 91; // left turns are made at 92 instead of 90
		if (rotate == 90)
			rotate = 91; // again left turns more to compensate.
		if (rotate == -90)
			rotate = -90;
		if (rotate == -180)
			rotate = -178;
		if (rotate == 180)
			rotate = 180; // one last time for left hand 180s.
		pilot.rotate(rotate);
		setFace(dir);
	}

	public void changeCoor() {
		switch (getFace()) {
		case 0:
			curx += -1;
			break;
		case 2:
			curx += 1;
			break;
		case 1:
			cury += 1;
			break;
		case 3:
			cury += -1;
			break;
		}
	}

	public int intFace(int difx, int dify) {
		if (difx == -1)
			return 2; // south
		if (difx == 1)
			return 0; // north
		if (dify == -1)
			return 1; // east
		if (dify == 1)
			return 3; // west
		return getFace();
	}

	public void backtrack() {

		if(getFace() == 0)
			System.out.println("North -> (" + getX() + ", " + getY() + ")");
		else if(getFace() == 1)
			System.out.println("East ->  (" + getX() + ", " + getY() + ")");
		else if(getFace() == 2)
			System.out.println("South -> (" + getX() + ", " + getY() + ")");
		else if(getFace() == 3)
			System.out.println("West ->  (" + getX() + ", " + getY() + ")");
		else
			System.out.println("LOST ->  (" + getX() + ", " + getY() + ")");

		int difx = getX() - stackx.pop();
		int dify = getY() - stacky.pop();
		int dir = intFace(difx, dify);
		if (dir != getFace()) {
			turn(intFace(difx, dify));
		}
		moveBack();
	}

	public void findSing() {
		File file = new File("OneUp.wav");
		Sound.setVolume(100);
		Sound.playSample(file);
	}

	public void startSing() {
		File file = new File("R2D2.wav");
		Sound.setVolume(100);
		Sound.playSample(file);
	}

	public void endSing() {
		File file = new File("StageClear.wav");
		Sound.setVolume(100);
		Sound.playSample(file);
	}

	public void winningSing() {
		File file = new File("Coin.wav");
		Sound.setVolume(100);
		Sound.playSample(file);
	}

	public void gohome() {
		// DIST = 11;
		while (!stackx.isEmpty() && !stacky.isEmpty()) {
			backtrack();
			//winningSing();
		}
	}

	public int getX() {
		return curx;
	}

	public int getY() {
		return cury;
	}

	public int getFace() {
		return face;
	}

	public void setFace(int dir) {
		face = dir;
	}

	public boolean isFound() {
		if (getX() == 6 && getY() == 5)
			return true;
		return false;
	}

	public boolean isHome() {
		if (getX() == 0 && getY() == 0)
			return true;
		return false;
	}

	public void spin() {
		pilot.rotate(810);
	}

	public static void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	public void checkHome() {
		turn(0);

		pilot.travel(DIST, true);
		while (pilot.isMoving()) {
			if ((csLeft.readValue()) > THRESH) {
				pilot.stop();
				align();
				pilot.travel(BACKUP);
			}
			if ((csRight.readValue()) > (THRESH+2)) {
				pilot.stop();
				align();
				pilot.travel(BACKUP);
			}
		}

		turn(3);

		pilot.travel(DIST, true);
		while (pilot.isMoving()) {
			if ((csLeft.readValue()) > THRESH) {
				pilot.stop();
				align();
				pilot.travel(BACKUP);
			}
			if ((csRight.readValue()) > (THRESH+2)) {
				pilot.stop();
				align();
				pilot.travel(BACKUP);
			}
		}

	}

	public void cleanStack() {
		int[] xVals = stackx.getArray();
		int[] yVals = stacky.getArray();

		int xsize = stackx.size();
		int ysize = stacky.size();

		for (int i = 0; i < xsize - 1; i++) {
			for (int j = i + 1; j < xsize; j++) {
				if ((xVals[i] == xVals[j]) && (yVals[i] == yVals[j])) {
					for (int k = 0; k < xsize; k++) {
						xVals[i + k] = xVals[j + k];
						yVals[i + k] = yVals[j + k];
						xsize--;
					}
					i = 0;
				}
			}
		}

		for (int i = 1; i < ysize; i++) {
			if (xVals[i] == 0 && yVals[i] == 0) {
				xsize = i - 1;
				break;
			}
		}

		if (xVals[xsize] == 0 && yVals[xsize] == 0)
			xsize--;

		stackx.setArray(xVals, xsize + 1);
		stacky.setArray(yVals, xsize + 1);
	}

	public void getFlag() {
		turn(2);
		pilot.travel(DIST, true);
		while (pilot.isMoving()) {
			if ((csLeft.readValue()) > THRESH) {  
				pilot.stop();
				align();
				pilot.travel(BACKUP);
			}
			if ((csRight.readValue()) > (THRESH+2)) {
				pilot.stop();
				align();
				pilot.travel(-4);
			}
		}
		turn(1);
		pilot.travel(DIST, true);
		while (pilot.isMoving()) {
			if ((csLeft.readValue()) > THRESH) {
				pilot.stop();
				align();
				pilot.travel(BACKUP);
			}
			if ((csRight.readValue()) > (THRESH+2)) {
				pilot.stop();
				align();
				pilot.travel(-4);
			}
		}

		pilot.rotate(-45);

		Motor.B.setSpeed(720);
		Motor.B.forward();
		
		pause(2000);
		Motor.B.stop();

		Motor.B.rotate(-35);

		pilot.travel(6);

		Motor.B.setSpeed(100);

		Motor.B.rotate(-160);

		pilot.travel(-5);

		pilot.rotate(45);
	}

}
