package gr.auth.ee.dsproject.node89068978;

import gr.auth.ee.dsproject.pacman.PacmanUtilities;
import gr.auth.ee.dsproject.pacman.Room;
import java.util.ArrayList;

/**
 * <p>
 * Title: DataStructures2011
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

public class Creature implements gr.auth.ee.dsproject.pacman.AbstractCreature {

	public String getName() {
		return "8906_8978";
	}

	private int[] prevPacPos = { -1, -1 };
	private int step = 1;
	private boolean amPrey;

	public Creature(boolean isPrey) {
		amPrey = isPrey;

	}

	/*
	 * Η συνάρτηση αυτή επιστρέφει την επόμενη κίνηση του Pacman. Δημιουργεί τη
	 * ρίζα του δέντρου σε κάθε κίνηση, δηλαδή τον κόμβο στον οποίο βρίσκεται τη
	 * δεδομένη στιγμή ο Pacman και στη συνέχεια καλεί την createSubTreePacman
	 * για την υλοποίηση του δένρου. Έπειτα, αποθηκεύει στη μεταβλητή h τον
	 * καλύτερο κόμβο για την επόμενη κίνηση, τον οποίο λαμβάνει από τη minimax.
	 * Τέλος, εμφανίζει την αξιολόγηση για τον κόμβο που επιλέχθηκε και τον
	 * αριθμό των κόμβων του δένδρου ενώ επιστρέφει την κίνηση που θεωρήθηκε η
	 * καλύτερη δυνατή από την ανάλυση του minimax.
	 */
	public int calculateNextPacmanPosition(Room[][] Maze, int[] currPosition) {
		Node h;
		Node root = new Node(0, null, currPosition[0], currPosition[1], -1, Maze);
		createSubTreePacman(root.getDepth() + 1, root, Maze, currPosition);
		h = MinimaxAB.minimax(root);
		return h.getMove();
	}

	/*
	 * Η συνάρτηση αυτή δημιουργεί το πρώτο επίπεδο του δένδρου. Λαμβάνοντας ως
	 * όρισμα τον αρχικό κόμβο, ελέγχει για όλες τις κατευθύνσεις αν οδηγούμαστε
	 * σε τοίχο. Η σύγκρουση αυτή δεν είναι επιτρεπτή και συνεπώς η ανάλυση
	 * συνεχίζεται για τις υπόλοιπες, επιτρεπτές, κινήσεις. Για κάθε μία από
	 * αυτές, ελέγχεται με τη βοήθεια της συνάρτησης isLeaf(boolean isOnFlag), η
	 * οποία εξηγείται αναλυτικά στην κλάση Node89068978, αν οδηγούν σε
	 * τερματικό κόμβο. Αν ναι, καλείται η createSubTreeGhosts για να
	 * προχωρήσουμε στο δεύτερο επίπεδο. Διαφορετικά, δε χρειάζεται να
	 * επιβαρύνουμε τη δομή με επιπλέον δεδομένα.
	 */
	void createSubTreePacman(int depth, Node parent, Room[][] Maze, int[] currPacmanPosition) {
		Room[][] MazeCopy;
		int moveCount = 0;
		boolean isOnFlag = Maze[currPacmanPosition[0]][currPacmanPosition[1]].isFlag();

		for (int i = 0; i < 4; i++) {
			int[] nextPacmanPosition = currPacmanPosition;
			if (Maze[currPacmanPosition[0]][currPacmanPosition[1]].walls[i] == 1) {

				// Κάνε το αντίγραφο, υπολόγισε την επόμενη θέση του Pacman και
				// αποθήκευσε την επόμενη κίνηση του στο αντίγραφο
				MazeCopy = PacmanUtilities.copy(Maze);
				nextPacmanPosition = PacmanUtilities.evaluateNextPosition(MazeCopy, currPacmanPosition, i,
						PacmanUtilities.borders);
				PacmanUtilities.movePacman(MazeCopy, currPacmanPosition, nextPacmanPosition);

				// Δημιούργησε κόμβο για κάθε επιτρεπτή κίνηση ώς παιδί της
				// ρίζας και για αυτούς που δεν είναι τερματικοί
				// προχώρησε ένα επίπεδο ακόμα μέσω της createSubTreeGhosts
				parent.getChildren()
						.add(new Node(depth, parent, nextPacmanPosition[0], nextPacmanPosition[1], i, MazeCopy));
				if (!parent.getChildren().get(moveCount).isLeaf(isOnFlag)) {
					createSubTreeGhosts(depth + 1, parent.getChildren().get(moveCount), MazeCopy, parent.findGhosts());
				}
				// Βοηθητική μεταβλητή για να κρατάω τον αριθμό των παιδιών που
				// έχουν εισαχθεί στη ρίζα, μέχρι στιγμής
				moveCount++;
			}
		}

	}

	/*
	 * Η συνάρτηση αυτή δημιουργεί το δεύτερο επίπεδο του δένδρου. Υπολογίζει
	 * όλα τα set κινήσεων των φαντασμάτων και κάθε ένα από αυτά τα δημιουργεί
	 * ώς παιδιά σε έναν κόμβο του πρώτου επιπέδου.
	 */
	void createSubTreeGhosts(int depth, Node parent, Room[][] Maze, int[][] currGhostsPosition) {
		Room[][] MazeCopy;
		ArrayList<int[][]> avPos = PacmanUtilities.allGhostMoves(Maze, currGhostsPosition);
		// Καλώ τη συνάρτηση getPacPos της κλάσης Creature με όρισμα το Maze,
		// στο οποίο θα υπάρχει
		// η ανανεωμένη, για κάθε κόμβο του πρώτου επιπέδου, θέση του Pacman
		// αφού περνιέται μέσω της createSubTreePacman
		int[] currPacPos = getPacPos(Maze);

		for (int i = 0; i < avPos.size(); i++) {
			// Κάνε το αντίγραφο του χώρου και αποθήκευσε την κίνηση του
			// φαντάσματος σε αυτό
			MazeCopy = PacmanUtilities.copy(Maze);
			PacmanUtilities.moveGhosts(MazeCopy, currGhostsPosition, avPos.get(i));
			// Κάθε set κινήσεων των φαντασμάτων εισάγεται ώς παιδί στον parent
			// κόμβο του πρώτου επιπέδου.
			// Αυτό που διφοροποιεί τους κόμβους αυτού του επιπέδου είναι το
			// MazeCopy που είναι διαφορετικό για κάθε set
			// κινήσεων, δηλαδή για κάθε επανάληψη της "for".
			parent.getChildren().add(new Node(depth, parent, currPacPos[0], currPacPos[1], parent.getMove(), MazeCopy));
		}

	}

	public static int[] getPacPos(Room[][] Maze) {
		int[] pacmanPos = new int[2];
		for (int i = 0; i < PacmanUtilities.numberOfRows; i++) {
			for (int j = 0; j < PacmanUtilities.numberOfColumns; j++) {
				if (Maze[i][j].isPacman()) {
					pacmanPos[0] = i;
					pacmanPos[1] = j;
					return pacmanPos;
				}
			}
		}
		return pacmanPos;
	}

	public boolean[] comAvPos(Room[][] Maze, int[][] currentPos, int[] moves, int currentGhost) {

		boolean[] availablePositions = { true, true, true, true };

		int[][] newPos = new int[4][2];

		for (int i = 0; i < 4; i++) {

			if (Maze[currentPos[currentGhost][0]][currentPos[currentGhost][1]].walls[i] == 0) {
				availablePositions[i] = false;
				continue;
			}

			if (PacmanUtilities.flagColision(Maze, currentPos[currentGhost], i)) {
				availablePositions[i] = false;
			}

			else if (currentGhost == 0)
				continue;

			else {
				switch (i) {
				case Room.WEST:
					newPos[currentGhost][0] = currentPos[currentGhost][0];
					newPos[currentGhost][1] = currentPos[currentGhost][1] - 1;
					break;
				case Room.SOUTH:
					newPos[currentGhost][0] = currentPos[currentGhost][0] + 1;
					newPos[currentGhost][1] = currentPos[currentGhost][1];
					break;
				case Room.EAST:
					newPos[currentGhost][0] = currentPos[currentGhost][0];
					newPos[currentGhost][1] = currentPos[currentGhost][1] + 1;
					break;
				case Room.NORTH:
					newPos[currentGhost][0] = currentPos[currentGhost][0] - 1;
					newPos[currentGhost][1] = currentPos[currentGhost][1];

				}

				for (int j = (currentGhost - 1); j > -1; j--) {
					switch (moves[j]) {
					case Room.WEST:
						newPos[j][0] = currentPos[j][0];
						newPos[j][1] = currentPos[j][1] - 1;
						break;
					case Room.SOUTH:
						newPos[j][0] = currentPos[j][0] + 1;
						newPos[j][1] = currentPos[j][1];
						break;
					case Room.EAST:
						newPos[j][0] = currentPos[j][0];
						newPos[j][1] = currentPos[j][1] + 1;
						break;
					case Room.NORTH:
						newPos[j][0] = currentPos[j][0] - 1;
						newPos[j][1] = currentPos[j][1];
						// break;
					}

					if ((newPos[currentGhost][0] == newPos[j][0]) && (newPos[currentGhost][1] == newPos[j][1])) {

						availablePositions[i] = false;
						continue;
					}

					if ((newPos[currentGhost][0] == currentPos[j][0]) && (newPos[currentGhost][1] == currentPos[j][1])
							&& (newPos[j][0] == currentPos[currentGhost][0])
							&& (newPos[j][1] == currentPos[currentGhost][1])) {

						availablePositions[i] = false;

					}
				}
			}
		}

		return availablePositions;
	}

	public int comBestPos(boolean[] availablePositions, int[] pacmanPosition, int[] currentPos, int[] suggestedMoves,
			double penaltyMove) {

		int[] newVerticalDifference = new int[2];
		for (int i = 0; i < 2; i++)
			newVerticalDifference[i] = currentPos[i] - pacmanPosition[i];

		int x = -1;
		int[] distanceSquared = new int[4];
		for (int i = 0; i < 4; i++) {
			if (availablePositions[i] == true) {

				int pacMove = moveCalc(prevPacPos, pacmanPosition);
				// Άν πρέπει όλα τα φαντάσματα να μετακινηθούν προς μία
				// κατεύθυνση, πήγαινε προς αυτήν
				if (suggestedMoves[0] == i || suggestedMoves[1] == i) {
					return i;
				}
				/////////////////////////////////////////////////////////////////////////////////////

				switch (pacMove) {
				case Room.WEST:
					if (currentPos[1] < pacmanPosition[1]) {
						if (currentPos[0] < pacmanPosition[0]) {
							x = Room.SOUTH;
						} else {
							x = Room.NORTH;
						}
					}
					break;
				case Room.SOUTH:
					if (currentPos[0] > pacmanPosition[0]) {
						if (currentPos[1] > pacmanPosition[1]) {
							x = Room.WEST;
						} else {
							x = Room.EAST;
						}
					}
					break;
				case Room.EAST:
					if (currentPos[1] > pacmanPosition[1]) {
						if (currentPos[0] > pacmanPosition[0]) {
							x = Room.NORTH;
						} else {
							x = Room.SOUTH;
						}
					}
					break;
				case Room.NORTH:
					if (currentPos[0] < pacmanPosition[0]) {
						if (currentPos[1] > pacmanPosition[1]) {
							x = Room.WEST;
						} else {
							x = Room.EAST;
						}
					}
					break;
				}

				/////////////////////////////////////////////////////////////////////////////////

				switch (i) {
				case Room.WEST:
					newVerticalDifference[1]--;
					break;
				case Room.SOUTH:
					newVerticalDifference[0]++;
					break;
				case Room.EAST:
					newVerticalDifference[1]++;
					break;
				case Room.NORTH:
					newVerticalDifference[0]--;
					break;
				}
				distanceSquared[i] = newVerticalDifference[0] * newVerticalDifference[0]
						+ newVerticalDifference[1] * newVerticalDifference[1];

				// Δώσε ποινή αν η κίνηση είναι τέτοια που το επιβάλλει
				if (i == penaltyMove) {
					distanceSquared[i] = PacmanUtilities.numberOfRows * PacmanUtilities.numberOfRows
							+ PacmanUtilities.numberOfColumns * PacmanUtilities.numberOfColumns;
				}
				////////////////////////////////////////////////////
			} else
				distanceSquared[i] = PacmanUtilities.numberOfRows * PacmanUtilities.numberOfRows
						+ PacmanUtilities.numberOfColumns * PacmanUtilities.numberOfColumns + 1;
		}
		if (x != -1) {
			if (availablePositions[x] == true) {
				return x;
			}
		}
		int minDistance = distanceSquared[0];
		int minPosition = 0;

		for (int i = 1; i < 4; i++) {
			if (minDistance > distanceSquared[i]) {
				minDistance = distanceSquared[i];
				minPosition = i;
			}

		}

		return minPosition;
	}

	public int[] calculateNextGhostPosition(Room[][] Maze, int[][] currentPos) {

		int[] moves = new int[PacmanUtilities.numberOfGhosts];

		int[] pacmanPosition = new int[2];
		pacmanPosition = getPacPos(Maze);

		// Βρές αν υπάρχουν "ομαδικές" κινήσεις για τα φαντάσματα
		///////////////////////////////////////////////////////////
		boolean[] isPacmanOnOneSideOfGhosts = { true, true, true, true };
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < PacmanUtilities.numberOfGhosts; j++) {
				switch (i) {
				case Room.WEST:
					if (currentPos[j][1] <= pacmanPosition[1]) {
						isPacmanOnOneSideOfGhosts[i] = false;
					}
					break;
				case Room.SOUTH:
					if (currentPos[j][0] >= pacmanPosition[0]) {
						isPacmanOnOneSideOfGhosts[i] = false;
					}
					break;
				case Room.EAST:
					if (currentPos[j][1] >= pacmanPosition[1]) {
						isPacmanOnOneSideOfGhosts[i] = false;
					}
					break;
				case Room.NORTH:
					if (currentPos[j][0] <= pacmanPosition[0]) {
						isPacmanOnOneSideOfGhosts[i] = false;
					}
				}
			}
		}
		////////////////////////////////////////////////////////////

		// Βρές τις "ομαδικές" κινήσεις για τα φαντάσματα
		int[] suggestedMoves = { -1, -1 };
		int count = 0;
		for (int i = 0; i < 4; i++) {
			if (isPacmanOnOneSideOfGhosts[i]) {
				suggestedMoves[count++] = i;
			}
		}
		////////////////////////////////////////////////////////

		for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
			double penaltyMove = -1;
			moves[i] = comBestPos(comAvPos(Maze, currentPos, moves, i), pacmanPosition, currentPos[i], suggestedMoves,
					penaltyMove);

			for (int j = i - 1; j > -1; j--) {
				switch (moves[i]) {
				/*
				 * Άν θέλει να πάει αριστερά ή δεξιά και υπάρχει ήδη φάντασμα
				 * δεξιά ή αριστερά του που θέλει //και αυτό να πάει δεξιά ή
				 * αριστερά αντίστοιχα, δώσε επιβάρυνση γι αυτή την κίνηση
				 */ case Room.WEST:
				case Room.EAST:
					if (currentPos[i][1] == currentPos[j][1] && moves[j] == moves[i]) {
						penaltyMove = moves[i];
					}
					break;
				/*
				 * Άν θέλει να πάει πάνω ή κάτω και υπάρχει ήδη φάντασμα πάνω ή
				 * κάτω του που θέλει //και αυτό να πάει πάνω ή κάτω αντίστοιχα,
				 * δώσε επιβάρυνση γι αυτή την κίνηση
				 */ case Room.SOUTH:
				case Room.NORTH:
					if (currentPos[i][0] == currentPos[j][0] && moves[j] == moves[i]) {
						penaltyMove = moves[i];
					}
					break;
				}
				if (penaltyMove != -1) {
					moves[i] = comBestPos(comAvPos(Maze, currentPos, moves, i), pacmanPosition, currentPos[i],
							suggestedMoves, penaltyMove);
				}
			}
			int[][] lf = lastFlags(Maze);
			if (lf != null) {
				if (lf[1][0] == -1) {
					if (Node.pointToPointDistance(lf[0], pacmanPosition[0], pacmanPosition[1]) > 3 && i > 0) {
						moves[i] = protectFlag(lf[0], currentPos[i], comAvPos(Maze, currentPos, moves, i),
								pacmanPosition);
					}
				} else {
					if (Node.pointToPointDistance(lf[0], pacmanPosition[0], pacmanPosition[1]) > 3  && i < 2) {
						moves[i] = protectFlag(lf[0], currentPos[i], comAvPos(Maze, currentPos, moves, i),
								pacmanPosition);
					}
					if (Node.pointToPointDistance(lf[1], pacmanPosition[0], pacmanPosition[1]) > 3  && i > 1) {
						moves[i] = protectFlag(lf[1], currentPos[i], comAvPos(Maze, currentPos, moves, i),
								pacmanPosition);
					}
				}
			}
		}

		prevPacPos = pacmanPosition;
		return moves;

	}

	public int moveCalc(int[] prev, int[] curr) {
		if (prev[1] == curr[1] && (prev[0] - 1) == curr[0]) {
			return Room.NORTH;
		} else if (prev[0] == curr[0] && (prev[1] + 1) == curr[0]) {
			return Room.EAST;
		} else if (prev[1] == curr[1] && (prev[0] + 1) == curr[0]) {
			return Room.SOUTH;
		} else {
			return Room.WEST;
		}
	}

	public boolean[] checkCollision(int[] moves, int[][] currentPos) {
		boolean[] collision = new boolean[PacmanUtilities.numberOfGhosts];

		int[][] newPos = new int[4][2];

		for (int i = 0; i < moves.length; i++) {

			if (moves[i] == 0) {
				if (currentPos[i][1] > 0) {
					newPos[i][0] = currentPos[i][0];
					newPos[i][1] = currentPos[i][1] - 1;
				} else {
					newPos[i][0] = currentPos[i][0];
					newPos[i][1] = PacmanUtilities.numberOfColumns - 1;
				}

			} else if (moves[i] == 1) {
				if (currentPos[i][0] < PacmanUtilities.numberOfRows - 1) {
					newPos[i][0] = currentPos[i][0] + 1;
					newPos[i][1] = currentPos[i][1];
				} else {
					newPos[i][0] = 0;
					newPos[i][1] = currentPos[i][1];
				}
			} else if (moves[i] == 2) {
				if (currentPos[i][1] < PacmanUtilities.numberOfColumns - 1) {
					newPos[i][0] = currentPos[i][0];
					newPos[i][1] = currentPos[i][1] + 1;
				} else {
					newPos[i][0] = currentPos[i][0];
					newPos[i][1] = 0;

				}
			} else {
				if (currentPos[i][0] > 0) {
					newPos[i][0] = currentPos[i][0] - 1;
					newPos[i][1] = currentPos[i][1];
				} else {

					newPos[i][0] = PacmanUtilities.numberOfRows - 1;
					newPos[i][1] = currentPos[i][1];

				}
			}

			collision[i] = false;
		}

		for (int i = 0; i < moves.length; i++) {
			for (int j = i + 1; j < moves.length; j++) {
				if (newPos[i][0] == newPos[j][0] && newPos[i][1] == newPos[j][1]) {

					collision[j] = true;
				}

				if (newPos[i][0] == currentPos[j][0] && newPos[i][1] == currentPos[j][1]
						&& newPos[j][0] == currentPos[i][0] && newPos[j][1] == currentPos[i][1]) {

					collision[j] = true;
				}

			}

		}
		return collision;
	}

	private int[][] lastFlags(Room[][] Maze) {

		int uncapturedFlags = 0;
		int[][] coordsOfLastUncapturedFlag = new int[2][2];
		coordsOfLastUncapturedFlag[1][0] = -1;
		l1: for (int i = 0; i < PacmanUtilities.numberOfRows; i++) {
			for (int j = 0; j < PacmanUtilities.numberOfColumns; j++) {
				if (Maze[i][j].isFlag()) {
					if (!Maze[i][j].isCapturedFlag()) {
						uncapturedFlags++;
						if (uncapturedFlags > 2) {
							break l1;
						}
						coordsOfLastUncapturedFlag[uncapturedFlags - 1][0] = i;
						coordsOfLastUncapturedFlag[uncapturedFlags - 1][1] = j;
					}
				}
			}
		}
		if (uncapturedFlags <= 2) {
			return coordsOfLastUncapturedFlag;
		} else {
			return null;
		}
	}

	private int protectFlag(int[] coords, int[] currentPos, boolean[] avPos, int[] pacmanPos) {
		int[] newPos = new int[2];
		double[] distance = new double[4];
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case Room.WEST:
				newPos[0] = currentPos[0];
				newPos[1] = currentPos[1] - 1;
				break;
			case Room.SOUTH:
				newPos[0] = currentPos[0] + 1;
				newPos[1] = currentPos[1];
				break;
			case Room.EAST:
				newPos[0] = currentPos[0];
				newPos[1] = currentPos[1] + 1;
				break;
			case Room.NORTH:
				newPos[0] = currentPos[0] - 1;
				newPos[1] = currentPos[1];

			}
			distance[i] = Node.pointToPointDistance(coords, newPos[0], newPos[1]);
		}
		double minD = Double.MAX_VALUE;
		int min = -1;
		for (int i = 0; i < 4; i++) {
			if (distance[i] < minD && avPos[i]) {
				minD = distance[i];
				min = i;
			} else if (distance[i] == minD && avPos[i]) {
				int x;
				switch (i) {
				case Room.WEST:
					newPos[0] = currentPos[0];
					newPos[1] = currentPos[1] - 1;
					break;
				case Room.SOUTH:
					newPos[0] = currentPos[0] + 1;
					newPos[1] = currentPos[1];
					break;
				case Room.EAST:
					newPos[0] = currentPos[0];
					newPos[1] = currentPos[1] + 1;
					break;
				case Room.NORTH:
					newPos[0] = currentPos[0] - 1;
					newPos[1] = currentPos[1];

				}
				x = Node.pointToPointDistance(pacmanPos, newPos[0], newPos[1]);
				int y;
				switch (min) {
				case Room.WEST:
					newPos[0] = currentPos[0];
					newPos[1] = currentPos[1] - 1;
					break;
				case Room.SOUTH:
					newPos[0] = currentPos[0] + 1;
					newPos[1] = currentPos[1];
					break;
				case Room.EAST:
					newPos[0] = currentPos[0];
					newPos[1] = currentPos[1] + 1;
					break;
				case Room.NORTH:
					newPos[0] = currentPos[0] - 1;
					newPos[1] = currentPos[1];

				}
				y = Node.pointToPointDistance(pacmanPos, newPos[0], newPos[1]);
				if (x < y) {
					minD = distance[i];
					min = i;
				}
			}
		}
		return min;
	}

}
