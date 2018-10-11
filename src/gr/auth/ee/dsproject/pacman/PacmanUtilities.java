/**
 *
 */
package gr.auth.ee.dsproject.pacman;

import java.util.ArrayList;
import java.util.Vector;

/**
 * <p>
 * Title: DataStructures2006
 * </p>
 * 
 * <p>
 * Description: Data Structures project: year 2011-2012
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * <p>
 * Company: A.U.Th.
 * </p>
 * 
 * @author Michael T. Tsapanos
 * @version 1.0
 */
public class PacmanUtilities {

	// public static final int visionSize = 2;
	public static final int windowWIDTH = 600;
	public static final int windowHEIGHT = 500;
	public static final int numberOfRows = 20;
	public static final int numberOfColumns = 25;
	public static final boolean boarders = false;
	public static final boolean colisionDetection = true;
	public static final int numberOfGhosts = 4;
	public static final int numberOfFlags = 4;
	public static final boolean borders = false;
	// public static final int minDistanceFromExit = 50;
	public static final int minDistanceFromPrey = 10;
	public static final int maxDistanceFromPrey = 15;
	public static final int stepLimit = 1000;
	public static final int smellDistance = 26;
	public static final int timeStep = 100;

	public static int[] preyPosition;
	public static int[][] predatorPosition;

	public static int[][] setRandomInitialPositions(Room[][] labyrinth,
			int minDistanceFromPrey, int maxDistanceFromPrey, int numOfGhosts) {
		int[] preyPos = new int[2];
		int[][] predatorPos = new int[numOfGhosts][2];
		int[][] preyAndPredator = new int[numOfGhosts + 1][2];

		int rows = labyrinth.length;
		int columns = labyrinth[0].length;

		preyPos[0] = (int) (rows * Math.random());

		preyPos[1] = (int) (columns * Math.random());

		for (int i = 0; i < numOfGhosts; i++) {
			predatorPos[i][0] = (int) (rows * Math.random());
			predatorPos[i][1] = (int) (columns * Math.random());

		}

		int minPath = rows * columns;
		/*
		 * for (int i = 0; i < numberOfExits; i++) { int[] tempPath =
		 * LabyrinthUtilities.findPathToPoint(labyrinth, preyPos, exits[i]); if
		 * (tempPath.length < minPath) { minPath = tempPath.length; } } while
		 * (minPath < distanceFromExit) { preyPos[0] = (int) (rows *
		 * Math.random()); preyPos[1] = (int) (columns * Math.random()); minPath
		 * = rows * columns; for (int i = 0; i < numberOfExits; i++) { int[]
		 * tempPath = LabyrinthUtilities.findPathToPoint(labyrinth, preyPos,
		 * exits[i]); if (tempPath.length < minPath) { minPath =
		 * tempPath.length; } } }
		 */

		/*
		 * predatorPos[0] = (int) (rows * Math.random()); predatorPos[1] = (int)
		 * (columns * Math.random());
		 */

		for (int i = 0; i < numOfGhosts; i++) {
			int tempPath = PacmanUtilities.findDistanceToPoint(predatorPos[i],
					preyPos);
			minPath = tempPath;

			boolean colision = false;

			for (int j = i - 1; j >= 0; j--) {

				if (predatorPos[i][0] == predatorPos[j][0]
						&& predatorPos[i][1] == predatorPos[j][1])
					colision = true;

			}

			while (minPath < minDistanceFromPrey
					|| minPath > maxDistanceFromPrey || colision) {
				predatorPos[i][0] = (int) (rows * Math.random());
				predatorPos[i][1] = (int) (columns * Math.random());
				tempPath = PacmanUtilities.findDistanceToPoint(predatorPos[i],
						preyPos);
				minPath = tempPath;

				colision = false;

				for (int j = i - 1; j >= 0; j--) {

					if (predatorPos[i][0] == predatorPos[j][0]
							&& predatorPos[i][1] == predatorPos[j][1])
						colision = true;

				}

			}
		}

		for (int i = 0; i < numOfGhosts; i++) {
			for (int j = 0; j < 2; j++) {
				preyAndPredator[i][j] = predatorPos[i][j];
			}

		}

		preyAndPredator[numOfGhosts][0] = preyPos[0];
		preyAndPredator[numOfGhosts][1] = preyPos[1];

		preyPosition = preyPos;
		predatorPosition = predatorPos;

		return preyAndPredator;
	}

	public static int findDistanceToPoint(int[] position, int[] target) {
		int stepDistance;

		stepDistance = Math.abs(position[0] - target[0])
				+ Math.abs(position[1] - target[1]);

		return stepDistance;
	}

	/*
	 * public static int[] findPathToPoint(Room[][] labyrinth, int[] position,
	 * int[] target) { int[] currentPos = new int[2]; int[] nextPos = new
	 * int[2];
	 * 
	 * int columns = labyrinth[0].length; LinkedList<Integer> pathNodes = new
	 * LinkedList<Integer> (); LinkedList<Integer> visitedNodes = new
	 * LinkedList<Integer> (); Stack<Integer> backtrackNodes = new
	 * Stack<Integer> (); currentPos[0] = position[0]; currentPos[1] =
	 * position[1]; while (! (currentPos[0] == target[0] && currentPos[1] ==
	 * target[1])) { int selectRandomDirection = (int) (4 * Math.random());
	 * LinkedList<Integer> selectedDirections = new LinkedList<Integer> ();
	 * boolean found = false; int tries = 0; while (found == false && tries < 4)
	 * { if (!selectedDirections.contains( (Integer) (selectRandomDirection))) {
	 * selectedDirections.add( (Integer) (selectRandomDirection)); tries++; if
	 * (labyrinth[currentPos[0]][currentPos[1]].walls[selectRandomDirection] ==
	 * 1) {
	 * //labyrinth[currentPos[0]][currentPos[1]].border[selectRandomDirection]
	 * == 1 && if (selectRandomDirection == Room.NORTH &&
	 * !visitedNodes.contains( (Integer) ( (currentPos[0] - 1) * columns +
	 * currentPos[1]))) { nextPos[0] = currentPos[0] - 1; nextPos[1] =
	 * currentPos[1]; found = true; } else { if (selectRandomDirection ==
	 * Room.SOUTH && !visitedNodes.contains( (Integer) ( (currentPos[0] + 1) *
	 * columns + currentPos[1]))) { nextPos[0] = currentPos[0] + 1; nextPos[1] =
	 * currentPos[1]; found = true; } else { if (selectRandomDirection ==
	 * Room.EAST && !visitedNodes.contains( (Integer) (currentPos[0] * columns +
	 * (currentPos[1] + 1)))) { nextPos[0] = currentPos[0]; nextPos[1] =
	 * currentPos[1] + 1; found = true; } else { if (selectRandomDirection ==
	 * Room.WEST && !visitedNodes.contains( (Integer) (currentPos[0] * columns +
	 * (currentPos[1] - 1)))) { nextPos[0] = currentPos[0]; nextPos[1] =
	 * currentPos[1] - 1; found = true; } } } } } } else { selectRandomDirection
	 * = (int) (4 * Math.random()); } } //System.out.print("CURRENT POS: " +
	 * currentPos[0] + " " + currentPos[1] + "  "); if (found == false) {
	 * //System.out.print(" (backtracking...) "); pathNodes.remove( (Integer)
	 * (currentPos[0] * columns + currentPos[1])); visitedNodes.add( (Integer)
	 * (currentPos[0] * columns + currentPos[1])); Integer tempID =
	 * backtrackNodes.pop(); nextPos[0] = (int) (tempID.intValue() / columns);
	 * nextPos[1] = (tempID.intValue()) % columns; visitedNodes.remove(
	 * (Integer) (nextPos[0] * columns + nextPos[1])); pathNodes.remove(
	 * (Integer) (nextPos[0] * columns + nextPos[1])); } else {
	 * backtrackNodes.push( (Integer) (currentPos[0] * columns +
	 * currentPos[1])); pathNodes.add( (Integer) (currentPos[0] * columns +
	 * currentPos[1])); visitedNodes.add( (Integer) (currentPos[0] * columns +
	 * currentPos[1])); } currentPos[0] = nextPos[0]; currentPos[1] =
	 * nextPos[1]; //System.out.println("NEXT POS: " + nextPos[0] + " " +
	 * nextPos[1]); }
	 * 
	 * int numberOfNodes = 0; Iterator iter = pathNodes.listIterator(); while
	 * (iter.hasNext()) { iter.next(); numberOfNodes++; } int[] path = new
	 * int[numberOfNodes]; int i = 0; iter = pathNodes.listIterator(); while
	 * (iter.hasNext()) { path[i] = ( (Integer) (iter.next())).intValue(); i++;
	 * } return path; }
	 */

	public static int[] evaluateNextPosition(Room[][] labyrinth,
			int[] position, int direction, boolean boarders) {
		int[] newPosition = new int[2];
		if (direction == Room.NORTH) {
			if (!(position[0] - 1 < 0)) {
				newPosition[0] = position[0] - 1;
				newPosition[1] = position[1];
			} else {
				newPosition[0] = PacmanUtilities.numberOfRows - 1;
				newPosition[1] = position[1];
			}

		}

		if (direction == Room.SOUTH) {
			if (!(position[0] + 1 >= labyrinth.length)) {
				newPosition[0] = position[0] + 1;
				newPosition[1] = position[1];
			} else {
				newPosition[0] = 0;
				newPosition[1] = position[1];
			}

		}

		if (direction == Room.EAST) {
			if (!(position[1] + 1 >= labyrinth[0].length)) {
				newPosition[0] = position[0];
				newPosition[1] = position[1] + 1;
			} else {
				newPosition[0] = position[0];
				newPosition[1] = 0;
			}
		}

		if (direction == Room.WEST) {
			if (!(position[1] - 1 < 0)) {
				newPosition[0] = position[0];
				newPosition[1] = position[1] - 1;
			} else {
				newPosition[0] = position[0];
				newPosition[1] = PacmanUtilities.numberOfColumns - 1;
			}
		}

		return newPosition;
	}

	public static boolean evaluateDirection(Room[][] labyrinth, int[] position,
			int direction, boolean boarders) {
		boolean validChoice = true;

		if (direction == Room.NORTH) {
			if (position[0] - 1 < 0 && boarders) {
				validChoice = false;
			} else {
				if (labyrinth[position[0]][position[1]].walls[Room.NORTH] == 0) {
					// System.out.println("WALL!!");
					validChoice = false;
				}
			}
		} else {
			if (direction == Room.SOUTH) {
				if (position[0] + 1 >= labyrinth.length && boarders) {
					validChoice = false;
				} else {
					if (labyrinth[position[0]][position[1]].walls[Room.SOUTH] == 0) {
						// System.out.println("WALL!!");
						validChoice = false;
					}
				}
			} else {
				if (direction == Room.EAST) {
					if (position[1] + 1 >= labyrinth[0].length && boarders) {
						validChoice = false;
					} else {
						if (labyrinth[position[0]][position[1]].walls[Room.EAST] == 0) {
							// System.out.println("WALL!!");

							validChoice = false;
						}
					}
				} else {
					if (direction == Room.WEST) {
						if (position[1] - 1 < 0 && boarders) {
							validChoice = false;
						} else {
							if (labyrinth[position[0]][position[1]].walls[Room.WEST] == 0) {
								// System.out.println("WALL!!");
								validChoice = false;
							}
						}
					}
				}
			}
		}
		return validChoice;
	}

	public static boolean detectColision(int[][] prevPos, int[] direction,
			Room[][] lab, int i, boolean borders) {
		boolean validChoice = true;

		int curPosIx = prevPos[i][0];
		int curPosIy = prevPos[i][1];

		if (direction[i] == Room.NORTH) {

			if (curPosIx > 0)
				curPosIx = curPosIx - 1;
			else
				curPosIx = PacmanUtilities.numberOfRows - 1;
		}
		if (direction[i] == Room.SOUTH) {
			if (curPosIx < PacmanUtilities.numberOfRows - 1)
				curPosIx = curPosIx + 1;
			else
				curPosIx = 0;
		}
		if (direction[i] == Room.EAST) {
			if (curPosIy < PacmanUtilities.numberOfColumns - 1)
				curPosIy = curPosIy + 1;
			else
				curPosIy = 0;
		}
		if (direction[i] == Room.WEST) {
			if (curPosIy > 0)
				curPosIy = curPosIy - 1;
			else
				curPosIy = PacmanUtilities.numberOfColumns - 1;
		}

		for (int j = 0; j < PacmanUtilities.numberOfGhosts; j++) {
			if (j != i) {

				/*
				 * System.out.println(i); System.out.println(j);
				 * System.out.println(direction[i]);
				 * System.out.println(direction[j]);
				 * System.out.println(prevPos[i][0]+"  "+prevPos[i][1]);
				 * System.out.println(prevPos[j][0]+"  "+prevPos[j][1]);
				 */

				int curPosJx = prevPos[j][0];
				int curPosJy = prevPos[j][1];

				if (direction[j] == Room.NORTH) {

					if (curPosJx > 0)
						curPosJx = curPosJx - 1;
					else
						curPosJx = PacmanUtilities.numberOfRows - 1;
				}
				if (direction[j] == Room.SOUTH) {
					if (curPosJx < PacmanUtilities.numberOfRows - 1)
						curPosJx = curPosJx + 1;
					else
						curPosJx = 0;
				}
				if (direction[j] == Room.EAST) {
					if (curPosJy < PacmanUtilities.numberOfColumns - 1)
						curPosJy = curPosJy + 1;
					else
						curPosJy = 0;
				}
				if (direction[j] == Room.WEST) {
					if (curPosJy > 0)
						curPosJy = curPosJy - 1;
					else
						curPosJy = PacmanUtilities.numberOfColumns - 1;
				}

				// System.out.println(curPosIx);
				// System.out.println(curPosIy);
				// System.out.println(curPosJx);
				// System.out.println(curPosJy);
				// System.out.println(" ");

				if (curPosIx == curPosJx && curPosIy == curPosJy) {
					validChoice = false;
					System.out.println("Mpika edwwww");

				}

				if (curPosIx == prevPos[j][0] && curPosIy == prevPos[j][1]
						&& curPosJx == prevPos[i][0]
						&& curPosJy == prevPos[i][1]) {
					validChoice = false;
				}

				if (!PacmanUtilities.colisionDetection) {
					validChoice = true;
				}

			}

		}

		return validChoice;
	}

	public static boolean detectFutureColision(int[][] prevPos, Room[][] lab,
			boolean borders) {
		boolean validChoice = true;
		boolean[] colision = new boolean[4];

		for (int i = 0; i < 4; i++) {
			colision[i] = true;
		}

		for (int i = 0; i < 4; i++) {
			int curPosIx = prevPos[i][0];
			int curPosIy = prevPos[i][1];

			for (int j = 0; j < PacmanUtilities.numberOfGhosts; j++) {
				if (j != i) {

					int curPosJx = prevPos[j][0];
					int curPosJy = prevPos[j][1];

					if (curPosIx == curPosJx && curPosIy == curPosJy) {
						colision[j] = false;
						System.out.println("Mpika edwwww");

					}

					if (curPosIx == prevPos[j][0] && curPosIy == prevPos[j][1]
							&& curPosJx == prevPos[i][0]
							&& curPosJy == prevPos[i][1]) {
						colision[j] = false;
					}

					if (!PacmanUtilities.colisionDetection) {
						validChoice = true;
					}

				}

			}
		}

		for (int i = 0; i < 4; i++) {
			if (colision[i] == false)
				validChoice = false;
		}

		return validChoice;
	}

	public static Room[] returnVisionVector(Room[][] labyrinth, int[] position,
			int size, boolean isPrey) {
		// size is the "length" of the vision vector for each direction: NORTH,
		// SOUTH, EAST and WEST
		Room[] visionVector = new Room[4 * size + 1];
		int[] isVisible = new int[4 * size + 1]; // if 1 => is visible, if 0 =>
		// not visible
		int positionI = position[0];
		int positionJ = position[1];
		int rows = labyrinth.length;
		int columns = labyrinth[0].length;

		for (int i = 0; i < 4 * size + 1; i++) {
			visionVector[i] = new Room();
			isVisible[i] = 0;
		}

		/*
		 * The vector is filled sequentially for each direction, and
		 * incrementally over the distance from the point of origin IE (with
		 * size 2): visionVector[0] = current position visionVector[1] = NORTH -
		 * 1 step from position visionVector[2] = NORTH - 2 steps from position
		 * visionVector[3] = SOUTH - 1 step from position visionVector[4] =
		 * SOUTH - 2 steps from position visionVector[5] = EAST - 1 step from
		 * position visionVector[6] = EAST - 2 steps from position
		 * visionVector[7] = WEST - 1 step from position visionVector[8] = WEST
		 * - 2 steps from position
		 * 
		 * If a certain position is NOT visible, then the corresponding ROOM
		 * will be NULL. If the caller is predator, knowledge of Exit is not
		 * included.
		 */

		isVisible[0] = 1;
		// NORTH POSITIONS
		if (positionI - 1 < 0) {
			isVisible[1] = 0;
		} else {
			if (labyrinth[positionI][positionJ].walls[Room.NORTH] == 1
					&& isVisible[0] == 1) {
				isVisible[1] = 1;
			} else {
				isVisible[1] = 0;
			}
		}
		if (positionI - 2 < 0) {
			isVisible[2] = 0;
		} else {
			if (labyrinth[positionI - 1][positionJ].walls[Room.NORTH] == 1
					&& isVisible[1] == 1) {
				isVisible[2] = 1;
			} else {
				isVisible[2] = 0;
			}
		}

		// SOUTH POSITIONS
		if (positionI + 1 >= rows) {
			isVisible[3] = 0;
		} else {
			if (labyrinth[positionI][positionJ].walls[Room.SOUTH] == 1
					&& isVisible[0] == 1) {
				isVisible[3] = 1;
			} else {
				isVisible[3] = 0;
			}
		}
		if (positionI + 2 >= rows) {
			isVisible[4] = 0;
		} else {
			if (labyrinth[positionI + 1][positionJ].walls[Room.SOUTH] == 1
					&& isVisible[3] == 1) {
				isVisible[4] = 1;
			} else {
				isVisible[4] = 0;
			}
		}

		// EAST POSITIONS
		if (positionJ + 1 >= columns) {
			isVisible[5] = 0;
		} else {
			if (labyrinth[positionI][positionJ].walls[Room.EAST] == 1
					&& isVisible[0] == 1) {
				isVisible[5] = 1;
			} else {
				isVisible[5] = 0;
			}
		}
		if (positionJ + 2 >= columns) {
			isVisible[6] = 0;
		} else {
			if (labyrinth[positionI][positionJ + 1].walls[Room.EAST] == 1
					&& isVisible[5] == 1) {
				isVisible[6] = 1;
			} else {
				isVisible[6] = 0;
			}
		}

		// WEST POSITIONS
		if (positionJ - 1 < 0) {
			isVisible[7] = 0;
		} else {
			if (labyrinth[positionI][positionJ].walls[Room.WEST] == 1
					&& isVisible[0] == 1) {
				isVisible[7] = 1;
			} else {
				isVisible[7] = 0;
			}
		}
		if (positionJ - 2 < 0) {
			isVisible[8] = 0;
		} else {
			if (labyrinth[positionI][positionJ - 1].walls[Room.WEST] == 1
					&& isVisible[7] == 1) {
				isVisible[8] = 1;
			} else {
				isVisible[8] = 0;
			}
		}

		// Calculate current position
		Room temp_init = new Room();
		temp_init.walls[Room.NORTH] = labyrinth[positionI][positionJ].walls[Room.NORTH];
		temp_init.walls[Room.SOUTH] = labyrinth[positionI][positionJ].walls[Room.SOUTH];
		temp_init.walls[Room.EAST] = labyrinth[positionI][positionJ].walls[Room.EAST];
		temp_init.walls[Room.WEST] = labyrinth[positionI][positionJ].walls[Room.WEST];

		if (labyrinth[positionI][positionJ].isPacman()) {
			temp_init.setAsPacman();
		}
		if (labyrinth[positionI][positionJ].isGhost()) {
			temp_init.setAsGhost();
		}
		visionVector[0] = temp_init;

		// Calculate North Positions
		for (int i = 0; i < size; i++) {
			Room temp = new Room();
			if (isVisible[1 + i] == 0) {
				visionVector[1 + i] = null;
			} else {
				temp.walls[Room.NORTH] = labyrinth[positionI - (i + 1)][positionJ].walls[Room.NORTH];
				temp.walls[Room.SOUTH] = labyrinth[positionI - (i + 1)][positionJ].walls[Room.SOUTH];
				temp.walls[Room.EAST] = labyrinth[positionI - (i + 1)][positionJ].walls[Room.EAST];
				temp.walls[Room.WEST] = labyrinth[positionI - (i + 1)][positionJ].walls[Room.WEST];

				if (labyrinth[positionI - (i + 1)][positionJ].isPacman()) {
					temp.setAsPacman();
				}
				if (labyrinth[positionI - (i + 1)][positionJ].isGhost()) {
					temp.setAsGhost();
				}
				visionVector[1 + i] = temp;
			}
		}

		// Calculate South Positions
		for (int i = 0; i < size; i++) {
			Room temp = new Room();
			if (isVisible[1 + size + i] == 0) {
				visionVector[1 + size + i] = null;
			} else {
				temp.walls[Room.NORTH] = labyrinth[positionI + (i + 1)][positionJ].walls[Room.NORTH];
				temp.walls[Room.SOUTH] = labyrinth[positionI + (i + 1)][positionJ].walls[Room.SOUTH];
				temp.walls[Room.EAST] = labyrinth[positionI + (i + 1)][positionJ].walls[Room.EAST];
				temp.walls[Room.WEST] = labyrinth[positionI + (i + 1)][positionJ].walls[Room.WEST];

				if (labyrinth[positionI + (i + 1)][positionJ].isPacman()) {
					temp.setAsPacman();
				}
				if (labyrinth[positionI + (i + 1)][positionJ].isGhost()) {
					temp.setAsGhost();
				}
				visionVector[1 + size + i] = temp;
			}
		}

		// Calculate East Positions
		for (int i = 0; i < size; i++) {
			Room temp = new Room();
			if (isVisible[1 + 2 * size + i] == 0) {
				visionVector[1 + 2 * size + i] = null;
			} else {
				temp.walls[Room.NORTH] = labyrinth[positionI][positionJ
						+ (i + 1)].walls[Room.NORTH];
				temp.walls[Room.SOUTH] = labyrinth[positionI][positionJ
						+ (i + 1)].walls[Room.SOUTH];
				temp.walls[Room.EAST] = labyrinth[positionI][positionJ
						+ (i + 1)].walls[Room.EAST];
				temp.walls[Room.WEST] = labyrinth[positionI][positionJ
						+ (i + 1)].walls[Room.WEST];

				if (labyrinth[positionI][positionJ + (i + 1)].isPacman()) {
					temp.setAsPacman();
				}
				if (labyrinth[positionI][positionJ + (i + 1)].isGhost()) {
					temp.setAsGhost();
				}
				visionVector[1 + 2 * size + i] = temp;
			}
		}

		// Calculate West Positions
		for (int i = 0; i < size; i++) {
			Room temp = new Room();
			if (isVisible[1 + 3 * size + i] == 0) {
				visionVector[1 + 3 * size + i] = null;
			} else {
				temp.walls[Room.NORTH] = labyrinth[positionI][positionJ
						- (i + 1)].walls[Room.NORTH];
				temp.walls[Room.SOUTH] = labyrinth[positionI][positionJ
						- (i + 1)].walls[Room.SOUTH];
				temp.walls[Room.EAST] = labyrinth[positionI][positionJ
						- (i + 1)].walls[Room.EAST];
				temp.walls[Room.WEST] = labyrinth[positionI][positionJ
						- (i + 1)].walls[Room.WEST];

				if (labyrinth[positionI][positionJ - (i + 1)].isPacman()) {
					temp.setAsPacman();
				}
				if (labyrinth[positionI][positionJ - (i + 1)].isGhost()) {
					temp.setAsGhost();
				}
				visionVector[1 + 3 * size + i] = temp;
			}
		}

		return visionVector;
	}

	public static Room[][] setRandomFlags(Room[][] labyrinth,
			int number_of_flags) {
		int rows = labyrinth.length;
		int columns = labyrinth[0].length;
		int totalNumberofRooms = rows * columns;

		labyrinth[2][2].setAsFlag();
		labyrinth[17][2].setAsFlag();
		labyrinth[2][22].setAsFlag();
		labyrinth[17][22].setAsFlag();

		return labyrinth;
	}

	public static boolean flagColision(Room[][] Labyrinth, int[] position,
			int direction) {
		boolean colision = false;

		// flag north

		if (direction == Room.NORTH && position[0] > 0) {
			if (Labyrinth[position[0] - 1][position[1]].isFlag())
				colision = true;
		}

		// flag south

		if (direction == Room.SOUTH
				&& position[0] < PacmanUtilities.numberOfRows - 1) {
			if (Labyrinth[position[0] + 1][position[1]].isFlag())
				colision = true;
		}

		// flag east

		if (direction == Room.EAST
				&& position[1] < PacmanUtilities.numberOfColumns - 1) {
			if (Labyrinth[position[0]][position[1] + 1].isFlag())
				colision = true;
		}

		// flag west

		if (direction == Room.WEST && position[1] > 0) {
			if (Labyrinth[position[0]][position[1] - 1].isFlag())
				colision = true;
		}

		return colision;

	}

	public static Room[][] copy(Room[][] maze) {
		Room[][] newMaze = new Room[PacmanUtilities.numberOfRows][PacmanUtilities.numberOfColumns];

		for (int i = 0; i < PacmanUtilities.numberOfRows; i++) {
			for (int j = 0; j < PacmanUtilities.numberOfColumns; j++) {

				newMaze[i][j] = new Room();

			}
		}

		for (int i = 0; i < PacmanUtilities.numberOfRows; i++) {
			for (int j = 0; j < PacmanUtilities.numberOfColumns; j++) {

				if (maze[i][j].isCapturedFlag())
					newMaze[i][j].setAsCapturedFlag();
				if (maze[i][j].isFlag())
					newMaze[i][j].setAsFlag();
				if (maze[i][j].isGhost())
					newMaze[i][j].setAsGhost();
				if (maze[i][j].isPacman())
					newMaze[i][j].setAsPacman();

				for (int k = 0; k < 4; k++) {
					if (maze[i][j].walls[k] == 0)
						newMaze[i][j].walls[k] = 0;
					if (maze[i][j].walls[k] == 1)
						newMaze[i][j].walls[k] = 1;

				}
			}
		}
		return newMaze;
	}

	public static ArrayList<int[][]> allGhostMoves(Room[][] maze,
			int[][] ghostPositions) {

		ArrayList<int[][]> futurePos = new ArrayList<int[][]>();
		ArrayList<int[]> directions = new ArrayList<int[]>();

		for (int m = 0; m < PacmanUtilities.numberOfGhosts; m++) {

			// System.out.println("X--> " + gPos[m][0] + "  Y--> " +
			// gPos[m][1]);
		}
		// System.out.println(" ");

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					for (int l = 0; l < 4; l++) {

						if (evaluateDirection(maze, ghostPositions[0], i,
								PacmanUtilities.boarders)
								&& evaluateDirection(maze, ghostPositions[1],
										j, PacmanUtilities.boarders)
								&& evaluateDirection(maze, ghostPositions[2],
										k, PacmanUtilities.boarders)
								&& evaluateDirection(maze, ghostPositions[3],
										l, PacmanUtilities.boarders)
								&& !flagColision(maze, ghostPositions[0], i)
								&& !flagColision(maze, ghostPositions[1], j)
								&& !flagColision(maze, ghostPositions[2], k)
								&& !flagColision(maze, ghostPositions[3], l)) {
							int[][] newPos = new int[PacmanUtilities.numberOfGhosts][2];

							newPos[0] = evaluateNextPosition(maze,
									ghostPositions[0], i,
									PacmanUtilities.boarders);
							// System.out.println("X--> " + newPos[0][0]
							// + "  Y--> " + newPos[0][1]);
							// System.out.println(" ");
							newPos[1] = evaluateNextPosition(maze,
									ghostPositions[1], j,
									PacmanUtilities.boarders);
							// System.out.println("X--> " + newPos[1][0]
							// + "  Y--> " + newPos[1][1]);
							// System.out.println(" ");
							newPos[2] = evaluateNextPosition(maze,
									ghostPositions[2], k,
									PacmanUtilities.boarders);
							newPos[3] = evaluateNextPosition(maze,
									ghostPositions[3], l,
									PacmanUtilities.boarders);

							futurePos.add(newPos);
							// futurePos.addElement(newPos);
							int[] dir = new int[PacmanUtilities.numberOfGhosts];

							dir[0] = i;
							dir[1] = j;
							dir[2] = k;
							dir[3] = l;
							directions.add(dir);
							// System.out.println(futurePos.size());
						}

					}

				}

			}

		}

		// System.out.println(futurePos.size());

		// for (int i = 0; i < futurePos.size(); i++) {
		// int[][] positions = (int[][]) futurePos.get(i);
		// System.out.println(i);
		// for (int m = 0; m < PacmanUtilities.numberOfGhosts; m++) {

		// System.out.println("X--> " + positions[m][0] + "  Y--> "
		// + positions[m][1]);
		// }

		// }

		for (int i = futurePos.size() - 1; i >= 0; i--) {
			int[][] positions = (int[][]) futurePos.get(i);
			// int[] direct = directions.get(i);

			for (int m = 0; m < PacmanUtilities.numberOfGhosts - 1; m++) {
				for (int n = m + 1; n < PacmanUtilities.numberOfGhosts; n++) {

					// System.out.println(positions[m][0] + "<----->"
					// + positions[n][0]);
					// System.out.println(positions[m][1] + "<----->"
					// + positions[n][1]);
					// System.out.println(" ");
					if ((positions[m][0] == positions[n][0] && positions[m][1] == positions[n][1])
							|| (positions[m][0] == positions[n][0]
									&& positions[m][1] == ghostPositions[n][1] && ghostPositions[m][1] == positions[n][1])
							|| (positions[m][1] == positions[n][1]
									&& positions[m][0] == ghostPositions[n][0] && ghostPositions[m][0] == positions[n][0])) {
						// System.out.println("Edwww");
						// System.out.println(i);
						futurePos.remove(i);
						m = PacmanUtilities.numberOfGhosts;
						n = PacmanUtilities.numberOfGhosts;
					}

				}

			}

		}
		// System.out.println(futurePos.size());

		return futurePos;
	}

	public static ArrayList<int[]> allGhostDirections(Room[][] maze,
			int[][] ghostPositions) {

		ArrayList<int[][]> futurePos = new ArrayList();
		ArrayList<int[]> directions = new ArrayList();

		for (int m = 0; m < PacmanUtilities.numberOfGhosts; m++) {

			// System.out.println("X--> " + gPos[m][0] + "  Y--> " +
			// gPos[m][1]);
		}
		// System.out.println(" ");

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					for (int l = 0; l < 4; l++) {

						if (evaluateDirection(maze, ghostPositions[0], i,
								PacmanUtilities.boarders)
								&& evaluateDirection(maze, ghostPositions[1],
										j, PacmanUtilities.boarders)
								&& evaluateDirection(maze, ghostPositions[2],
										k, PacmanUtilities.boarders)
								&& evaluateDirection(maze, ghostPositions[3],
										l, PacmanUtilities.boarders)
								&& !flagColision(maze, ghostPositions[0], i)
								&& !flagColision(maze, ghostPositions[1], j)
								&& !flagColision(maze, ghostPositions[2], k)
								&& !flagColision(maze, ghostPositions[3], l)) {
							int[][] newPos = new int[PacmanUtilities.numberOfGhosts][2];

							newPos[0] = evaluateNextPosition(maze,
									ghostPositions[0], i,
									PacmanUtilities.boarders);
							// System.out.println("X--> " + newPos[0][0]
							// + "  Y--> " + newPos[0][1]);
							// System.out.println(" ");
							newPos[1] = evaluateNextPosition(maze,
									ghostPositions[1], j,
									PacmanUtilities.boarders);
							// System.out.println("X--> " + newPos[1][0]
							// + "  Y--> " + newPos[1][1]);
							// System.out.println(" ");
							newPos[2] = evaluateNextPosition(maze,
									ghostPositions[2], k,
									PacmanUtilities.boarders);
							newPos[3] = evaluateNextPosition(maze,
									ghostPositions[3], l,
									PacmanUtilities.boarders);

							futurePos.add(newPos);
							// futurePos.addElement(newPos);
							int[] dir = new int[PacmanUtilities.numberOfGhosts];

							dir[0] = i;
							dir[1] = j;
							dir[2] = k;
							dir[3] = l;
							directions.add(dir);
							// System.out.println(futurePos.size());
						}

					}

				}

			}

		}

		// System.out.println(futurePos.size());

		// for (int i = 0; i < futurePos.size(); i++) {
		// int[][] positions = (int[][]) futurePos.get(i);
		// System.out.println(i);
		// for (int m = 0; m < PacmanUtilities.numberOfGhosts; m++) {

		// System.out.println("X--> " + positions[m][0] + "  Y--> "
		// + positions[m][1]);
		// }

		// }

		for (int i = futurePos.size() - 1; i >= 0; i--) {
			int[][] positions = (int[][]) futurePos.get(i);
			// int[] direct = directions.get(i);

			for (int m = 0; m < PacmanUtilities.numberOfGhosts - 1; m++) {
				for (int n = m + 1; n < PacmanUtilities.numberOfGhosts; n++) {

					// System.out.println(positions[m][0] + "<----->"
					// + positions[n][0]);
					// System.out.println(positions[m][1] + "<----->"
					// + positions[n][1]);
					// System.out.println(" ");
					if ((positions[m][0] == positions[n][0] && positions[m][1] == positions[n][1])
							|| (positions[m][0] == positions[n][0]
									&& positions[m][1] == ghostPositions[n][1] && ghostPositions[m][1] == positions[n][1])
							|| (positions[m][1] == positions[n][1]
									&& positions[m][0] == ghostPositions[n][0] && ghostPositions[m][0] == positions[n][0])) {
						// System.out.println("Edwww");
						// System.out.println(i);
						directions.remove(i);
						m = PacmanUtilities.numberOfGhosts;
						n = PacmanUtilities.numberOfGhosts;
					}

				}

			}

		}
		// System.out.println(futurePos.size());

		return directions;
	}

	public static void movePacman(Room[][] Maze, int[] presentPosition,
			int[] nextPosition) {
		Maze[presentPosition[0]][presentPosition[1]].setEmpty(1);
		Maze[nextPosition[0]][nextPosition[1]].setAsPacman();

	}

	public static void moveGhosts(Room[][] Maze, int[][] presentPositions,
			int[][] nextPositions) {
		for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
			Maze[presentPositions[i][0]][presentPositions[i][1]].setEmpty(0);
		}

		for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
			Maze[nextPositions[i][0]][nextPositions[i][1]].setAsGhost();
		}
	}

	public static double calculateScore(boolean isPacman, int flags, int time) {
		double score = 0;
		if (isPacman) {
			if (flags == 0)
				score = 0;
			if (flags == 1)
				score = 1;
			if (flags == 2)
				score = 2;
			if (flags == 3)
				score = 3;
			if (flags == 4) {
				if (time > 200)
					score = 4;
				else
					score = 4.5;
			}

			if (score > 0 && score < 4) {
				score = score + ((0.5 / 1000) * time);
			}

		}

		else {
			if (flags == 0)
				if (time > 200)
					score = 4;
				else
					score = 4.5;
			if (flags == 1)
				score = 3;
			if (flags == 2)
				score = 2;
			if (flags == 3)
				score = 1;
			if (flags == 4)
				score = 0;

			if (score > 0 && score < 4) {
				score = score + (0.5 - (0.5 / 1000) * time);
			}

		}

		double finalScore = score;
		return finalScore;
	}

}
