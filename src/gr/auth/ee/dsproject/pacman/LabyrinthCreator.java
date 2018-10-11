/**
 *
 */
package gr.auth.ee.dsproject.pacman;

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
public class LabyrinthCreator extends AbstractLabyrinthCreator {

	private Room rooms[][];
	private int number_of_rows = 0;
	private int number_of_col = 0;
	private int number_of_flags = 0;
	private boolean boarders = false;

	/**
   *
   */
	public LabyrinthCreator() {
		number_of_rows = 0;
		number_of_col = 0;
		number_of_flags = 0;
		boarders = false;
	}

	/**
	 * @param rows
	 */
	public void setRows(int rows) {
		number_of_rows = rows;
	}

	/**
	 * @param cols
	 */
	public void setColumns(int cols) {
		number_of_col = cols;
	}

	public void setFlags(int flags) {
		number_of_flags = flags;
	}

	public void setBoarders(boolean bords) {
		boarders = bords;
	}

	/**
   *
   */
	public Room[][] createLabyrinth() {
		// Initiate room array
		rooms = new Room[number_of_rows][number_of_col];
		for (int i = 0; i < number_of_rows; i++) {
			for (int j = 0; j < number_of_col; j++) {
				rooms[i][j] = new Room();
			}
		}

		if (!boarders) {

			for (int i = 0; i < number_of_rows; i++) {
				for (int j = 0; j < number_of_col; j++) {
					rooms[i][j].walls[Room.NORTH] = 1;
					rooms[i][j].walls[Room.WEST] = 1;
					rooms[i][j].walls[Room.SOUTH] = 1;
					rooms[i][j].walls[Room.EAST] = 1;
				}
			}

			// Setting North Boarders

			for (int j = 0; j < number_of_col; j++) {
				if (j < Math.round(PacmanUtilities.numberOfColumns / 2) - 1
						|| j > Math.round(PacmanUtilities.numberOfColumns / 2) + 1)
					rooms[0][j].walls[Room.NORTH] = 0;
			}

			// Setting South Boarders
			for (int j = 0; j < number_of_col; j++) {
				if (j < Math.round(PacmanUtilities.numberOfColumns / 2) - 1
						|| j > Math.round(PacmanUtilities.numberOfColumns / 2) + 1)
					rooms[number_of_rows - 1][j].walls[Room.SOUTH] = 0;
			}

			// Setting West Boarders
			for (int i = 0; i < number_of_rows; i++) {
				if (i < Math.round(PacmanUtilities.numberOfRows / 2) - 1
						|| i > Math.round(PacmanUtilities.numberOfRows / 2))
					rooms[i][0].walls[Room.WEST] = 0;
			}

			// Setting Eastern Boarders
			for (int i = 0; i < number_of_rows; i++) {
				if (i < Math.round(PacmanUtilities.numberOfRows / 2) - 1
						|| i > Math.round(PacmanUtilities.numberOfRows / 2))
					rooms[i][number_of_col - 1].walls[Room.EAST] = 0;
			}

			// setting obstacles

			createRectangularObstacles(4, 4, 5);
			createRectangularObstacles(4, 15, 5);
			createRectangularObstacles(10, 4, 5);
			createRectangularObstacles(10, 15, 5);

		}

		else {
			for (int i = 0; i < number_of_rows; i++) {
				for (int j = 0; j < number_of_col; j++) {
					rooms[i][j].walls[Room.NORTH] = 1;
					rooms[i][j].walls[Room.WEST] = 1;
					rooms[i][j].walls[Room.SOUTH] = 1;
					rooms[i][j].walls[Room.EAST] = 1;
				}
			}

			// Setting Northern Boarders

			for (int j = 0; j < number_of_col; j++) {
				rooms[0][j].walls[Room.NORTH] = 0;
			}

			// Setting Southern Boarders

			for (int j = 0; j < number_of_col; j++) {
				rooms[number_of_rows - 1][j].walls[Room.SOUTH] = 0;
			}

			// Setting Western Boarders
			for (int i = 0; i < number_of_rows; i++) {
				rooms[i][0].walls[Room.WEST] = 0;
			}

			// Setting Eastern Boarders
			for (int i = 0; i < number_of_rows; i++) {
				rooms[i][number_of_col - 1].walls[Room.EAST] = 0;
			}
		}

		return rooms;

	}

	public void createRectangularObstacles(int startX, int startY, int size) {
		for (int i = 1; i < size; i++) {
			rooms[startX][startY + i].walls[Room.SOUTH] = 0;
			rooms[startX + i][startY].walls[Room.EAST] = 0;
			rooms[startX + size][startY + i].walls[Room.NORTH] = 0;
			rooms[startX + i][startY + size].walls[Room.WEST] = 0;
		}
	}
}
