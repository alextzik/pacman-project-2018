package gr.auth.ee.dsproject.pacman;

public class GhostStopper extends Thread {

	static int[] direction;
	int prevPos[][];
	AbstractCreature creat;
	Labyrinth maze;
	static boolean movePlayed = false;

	public GhostStopper(AbstractCreature prey, Labyrinth lab,
			int prevPosition[][]) {
		prevPos = prevPosition;
		creat = prey;
		maze = lab;

	}

	public int[] getDirection() {
		return direction;
	}

	public static boolean isMovePlayed() {
		return movePlayed;
	}

	public void run() {

		direction = creat.calculateNextGhostPosition(maze.returnLabyrinth(),
				prevPos);
		//System.out.println("!!!!!!!!!!!!!" + direction + "!!!!!!!!!!!");
		movePlayed = true;

	}
}
