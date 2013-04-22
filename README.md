2011-2012 Solutions by Brian Anders and Alex Cash
2013 Solution by Brian Anders

This is the code that we used to solve these problems:

http://www2.css.edu/mics/RoboMazeMICS_2011.pdf                                   
http://www.micsymposium.org/mics2012/Robot_grid_world_2012.pdf                
http://www.cs.uwlax.edu/mics2013/RobotContestDraft.pdf

We have a strong algorithm in this code that, when calibrated properly, could solve every maze that we set up. If you adjust the size of the maze in the code, this algorithm will solve any sized maze.

At MICS 2011, we won 2nd place. The first place robot had a much more simplistic design and moved faster than ours did. Sadly, 1st place could only solve mazes without islands and used the righthand rule. Our algorithm is much more robust.

At MICS 2012, we had a bug in our code in the "cleanStack()" method. This bug has since been fixed. As a result, our robot could get to the goal, but could not drive home.

At MICS 2013, the robot placed 2nd again. The first place robot set up their robot so that it would drive directly into my robot so that I would restart. Their robot only drove in a straight line and did not even attempt to solve the maze. 

The robot uses lejos 0.8.5 beta. We used the pilot class to do the driving and turning calculations. 

MotorTest, UltrasonicTest, ForkTest, and SensorTest are all main() files that are used to print the readings of the sensors or to test a vital function in the program. Use them to calibrate your motors and sensor to where they match pretty closely. Reading close values is important to accuracy.

Instack.java courtesy of university of colorado: cs.colorado.edu. More documentation
commented within the file.