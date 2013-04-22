public class mach1 {
	public static void main(String[] args) {
		robitController robot = new robitController();
		maze aMaze = new maze();

		robot.startSing();
		robot.startAlign();

		System.out.println("Found: " + robot.isFound());

		while (!robot.isFound()) {
			if(robot.getFace() == 0)
				System.out.println("North -> (" + robot.getX() + ", " + robot.getY() + ")");
			else if(robot.getFace() == 1) 
				System.out.println("East ->  (" + robot.getX() + ", " + robot.getY() + ")");
			else if(robot.getFace() == 2)
				System.out.println("South -> (" + robot.getX() + ", " + robot.getY() + ")");
			else if(robot.getFace() == 3)
				System.out.println("West ->  (" + robot.getX() + ", " + robot.getY() + ")");
			else
				System.out.println("LOST ->  (" + robot.getX() + ", " + robot.getY() + ")");


			if (robot.move()) // Wall
			{
				aMaze.addWall(robot.getX(), robot.getY(), robot.getFace());
				//robit.backup();
				int turnval = aMaze.nextDir(robot.getX(), robot.getY(), robot.getFace());
				while (turnval == -1) {
					robot.backtrack();
					turnval = aMaze.nextDir(robot.getX(), robot.getY(), robot.getFace());
				}
				if (turnval != robot.getFace())
					robot.turn(turnval);
			} else { // DOOR
				aMaze.addDoor(robot.getX(), robot.getY(), robot.getFace());
				robot.align();
				robot.next();
				if (robot.isFound())
					break;
				int turnval = aMaze.nextDir(robot.getX(), robot.getY(), robot.getFace());
				while (turnval == -1) {
					robot.backtrack();
					turnval = aMaze.nextDir(robot.getX(), robot.getY(), robot.getFace());
				}
				if (turnval != robot.getFace()) {
					robot.turn(turnval);
				}
			}
		}


		robot.getFlag();
		robot.findSing();

		System.out.println("WOAH! WE'RE HALFWAY THERE");
		robot.cleanStack();

		while (!robot.isHome()) {
			robot.gohome();
		}

		robot.checkHome();
		robot.endSing();
		System.out.println("WE ARE AWESOME!");
		robot.spin();
	}

}
