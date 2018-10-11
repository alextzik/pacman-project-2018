/**
 *
 */
package gr.auth.ee.dsproject.pacman;

/**
 * <p>
 * Title: DataStructures 2011
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
 * @version 1.1
 */
public class Room {

	final static public int WEST = 0;
	final static public int SOUTH = 1;
	final static public int EAST = 2;
	final static public int NORTH = 3;

	private int isFlag = 0;
	private int isCapturedFlag = 0;
	private int isPrey = 0;
	private int isPredator = 0;

	public int[] walls = new int[4];

	// if walls[i] == 0 then a wall EXISTS

	/**
   *
   */
	public Room() {
		for (int i = 0; i < 4; i++) {
			walls[i] = 0;
		}
	}

	public boolean isDungeon() {
		boolean result = true;
		for (int i = 0; i < 4; i++) {
			if (walls[i] == 1) {
				result = false;
			}
		}
		return result;
	}

	public void setAsFlag() {
		isFlag = 1;
	}

	public boolean isFlag() {
		if (isFlag == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void setAsCapturedFlag() {
		isCapturedFlag = 1;
	}

	public void resetCapturedFlag() {
		isCapturedFlag = 0;
	}

	public boolean isCapturedFlag() {
		if (isCapturedFlag == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void setAsPacman() {
		isPrey = 1;
	}

	public boolean isPacman() {
		if (isPrey == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void setAsGhost() {
		isPredator = 1;
	}

	public boolean isGhost() {
		if (isPredator == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void setEmpty(int preyOrPredator) {
		// if 1, then it is a PREY call
		if (preyOrPredator == 1) {
			isPrey = 0;
		} else {
			isPredator = 0;
		}
	}

	public boolean DeadEnd() {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			if (walls[i] == 0)
				count++;
		}
		if (count == 3)
			return true;
		else
			return false;
	}

}
