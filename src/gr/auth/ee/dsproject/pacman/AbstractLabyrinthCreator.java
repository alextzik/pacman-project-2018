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
public abstract class AbstractLabyrinthCreator {

	public abstract void setRows(int rows);

	public abstract void setColumns(int cols);

	public abstract void setFlags(int flags);

	public abstract void setBoarders(boolean bords);

	public abstract Room[][] createLabyrinth();

	public abstract void createRectangularObstacles(int startX, int startY,
			int size);

}
