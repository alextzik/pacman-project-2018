package gr.auth.ee.dsproject.node89068978;

import gr.auth.ee.dsproject.pacman.PacmanUtilities;
import gr.auth.ee.dsproject.pacman.Room;

import java.util.Vector;

public class NodeC
{

  int nodeX;
  int nodeY;
  int depth;
  int nodeMove;
  double nodeEvaluation;
  int[][] currentGhostPos;
  int[][] flagPos;
  boolean[] currentFlagStatus;

  NodeC parent;
  Vector<NodeC> children = new Vector<NodeC>();

  // Constructor
  public NodeC (int depth, int move, int[] curPos, Room[][] Maze)
  {

    nodeX = curPos[0];
    nodeY = curPos[1];

    if (depth == 1)
      nodeMove = move;
    else
      nodeMove = -1;

    this.depth = depth;

    currentGhostPos = findGhosts(Maze);
    flagPos = findFlags(Maze);
    currentFlagStatus = checkFlags(Maze);

    if (depth > 0)
      nodeEvaluation = evaluate();

  }

  private int[][] findGhosts (Room[][] Maze)
  {
    int[][] temp = new int[PacmanUtilities.numberOfGhosts][2];
    int counter = 0;

    for (int i = 0; i < PacmanUtilities.numberOfRows; i++) {
      for (int j = 0; j < PacmanUtilities.numberOfColumns; j++) {
        if (Maze[i][j].isGhost()) {
          temp[counter][0] = i;
          temp[counter][1] = j;
          counter++;
        }

      }
    }

    return temp;
  }

  private int[][] findFlags (Room[][] Maze)
  {
    int[][] temp = new int[PacmanUtilities.numberOfFlags][2];
    int counter = 0;

    for (int i = 0; i < PacmanUtilities.numberOfRows; i++) {
      for (int j = 0; j < PacmanUtilities.numberOfColumns; j++) {
        if (Maze[i][j].isFlag()) {
          temp[counter][0] = i;
          temp[counter][1] = j;
          counter++;
        }

      }
    }

    return temp;
  }

  private boolean[] checkFlags (Room[][] Maze)
  {

    boolean[] temp = new boolean[PacmanUtilities.numberOfFlags];

    for (int i = 0; i < PacmanUtilities.numberOfFlags; i++) {

      if (Maze[flagPos[i][0]][flagPos[i][1]].isCapturedFlag()) {
        temp[i] = true;
      } else {
        temp[i] = false;
      }

    }

    return temp;
  }

  private double evaluate ()
  {

    double evaluation = (200 * Math.random()) - 100;
    return evaluation;

  }

  public void status ()
  {

    System.out.println("Node Status");
    System.out.println("Node x: " + nodeX);
    System.out.println("Node y: " + nodeY);
    System.out.println("Node Direction: " + nodeMove);

    System.out.println("Ghosts Position");
    for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
      System.out.println("Ghost " + i + ": " + currentGhostPos[i][0] + "," + currentGhostPos[i][1]);
    }

    System.out.println("Flag Status");
    for (int i = 0; i < PacmanUtilities.numberOfFlags; i++) {
      System.out.println("Flag " + i + ": " + flagPos[i][0] + "," + flagPos[i][1] + " Status: " + currentFlagStatus[i]);
    }

    System.out.println("Evaluation Value:" + nodeEvaluation);

  }

  public void statusShort ()
  {
    System.out.println("Node Direction: " + nodeMove + " Evaluation: " + nodeEvaluation);
    System.out.println("Node Children: ");
    for (int i = 0; i < children.size(); i++) {
      System.out.println("Child " + i + ":  Direction: " + children.get(i).nodeMove + ", Evaluation: " + children.get(i).nodeEvaluation);
    }

  }

  public int getMove ()
  {
    return nodeMove;
  }

  public double getEvaluation ()
  {
    return nodeEvaluation;
  }

  public void setEvaluation (double value)
  {
    nodeEvaluation = value;
  }

  public void setParent (NodeC parent)
  {

    this.parent = parent;

  }

  public void addChildren (NodeC children)
  {

    this.children.add(children);

  }

  public Vector<NodeC> getChildren ()
  {

    return this.children;

  }

  public int[][] getGhostsPos ()
  {
    return currentGhostPos;
  }

  public int getNodeX ()
  {
    return nodeX;
  }

  public int getNodeY ()
  {
    return nodeY;
  }

}
