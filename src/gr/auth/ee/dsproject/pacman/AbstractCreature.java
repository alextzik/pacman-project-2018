/**
 * 
 */
package gr.auth.ee.dsproject.pacman;

/**
 * @author Micahel Tsapanos
 *
 */
public interface AbstractCreature {
	 public String getName();
	
	  public int calculateNextPacmanPosition(Room[][] Labyrinth, int[] currPos);
	  
	  public int[] calculateNextGhostPosition(Room[][] Maze,int[][] currentPos);
}
