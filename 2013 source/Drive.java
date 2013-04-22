import lejos.nxt.*;
import lejos.robotics.navigation.*;
import java.io.File;
import java.util.*;

public class Drive {
	public static void main(String args[]){
		while(true){
			Motor.A.forward();
			Motor.C.forward();
		}
	}
}
