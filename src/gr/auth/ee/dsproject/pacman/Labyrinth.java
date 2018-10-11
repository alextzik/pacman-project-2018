/**
 *
 */
package gr.auth.ee.dsproject.pacman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

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
public class Labyrinth extends JPanel {

	private int WIDTH;
	private int HEIGHT;
	private boolean boarders;
	private int rows;
	private int columns;
	private int flags;

	private int padding;
	private int graphics_WIDTH;
	private int graphics_HEIGHT;
	private Room[][] rooms;

	/**
   *
   */
	private static final long serialVersionUID = 1L;

	/**
	 * Does the actual drawing.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// You add/change the statements here to draw
		// the picture you want.

		// Draw Rooms

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("pacmanright.png"));
		} catch (IOException e) {
		}

		BufferedImage img1 = null;
		try {
			img1 = ImageIO.read(new File("ghostsprite.png"));
		} catch (IOException e) {
		}

		// Drawing Bordered Maze
		// if (!boarders) {
		g2.setColor(Color.BLACK);
		// north
		/*
		 * for (int j = 0; j < columns; j++) { if (rooms[0][j].walls[Room.NORTH]
		 * == 0) g2.drawLine(padding + j * graphics_HEIGHT / rows, padding + 0
		 * graphics_WIDTH / columns, padding + (j + 1) graphics_HEIGHT / rows,
		 * padding + 0 * graphics_WIDTH / columns); }
		 * 
		 * // south for (int j = 0; j < columns; j++) { g2.drawLine(padding + j
		 * * graphics_HEIGHT / rows, padding + (rows) graphics_WIDTH / columns,
		 * padding + (j + 1) graphics_HEIGHT / rows, padding + (rows) *
		 * graphics_WIDTH / columns); }
		 * 
		 * // west for (int i = 0; i < rows; i++) { g2.drawLine(padding + 0 *
		 * graphics_HEIGHT / rows, padding + i graphics_WIDTH / columns, padding
		 * + 0 * graphics_HEIGHT / rows, padding + (i + 1) * graphics_WIDTH /
		 * columns); }
		 * 
		 * // east for (int i = 0; i < rows; i++) { g2.drawLine(padding +
		 * (columns) * graphics_HEIGHT / rows, padding + i * graphics_WIDTH /
		 * columns, padding + (columns) graphics_HEIGHT / rows, padding + (i +
		 * 1) graphics_WIDTH / columns); } // }
		 */

		Stroke Stroke = new BasicStroke(2, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 0, new float[] { (float) 0.5 }, 0);
		g2.setStroke(Stroke);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (rooms[i][j].walls[Room.WEST] == 0) {

					g2.drawLine(padding + j * graphics_HEIGHT / rows, padding
							+ i * graphics_WIDTH / columns, padding + j
							* graphics_HEIGHT / rows, padding + (i + 1)
							* graphics_WIDTH / columns);
				}
				if (rooms[i][j].walls[Room.SOUTH] == 0) {
					g2.drawLine(padding + j * graphics_HEIGHT / rows, padding
							+ (i + 1) * graphics_WIDTH / columns, padding
							+ (j + 1) * graphics_HEIGHT / rows, padding
							+ (i + 1) * graphics_WIDTH / columns);
				}
				if (rooms[i][j].walls[Room.EAST] == 0) {
					g2.drawLine(padding + (j + 1) * graphics_HEIGHT / rows,
							padding + i * graphics_WIDTH / columns, padding
									+ (j + 1) * graphics_HEIGHT / rows, padding
									+ (i + 1) * graphics_WIDTH / columns);
				}

				if (rooms[i][j].walls[Room.NORTH] == 0) {
					g2.drawLine(padding + j * graphics_HEIGHT / rows, padding
							+ i * graphics_WIDTH / columns, padding + (j + 1)
							* graphics_HEIGHT / rows, padding + i
							* graphics_WIDTH / columns);
				}
			}
		}

		g2.setColor(Color.BLACK);
		// Drawing Borderless Maze
		Stroke drawingStroke = new BasicStroke(1, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		g2.setStroke(drawingStroke);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (rooms[i][j].walls[Room.WEST] == 1) {

					g2.drawLine(padding + j * graphics_HEIGHT / rows, padding
							+ i * graphics_WIDTH / columns, padding + j
							* graphics_HEIGHT / rows, padding + (i + 1)
							* graphics_WIDTH / columns);
				}
				if (rooms[i][j].walls[Room.SOUTH] == 1) {
					g2.drawLine(padding + j * graphics_HEIGHT / rows, padding
							+ (i + 1) * graphics_WIDTH / columns, padding
							+ (j + 1) * graphics_HEIGHT / rows, padding
							+ (i + 1) * graphics_WIDTH / columns);
				}
				if (rooms[i][j].walls[Room.EAST] == 1) {
					g2.drawLine(padding + (j + 1) * graphics_HEIGHT / rows,
							padding + i * graphics_WIDTH / columns, padding
									+ (j + 1) * graphics_HEIGHT / rows, padding
									+ (i + 1) * graphics_WIDTH / columns);
				}

				if (rooms[i][j].walls[Room.NORTH] == 1) {
					g2.drawLine(padding + j * graphics_HEIGHT / rows, padding
							+ i * graphics_WIDTH / columns, padding + (j + 1)
							* graphics_HEIGHT / rows, padding + i
							* graphics_WIDTH / columns);
				}
				/*
				 * if (rooms[i][j].smell()) { g2.setColor(Color.PINK);
				 * g2.fillRect(padding + 1 + j * graphics_HEIGHT / rows, padding
				 * + 1 + i * graphics_WIDTH / columns, graphics_HEIGHT / rows -
				 * 1, graphics_WIDTH / columns - 1); g2.setColor(Color.BLACK); }
				 */

				if (rooms[i][j].isFlag()) {
					g2.setColor(Color.BLUE);

					g2.fillRoundRect(padding + 1 + j * graphics_HEIGHT / rows,
							padding + 1 + i * graphics_WIDTH / columns,
							graphics_HEIGHT / rows - 1, graphics_WIDTH
									/ columns - 1, 20, 20);

					// g2.fillRect(padding + 1 + j * graphics_HEIGHT / rows,
					// padding + 1 + i * graphics_WIDTH / columns,
					// graphics_HEIGHT / rows - 1, graphics_WIDTH
					// / columns - 1);
					g2.setColor(Color.BLACK);
				}

				if (rooms[i][j].isCapturedFlag()) {
					g2.setColor(Color.lightGray);

					g2.fillRoundRect(padding + 1 + j * graphics_HEIGHT / rows,
							padding + 1 + i * graphics_WIDTH / columns,
							graphics_HEIGHT / rows - 1, graphics_WIDTH
									/ columns - 1, 20, 20);

					// g2.fillRect(padding + 1 + j * graphics_HEIGHT / rows,
					// padding + 1 + i * graphics_WIDTH / columns,
					// graphics_HEIGHT / rows - 1, graphics_WIDTH
					// / columns - 1);
					g2.setColor(Color.BLACK);
				}

				if (rooms[i][j].isPacman()) {
					g2.setColor(Color.GREEN);
					g2.drawImage(img, padding + 1 + j * graphics_HEIGHT / rows,
							padding + 1 + i * graphics_WIDTH / columns,
							graphics_HEIGHT / rows - 1, graphics_WIDTH
									/ columns - 1, null);
					// g2.fillRect(padding + 1 + j * graphics_HEIGHT / rows,
					// padding + 1 + i * graphics_WIDTH / columns,
					// graphics_HEIGHT / rows - 1, graphics_WIDTH
					// / columns - 1);
					g2.setColor(Color.BLACK);
				}
				if (rooms[i][j].isGhost()) {
					g2.setColor(Color.RED);
					g2.drawImage(img1,
							padding + 1 + j * graphics_HEIGHT / rows, padding
									+ 1 + i * graphics_WIDTH / columns,
							graphics_HEIGHT / rows - 1, graphics_WIDTH
									/ columns - 1, null);
					// g2.fillRect(padding + 1 + j * graphics_HEIGHT / rows,
					// padding + 1 + i * graphics_WIDTH / columns,
					// graphics_HEIGHT / rows - 1, graphics_WIDTH
					// / columns - 1);
					g2.setColor(Color.BLACK);
				}
			}
		}
	}

	/**
	 * Makes sure that the window drawing area ends up being the right size. You
	 * don't need to change this.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH + 2 * padding, HEIGHT);
	}

	private void generateLabyrinth() {
		padding = 10;
		graphics_WIDTH = WIDTH - 2 * padding;
		graphics_HEIGHT = HEIGHT - 2 * padding;

		LabyrinthCreator mazeMaker = new LabyrinthCreator();
		mazeMaker.setRows(rows);
		mazeMaker.setColumns(columns);
		mazeMaker.setFlags(flags);
		mazeMaker.setBoarders(boarders);
		rooms = mazeMaker.createLabyrinth();
	}

	public void emptyAllRooms() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				rooms[i][j].setEmpty(1);
				rooms[i][j].setEmpty(0);
			}
		}
	}

	private void cleanupMaze() {
		// Initiate room array
		rooms = new Room[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				rooms[i][j] = new Room();
			}
		}
	}

	/**
   *
   */
	public Labyrinth() {
		WIDTH = 500;
		HEIGHT = 500;
		rows = 5;
		columns = 5;
		flags = 2;
		cleanupMaze();
	}

	/**
   *
   */
	public Labyrinth(int maze_rows, int maze_columns, int maze_flags) {
		WIDTH = 500;
		HEIGHT = 600;
		rows = maze_rows;
		columns = maze_columns;
		flags = maze_flags;
		cleanupMaze();
		generateLabyrinth();
	}

	public Labyrinth(int maze_Width, int maze_Height, int maze_rows,
			int maze_columns, int maze_flags) {
		WIDTH = maze_Width;
		HEIGHT = maze_Height;
		rows = maze_rows;
		columns = maze_columns;
		flags = maze_flags;
		cleanupMaze();
		generateLabyrinth();
	}

	public Labyrinth(int maze_Width, int maze_Height, int maze_rows,
			int maze_columns, boolean maze_boarders, int maze_flags) {
		WIDTH = maze_Width;
		HEIGHT = maze_Height;
		rows = maze_rows;
		columns = maze_columns;
		flags = maze_flags;
		boarders = maze_boarders;
		cleanupMaze();
		generateLabyrinth();
	}

	public Room[][] returnLabyrinth() {
		return rooms;
	}

	public void setFlags(int numberOfFlags) {
		rooms = PacmanUtilities.setRandomFlags(rooms, numberOfFlags);
	}

	public void resetCaughtFlags() {
		int[][] flags = new int[PacmanUtilities.numberOfFlags][3];

		for (int i = 0; i < PacmanUtilities.numberOfRows; i++) {
			for (int j = 0; j < PacmanUtilities.numberOfColumns; j++) {

				if (rooms[i][j].isFlag())
					rooms[i][j].resetCapturedFlag();
			}
		}
	}

	public void movePrey(int[] curr_Prey, int[] prev_Prey) {
		rooms[prev_Prey[0]][prev_Prey[1]].setEmpty(1);
		rooms[curr_Prey[0]][curr_Prey[1]].setAsPacman();

	}

	public void movePredator(int[] curr_Predator, int[] prev_Predator) {
		rooms[prev_Predator[0]][prev_Predator[1]].setEmpty(0);
		rooms[curr_Predator[0]][curr_Predator[1]].setAsGhost();
	}

	public void emptyPredatorCells(int[] prev_Predator) {
		rooms[prev_Predator[0]][prev_Predator[1]].setEmpty(0);
	}

	public void setPredatorCells(int[] curr_Predator) {
		rooms[curr_Predator[0]][curr_Predator[1]].setAsGhost();
	}

	/*
	 * public void clearSmell() { for (int i=0; i<rows; i++) { for (int j=0;
	 * j<columns; j++) { rooms[i][j].unsetSmell(); } } }
	 */

	public void setPrey(int[] position) {
		rooms[position[0]][position[1]].setAsPacman();
	}

	public void setPredator(int[] position) {
		rooms[position[0]][position[1]].setAsGhost();
	}

}
