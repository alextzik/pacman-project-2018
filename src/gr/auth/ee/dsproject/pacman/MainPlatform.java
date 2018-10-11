package gr.auth.ee.dsproject.pacman;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

public class MainPlatform {

	/**
	 * @param args
	 */
	private static int limit;

	protected static JFrame frame;
	protected static Labyrinth maze;

	protected static int[] flagsCaught = new int[4];
	static int countflags = 0;

	private static JComboBox teamOne;
	private static JComboBox teamTwo;

	private static JButton generateMaze;
	private static JButton chase;
	private static JButton swap_chase;
	private static JButton quit;

	private static String PlayerOne = "gr.auth.ee.dsproject.pacman.Creature";
	private static String PlayerTwo = "gr.auth.ee.dsproject.pacman.Creature";

	private static String preyIcon = "pacman.gif";
	private static String predatorIcon = "ghosts.gif";

	private static String[] teamNames = { "Test", "8906_8978" };
	private static String[] teamClasses = {
			"gr.auth.ee.dsproject.pacman.Creature", "gr.auth.ee.dsproject.node89068978.Creature" };

	/*
	 * private static boolean preyCaught(Labyrinth lab, int numOfGhosts,
	 * int[]preyPrevPos, int [] preyCurrPos, int predPrevPos[][], int [][]
	 * predCurrPos) { boolean answer = false;
	 * 
	 * for (int i=0; i < numOfGhosts; i++){
	 * 
	 * if (preyCurrPos[0] == predCurrPos[i][0] && preyCurrPos[1] ==
	 * predCurrPos[i][1] ) { answer = true; } else answer = false; }
	 * //maze.returnLabyrinth
	 * ()[predatorPreviousPos[0]][predatorPreviousPos[1]].isPrey() ||
	 * !maze.returnLabyrinth
	 * ()[preyPreviousPos[0]][preyPreviousPos[1]].isPredator()) return answer; }
	 */

	private static String showScore() {
		// Retrieve all Elements, for transformation
		Vector prey_number = new Vector(1, 1);
		Vector prey_AEM = new Vector(1, 1);
		Vector prey_Score = new Vector(1, 1);
		Vector predator_number = new Vector(1, 1);
		Vector predator_AEM = new Vector(1, 1);
		Vector predator_Score = new Vector(1, 1);
		Vector number_of_steps = new Vector(1, 1);

		File inputFile = new File("GameLog.txt");
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(
					new FileInputStream(inputFile)));
			String line;
			while ((line = r.readLine()) != null) {
				// For each line, retrieve the elements...
				StringTokenizer parser = new StringTokenizer(line, "\t");
				String str_prey_number = parser.nextToken();
				String str_prey_AEM = parser.nextToken();
				String str_prey_Score = parser.nextToken();
				String str_predator_number = parser.nextToken();
				String str_predator_AEM = parser.nextToken();
				String str_predator_Score = parser.nextToken();
				String str_number_of_steps = parser.nextToken();

				if (prey_number.contains(str_prey_number)) {
					int prey_pos = prey_number.indexOf(str_prey_number);
					float previous_score = (float) (Float.parseFloat(prey_Score
							.elementAt(prey_pos).toString()));
					float current_score = (float) (Float
							.parseFloat(str_prey_Score));
					float final_score = previous_score + current_score;
					prey_Score.removeElementAt(prey_pos);
					prey_Score.insertElementAt(final_score + "", prey_pos);
				} else {
					prey_number.add(str_prey_number);
					prey_AEM.add(str_prey_AEM);
					prey_Score.add(str_prey_Score);
				}

				if (predator_number.contains(str_predator_number)) {
					int predator_pos = predator_number
							.indexOf(str_predator_number);
					float previous_score = (float) (Float
							.parseFloat(predator_Score.elementAt(predator_pos)
									.toString()));
					float current_score = (float) (Float
							.parseFloat(str_predator_Score));
					float final_score = previous_score + current_score;
					predator_Score.removeElementAt(predator_pos);
					predator_Score.insertElementAt(final_score + "",
							predator_pos);
				} else {
					predator_number.add(str_predator_number);
					predator_AEM.add(str_predator_AEM);
					predator_Score.add(str_predator_Score);
				}
				number_of_steps.add(str_number_of_steps);
			}
		} catch (IOException ioException) {
			System.out.println(ioException);
		}

		String output = " TEAM No         TEAM Name        Score (Pacman)   Score (Ghosts)   FINAL \n=======================================================\n";

		for (int i = 0; i < prey_number.size(); i++) {
			String pr_team_number = prey_number.elementAt(i).toString();
			float pr_score = (float) (Float.parseFloat(prey_Score.elementAt(i)
					.toString()));
			float pd_score = 0;
			int other_pos = predator_number.indexOf(pr_team_number);
			if (other_pos != -1) {
				pd_score = (float) (Float.parseFloat(predator_Score.elementAt(
						other_pos).toString()));
			}
			float score = pr_score + pd_score;

			output += pr_team_number + "       "
					+ prey_AEM.elementAt(i).toString() + "           ";
			output += pr_score + "                " + pd_score
					+ "                 " + score + "\n";
		}

		for (int i = 0; i < predator_number.size(); i++) {
			String pd_team_number = predator_number.elementAt(i).toString();
			if (prey_number.contains(pd_team_number)) {

			} else {
				float pd_score = (float) (Float.parseFloat(predator_Score
						.elementAt(i).toString()));
				float pr_score = 0;
				int other_pos = prey_number.indexOf(pd_team_number);
				if (other_pos != -1) {
					pr_score = (float) (Float.parseFloat(prey_Score.elementAt(
							other_pos).toString()));
				}
				float score = pr_score + pd_score;

				output += pd_team_number + "       "
						+ predator_AEM.elementAt(i).toString() + "           ";
				output += pr_score + "                " + pd_score
						+ "                 " + score + "\n";
			}
		}
		return output;

	}

	private static void createAndShowGUI() {

		JFrame.setDefaultLookAndFeelDecorated(false);

		frame = new JFrame("MAZE");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		maze = new Labyrinth();
		JPanel buttonPanel = new JPanel();
		BoxLayout horizontal = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
		JPanel teamsPanel = new JPanel();
		generateMaze = new JButton("Generate Maze");
		chase = new JButton("Chase");
		swap_chase = new JButton("Swap and Chase");
		quit = new JButton("Quit");

		teamOne = new JComboBox(teamNames);
		teamTwo = new JComboBox(teamNames);
		teamOne.setSelectedIndex(0);
		teamTwo.setSelectedIndex(0);

		JLabel label = new JLabel("THE Pacman GAME!!!", JLabel.CENTER);

		teamsPanel.setLayout(new BorderLayout());
		teamsPanel.add("West", teamOne);
		teamsPanel.add("East", teamTwo);
		teamsPanel.add("Center", label);
		teamsPanel.add("South", buttonPanel);

		buttonPanel.add(generateMaze);
		buttonPanel.add(chase);
		buttonPanel.add(swap_chase);
		buttonPanel.add(quit);

		frame.setLayout(new BorderLayout());
		frame.add("Center", teamsPanel);
		frame.add("South", buttonPanel);

		frame.pack();
		frame.setVisible(true);

		// ---------ActionListeners------//
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});

		generateMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				chase.setEnabled(false);
				swap_chase.setEnabled(false);
				generateMaze.setEnabled(false);

				frame.repaint();
				frame.remove(maze);
				maze = new Labyrinth(PacmanUtilities.windowWIDTH,
						PacmanUtilities.windowHEIGHT,
						PacmanUtilities.numberOfRows,
						PacmanUtilities.numberOfColumns,
						PacmanUtilities.boarders, PacmanUtilities.numberOfFlags);

				maze.setFlags(PacmanUtilities.numberOfFlags);

				int[][] preyAndPredator = PacmanUtilities
						.setRandomInitialPositions(maze.returnLabyrinth(),
								PacmanUtilities.minDistanceFromPrey,
								PacmanUtilities.maxDistanceFromPrey,
								PacmanUtilities.numberOfGhosts);

				// preyPos[0] = preyAndPredator[0];
				// preyPos[1] = preyAndPredator[1];
				// predatorPos[0] = preyAndPredator[2];
				// predatorPos[1] = preyAndPredator[3];

				maze.setPrey(preyAndPredator[PacmanUtilities.numberOfGhosts]);

				for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {

					maze.setPredator(preyAndPredator[i]);

				}

				PlayerOne = teamClasses[teamOne.getSelectedIndex()];
				PlayerTwo = teamClasses[teamTwo.getSelectedIndex()];

				frame.add("North", maze);
				frame.pack();
				chase.setEnabled(true);
				swap_chase.setEnabled(false);
				generateMaze.setEnabled(false);
			}
		});

		chase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				chase.setEnabled(false);
				swap_chase.setEnabled(false);
				generateMaze.setEnabled(false);
				countflags = 0;
				for (int i = 0; i < 4; i++)
					flagsCaught[i] = 0;

				Thread t = new Thread(new Runnable() {
					@SuppressWarnings("deprecation")
					public void run() {
						maze.emptyAllRooms();
						// maze.resetCaughtFlags();
						// flagsCaught =0;
						int preyPreviousPos[] = PacmanUtilities.preyPosition;
						int predatorPreviousPos[][] = new int[PacmanUtilities.numberOfGhosts][2];
						for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
							predatorPreviousPos[i][0] = PacmanUtilities.predatorPosition[i][0];
							predatorPreviousPos[i][1] = PacmanUtilities.predatorPosition[i][1];
						}

						maze.setPrey(preyPreviousPos);

						for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
							maze.setPredator(predatorPreviousPos[i]);
						}

						AbstractCreature prey = null;
						try {
							Class preyClass = Class.forName(PlayerOne);
							Class partypes[] = new Class[1];
							partypes[0] = Boolean.TYPE;

							Constructor preyArgsConstructor = preyClass
									.getConstructor(partypes);
							Object arglist[] = new Object[1];
							arglist[0] = new Boolean(true);
							Object preyObject = preyArgsConstructor
									.newInstance(arglist);

							prey = (AbstractCreature) preyObject;
							// prey = new AbstractCreature(true);
						} catch (ClassNotFoundException ex) {
							ex.printStackTrace();
						} catch (IllegalAccessException ex) {
							ex.printStackTrace();
						} catch (InstantiationException ex) {
							ex.printStackTrace();
						} catch (NoSuchMethodException ex) {
							ex.printStackTrace();
						} catch (InvocationTargetException ex) {
							ex.printStackTrace();
						}

						// Creature predator = new Creature(false);
						AbstractCreature predator = null;
						try {
							Class predatorClass = Class.forName(PlayerTwo);
							Class partypes[] = new Class[1];
							partypes[0] = Boolean.TYPE;
							Constructor predatorArgsConstructor = predatorClass
									.getConstructor(partypes);
							Object arglist[] = new Object[1];
							arglist[0] = new Boolean(false);
							Object predatorObject = predatorArgsConstructor
									.newInstance(arglist);
							predator = (AbstractCreature) predatorObject;
						} catch (ClassNotFoundException ex) {
							ex.printStackTrace();
						} catch (IllegalAccessException ex) {
							ex.printStackTrace();
						} catch (InstantiationException ex) {
							ex.printStackTrace();
						} catch (NoSuchMethodException ex) {
							ex.printStackTrace();
						} catch (InvocationTargetException ex) {
							ex.printStackTrace();
						}

						frame.setTitle("Pacman  " + prey.getName() + "  Vs  "
								+ predator.getName());

						int preyCurrentPos[] = new int[2];
						int predatorCurrentPos[][] = new int[PacmanUtilities.numberOfGhosts][2];

						limit = -1;

						// while(limit < LabyrinthUtilities.stepLimit &&
						// (preyCaught(maze, LabyrinthUtilities.numberOfGhosts,
						// preyPreviousPos,preyCurrentPos , predatorPreviousPos,
						// predatorCurrentPos)) ){
						while (limit < PacmanUtilities.stepLimit) {

							frame.remove(maze);
							int prey_direction = 10;

							Stopper st = new Stopper(prey, maze,
									preyPreviousPos);
							st.start();
							try {
								st.join(3000);
								if (!st.movePlayed) {
									prey_direction = (int) (Math.random() * 4);
									while (maze.returnLabyrinth()[preyPreviousPos[0]][preyPreviousPos[1]].walls[prey_direction] == 0) {
										prey_direction = (int) (Math.random() * 4);
									}

									System.out
											.println("$$$$$$$ PREY  TIME OUT $$$$$$$");
									// System.out.println("$$$$$$$ "
									// + prey_direction + " $$$$$$$");
								} else {
									prey_direction = Stopper.direction;
									st.movePlayed = false;
								}
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							// System.out.println("!!!!!!!!!!!!!" +
							// prey_direction
							// + "!!!!!!!!!!!");

							// get new prey direction
							int predator_direction[] = new int[PacmanUtilities.numberOfGhosts];

							GhostStopper gs = new GhostStopper(predator, maze,
									predatorPreviousPos);
							gs.start();

							try {
								gs.join(3000);
								if (!gs.movePlayed) {
									predator_direction = PacmanUtilities
											.allGhostDirections(
													maze.returnLabyrinth(),
													predatorPreviousPos)
											.get((int) (Math.random() * PacmanUtilities
													.allGhostDirections(
															maze.returnLabyrinth(),
															predatorPreviousPos)
													.size()));

									System.out
											.println("$$$$$$$ PREDATOR  TIME OUT $$$$$$$");
									// System.out.println("$$$$$$$ "
									// + predator_direction + " $$$$$$$");
								} else {
									predator_direction = GhostStopper.direction;
									gs.movePlayed = false;
								}
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							// int prey_direction = prey
							// .calculateNextPacmanPosition(
							// maze.returnLabyrinth(),
							// preyPreviousPos);

							// get new predators
							// int predator_direction[] = new
							// int[PacmanUtilities.numberOfGhosts];
							// predator_direction = predator
							// .calculateNextGhostPosition(
							// maze.returnLabyrinth(),
							// predatorPreviousPos);

							// evaluate new prey direction

							if (!PacmanUtilities.evaluateDirection(
									maze.returnLabyrinth(), preyPreviousPos,
									prey_direction, PacmanUtilities.boarders)) {
								System.out
										.println("ERROR: suggested PREY move NOT valid!!!");
								System.out.println("Current Position: ["
										+ preyPreviousPos[0] + ", "
										+ preyPreviousPos[1] + "]");
								System.out.println("Suggested direction: "
										+ prey_direction);
								System.out.println("  evaluated new position: ["
										+ PacmanUtilities.evaluateNextPosition(
												maze.returnLabyrinth(),
												preyPreviousPos,
												prey_direction,
												PacmanUtilities.boarders)[0]
										+ ", "
										+ PacmanUtilities.evaluateNextPosition(
												maze.returnLabyrinth(),
												preyPreviousPos,
												prey_direction,
												PacmanUtilities.boarders)[1]
										+ "]");
							} else {
								preyCurrentPos = PacmanUtilities
										.evaluateNextPosition(
												maze.returnLabyrinth(),
												preyPreviousPos,
												prey_direction,
												PacmanUtilities.boarders);
							}

							// evaluate new predators positions
							for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {

								if (!PacmanUtilities.evaluateDirection(
										maze.returnLabyrinth(),
										predatorPreviousPos[i],
										predator_direction[i],
										PacmanUtilities.boarders)
										|| !(PacmanUtilities.detectColision(
												predatorPreviousPos,
												predator_direction,
												maze.returnLabyrinth(), i,
												PacmanUtilities.boarders))
										|| PacmanUtilities.flagColision(
												maze.returnLabyrinth(),
												predatorPreviousPos[i],
												predator_direction[i])) {
									System.out
											.println("ERROR: suggested PREDATOR move NOT valid!!!");
									System.out.println("Current Position: ["
											+ predatorPreviousPos[i][0] + ", "
											+ predatorPreviousPos[i][1] + "]");
									System.out.println("Suggested direction: "
											+ predator_direction);
									System.out.println("  evaluated new position: ["
											+ PacmanUtilities.evaluateNextPosition(
													maze.returnLabyrinth(),
													predatorPreviousPos[i],
													predator_direction[i],
													PacmanUtilities.boarders)[0]
											+ ", "
											+ PacmanUtilities.evaluateNextPosition(
													maze.returnLabyrinth(),
													predatorPreviousPos[i],
													predator_direction[i],
													PacmanUtilities.boarders)[1]
											+ "]");
									System.out.println(predator_direction[i]);
								} else {
									predatorCurrentPos[i] = PacmanUtilities
											.evaluateNextPosition(
													maze.returnLabyrinth(),
													predatorPreviousPos[i],
													predator_direction[i],
													PacmanUtilities.boarders);
								}

							}

							boolean end = false;
							for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
								if (((preyCurrentPos[0] == predatorPreviousPos[i][0]) && (preyCurrentPos[1] == predatorPreviousPos[i][1]))
										&& ((predatorCurrentPos[i][0] == preyPreviousPos[0]) && (predatorCurrentPos[i][1] == preyPreviousPos[1]))
										|| (preyCurrentPos[0] == predatorCurrentPos[i][0] && preyCurrentPos[1] == predatorCurrentPos[i][1])) {

									// System.out.println
									// (predatorPreviousPos[i][0]+"  "+predatorPreviousPos[i][1]);
									// System.out.println
									// (preyPreviousPos[0]+"  "+preyPreviousPos[1]);
									// System.out.println
									// (predatorCurrentPos[i][0]+"  "+predatorCurrentPos[i][1]);
									// System.out.println
									// (preyCurrentPos[0]+"  "+preyCurrentPos[1]);

									end = true;
								}
							}

							countflags = 0;
							for (int i = 0; i < 4; i++) {
								countflags = countflags + flagsCaught[i];

							}
							if (countflags == 4)
								end = true;

							if (maze.returnLabyrinth()[preyCurrentPos[0]][preyCurrentPos[1]]
									.isFlag()) {
								maze.returnLabyrinth()[preyCurrentPos[0]][preyCurrentPos[1]]
										.setAsCapturedFlag();

								if (preyCurrentPos[0] == 2
										&& preyCurrentPos[1] == 2)
									flagsCaught[0] = 1;
								if (preyCurrentPos[0] == 17
										&& preyCurrentPos[1] == 2)
									flagsCaught[1] = 1;
								if (preyCurrentPos[0] == 2
										&& preyCurrentPos[1] == 22)
									flagsCaught[2] = 1;
								if (preyCurrentPos[0] == 17
										&& preyCurrentPos[1] == 22)
									flagsCaught[3] = 1;

							}

							maze.movePrey(preyCurrentPos, preyPreviousPos);

							for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
								// maze.movePredator(predatorCurrentPos[i],
								// predatorPreviousPos[i]);
								maze.emptyPredatorCells(predatorPreviousPos[i]);
							}

							for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
								// maze.movePredator(predatorCurrentPos[i],
								// predatorPreviousPos[i]);
								maze.setPredatorCells(predatorCurrentPos[i]);
							}

							preyPreviousPos = preyCurrentPos;
							// predatorPreviousPos = predatorCurrentPos;
							for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
								predatorPreviousPos[i] = predatorCurrentPos[i];
							}

							frame.add("North", maze);
							frame.validate();
							frame.pack();
							frame.repaint();

							if (end == true)
								break;

							try {
								Thread.sleep(PacmanUtilities.timeStep);
							} catch (InterruptedException e) {
							}
							limit++;

						}

						double preyScore = PacmanUtilities.calculateScore(true,
								countflags, limit);
						double predatorScore = PacmanUtilities.calculateScore(
								false, countflags, limit);

						try {
							BufferedWriter out = new BufferedWriter(
									new FileWriter("GameLog.txt", true));

							out.write(teamNames[teamOne.getSelectedIndex()]
									+ "\t" + prey.getName() + "\t" + preyScore
									+ "\t"
									+ teamNames[teamTwo.getSelectedIndex()]
									+ "\t" + predator.getName() + "\t"
									+ predatorScore + "\t" + limit + "\n");
							// System.out.println("NO WINNER (TIE)!!!   Number of Steps: "
							// + limit);
							if (preyScore > predatorScore) {
								JOptionPane.showMessageDialog(null,
										"WINNER IS (pacman): " + prey.getName()
												+ "   Number of Steps: "
												+ limit + "\n Game Score:  "
												+ prey.getName() + "  "
												+ preyScore + "  -  "
												+ predator.getName() + "  "
												+ predatorScore, "Results...",
										JOptionPane.INFORMATION_MESSAGE,
										new ImageIcon(preyIcon));
							} else {
								JOptionPane.showMessageDialog(
										null,
										"WINNER IS (ghosts): "
												+ predator.getName()
												+ "   Number of Steps: "
												+ limit + "\n Game Score:  "
												+ prey.getName() + "  "
												+ preyScore + "  -  "
												+ predator.getName() + "  "
												+ predatorScore, "Results...",
										JOptionPane.INFORMATION_MESSAGE,
										new ImageIcon(predatorIcon));
							}

							out.close();
						} catch (IOException ioExc) {

						}

						chase.setEnabled(false);
						swap_chase.setEnabled(true);
						generateMaze.setEnabled(false);

					}
				});
				t.start();
			}
		});

		swap_chase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				chase.setEnabled(false);
				swap_chase.setEnabled(false);
				generateMaze.setEnabled(false);
				countflags = 0;
				for (int i = 0; i < 4; i++)
					flagsCaught[i] = 0;

				Thread t = new Thread(new Runnable() {
					public void run() {
						maze.emptyAllRooms();
						maze.resetCaughtFlags();

						int preyPreviousPos[] = PacmanUtilities.preyPosition;
						int predatorPreviousPos[][] = new int[PacmanUtilities.numberOfGhosts][2];
						for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
							predatorPreviousPos[i][0] = PacmanUtilities.predatorPosition[i][0];
							predatorPreviousPos[i][1] = PacmanUtilities.predatorPosition[i][1];
						}

						maze.setPrey(preyPreviousPos);

						for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
							maze.setPredator(predatorPreviousPos[i]);
						}

						AbstractCreature prey = null;
						try {
							Class preyClass = Class.forName(PlayerTwo);
							Class partypes[] = new Class[1];
							partypes[0] = Boolean.TYPE;

							Constructor preyArgsConstructor = preyClass
									.getConstructor(partypes);
							Object arglist[] = new Object[1];
							arglist[0] = new Boolean(true);
							Object preyObject = preyArgsConstructor
									.newInstance(arglist);

							prey = (AbstractCreature) preyObject;
							// prey = new AbstractCreature(true);
						} catch (ClassNotFoundException ex) {
							ex.printStackTrace();
						} catch (IllegalAccessException ex) {
							ex.printStackTrace();
						} catch (InstantiationException ex) {
							ex.printStackTrace();
						} catch (NoSuchMethodException ex) {
							ex.printStackTrace();
						} catch (InvocationTargetException ex) {
							ex.printStackTrace();
						}

						// Creature predator = new Creature(false);
						AbstractCreature predator = null;
						try {
							Class predatorClass = Class.forName(PlayerOne);
							Class partypes[] = new Class[1];
							partypes[0] = Boolean.TYPE;
							Constructor predatorArgsConstructor = predatorClass
									.getConstructor(partypes);
							Object arglist[] = new Object[1];
							arglist[0] = new Boolean(false);
							Object predatorObject = predatorArgsConstructor
									.newInstance(arglist);
							predator = (AbstractCreature) predatorObject;
						} catch (ClassNotFoundException ex) {
							ex.printStackTrace();
						} catch (IllegalAccessException ex) {
							ex.printStackTrace();
						} catch (InstantiationException ex) {
							ex.printStackTrace();
						} catch (NoSuchMethodException ex) {
							ex.printStackTrace();
						} catch (InvocationTargetException ex) {
							ex.printStackTrace();
						}

						frame.setTitle("Pacman  " + prey.getName() + "  Vs  "
								+ predator.getName());

						int preyCurrentPos[] = new int[2];
						int predatorCurrentPos[][] = new int[PacmanUtilities.numberOfGhosts][2];

						limit = -1;

						// while(limit < LabyrinthUtilities.stepLimit &&
						// (preyCaught(maze, LabyrinthUtilities.numberOfGhosts,
						// preyPreviousPos,preyCurrentPos , predatorPreviousPos,
						// predatorCurrentPos)) ){
						while (limit < PacmanUtilities.stepLimit) {

							frame.remove(maze);

							// get new prey direction
							// int prey_direction = prey
							// .calculateNextPacmanPosition(
							// maze.returnLabyrinth(),
							// preyPreviousPos);

							int prey_direction = 10;

							Stopper st = new Stopper(prey, maze,
									preyPreviousPos);
							st.start();
							try {
								st.join(3000);
								if (!st.movePlayed) {
									prey_direction = (int) (Math.random() * 4);
									while (maze.returnLabyrinth()[preyPreviousPos[0]][preyPreviousPos[1]].walls[prey_direction] == 0) {
										prey_direction = (int) (Math.random() * 4);
									}

									System.out
											.println("$$$$$$$ PREY  TIME OUT $$$$$$$");
									// System.out.println("$$$$$$$ "
									// + prey_direction + " $$$$$$$");
								} else {
									prey_direction = Stopper.direction;
									st.movePlayed = false;
								}
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							// get new predators
							int predator_direction[] = new int[PacmanUtilities.numberOfGhosts];

							GhostStopper gs = new GhostStopper(predator, maze,
									predatorPreviousPos);
							gs.start();

							try {
								gs.join(3000);
								if (!gs.movePlayed) {
									predator_direction = PacmanUtilities
											.allGhostDirections(
													maze.returnLabyrinth(),
													predatorPreviousPos)
											.get((int) (Math.random() * PacmanUtilities
													.allGhostDirections(
															maze.returnLabyrinth(),
															predatorPreviousPos)
													.size()));

									System.out
											.println("$$$$$$$ PREDATOR TIME OUT $$$$$$$");
									// System.out.println("$$$$$$$ "
									// + predator_direction + " $$$$$$$");
								} else {
									predator_direction = GhostStopper.direction;
									gs.movePlayed = false;
								}
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							// predator_direction = predator
							// .calculateNextGhostPosition(
							// maze.returnLabyrinth(),
							// predatorPreviousPos);

							// evaluate new prey direction

							if (!PacmanUtilities.evaluateDirection(
									maze.returnLabyrinth(), preyPreviousPos,
									prey_direction, PacmanUtilities.boarders)) {
								System.out
										.println("ERROR: suggested PREY move NOT valid!!!");
								System.out.println("Current Position: ["
										+ preyPreviousPos[0] + ", "
										+ preyPreviousPos[1] + "]");
								System.out.println("Suggested direction: "
										+ prey_direction);
								System.out.println("  evaluated new position: ["
										+ PacmanUtilities.evaluateNextPosition(
												maze.returnLabyrinth(),
												preyPreviousPos,
												prey_direction,
												PacmanUtilities.boarders)[0]
										+ ", "
										+ PacmanUtilities.evaluateNextPosition(
												maze.returnLabyrinth(),
												preyPreviousPos,
												prey_direction,
												PacmanUtilities.boarders)[1]
										+ "]");
							} else {
								preyCurrentPos = PacmanUtilities
										.evaluateNextPosition(
												maze.returnLabyrinth(),
												preyPreviousPos,
												prey_direction,
												PacmanUtilities.boarders);
							}

							// evaluate new predators positions

							for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {

								if (!PacmanUtilities.evaluateDirection(
										maze.returnLabyrinth(),
										predatorPreviousPos[i],
										predator_direction[i],
										PacmanUtilities.boarders)
										|| !(PacmanUtilities.detectColision(
												predatorPreviousPos,
												predator_direction,
												maze.returnLabyrinth(), i,
												PacmanUtilities.boarders))
										|| PacmanUtilities.flagColision(
												maze.returnLabyrinth(),
												predatorPreviousPos[i],
												predator_direction[i])) {
									System.out
											.println("ERROR: suggested PREDATOR move NOT valid!!!");
									System.out.println("Current Position: ["
											+ predatorPreviousPos[i][0] + ", "
											+ predatorPreviousPos[i][1] + "]");
									System.out.println("Suggested direction: "
											+ predator_direction);
									System.out.println("  evaluated new position: ["
											+ PacmanUtilities.evaluateNextPosition(
													maze.returnLabyrinth(),
													predatorPreviousPos[i],
													predator_direction[i],
													PacmanUtilities.boarders)[0]
											+ ", "
											+ PacmanUtilities.evaluateNextPosition(
													maze.returnLabyrinth(),
													predatorPreviousPos[i],
													predator_direction[i],
													PacmanUtilities.boarders)[1]
											+ "]");
								}

								/*
								 * else if (LabyrinthUtilities.colisionDetection
								 * && i-1 >= 1) { for (int j = i-1; j > 0; j--)
								 * { if (!(LabyrinthUtilities.detectColision(
								 * predatorPreviousPos[i],
								 * predator_direction[i],
								 * maze.returnLabyrinth())));
								 * System.out.println(
								 * "ERROR: suggested move NOT valid!!!");
								 * System.out.println("Current Position: [" +
								 * predatorPreviousPos[0] + ", " +
								 * predatorPreviousPos[1] + "]");
								 * System.out.println("Suggested direction: " +
								 * predator_direction);
								 * System.out.println("  evaluated new position: ["
								 * +
								 * LabyrinthUtilities.evaluateNextPosition(maze
								 * .returnLabyrinth(), predatorPreviousPos[i],
								 * predator_direction[i],
								 * LabyrinthUtilities.boarders)[0] + ", " +
								 * LabyrinthUtilities
								 * .evaluateNextPosition(maze.returnLabyrinth(),
								 * predatorPreviousPos[i],
								 * predator_direction[i],
								 * LabyrinthUtilities.boarders)[1] +"]"); } }
								 */

								else {
									predatorCurrentPos[i] = PacmanUtilities
											.evaluateNextPosition(
													maze.returnLabyrinth(),
													predatorPreviousPos[i],
													predator_direction[i],
													PacmanUtilities.boarders);

								}

							}

							boolean end = false;
							for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
								if (((preyCurrentPos[0] == predatorPreviousPos[i][0]) && (preyCurrentPos[1] == predatorPreviousPos[i][1]))
										&& ((predatorCurrentPos[i][0] == preyPreviousPos[0]) && (predatorCurrentPos[i][1] == preyPreviousPos[1]))
										|| (preyCurrentPos[0] == predatorCurrentPos[i][0] && preyCurrentPos[1] == predatorCurrentPos[i][1])) {

									// System.out.println
									// (predatorPreviousPos[i][0]+"  "+predatorPreviousPos[i][1]);
									// System.out.println
									// (preyPreviousPos[0]+"  "+preyPreviousPos[1]);
									// System.out.println
									// (predatorCurrentPos[i][0]+"  "+predatorCurrentPos[i][1]);
									// System.out.println
									// (preyCurrentPos[0]+"  "+preyCurrentPos[1]);

									end = true;
								}
							}

							countflags = 0;
							for (int i = 0; i < 4; i++) {
								countflags = countflags + flagsCaught[i];

							}
							if (countflags == 4)
								end = true;

							if (maze.returnLabyrinth()[preyCurrentPos[0]][preyCurrentPos[1]]
									.isFlag()) {
								maze.returnLabyrinth()[preyCurrentPos[0]][preyCurrentPos[1]]
										.setAsCapturedFlag();

								if (preyCurrentPos[0] == 2
										&& preyCurrentPos[1] == 2)
									flagsCaught[0] = 1;
								if (preyCurrentPos[0] == 17
										&& preyCurrentPos[1] == 2)
									flagsCaught[1] = 1;
								if (preyCurrentPos[0] == 2
										&& preyCurrentPos[1] == 22)
									flagsCaught[2] = 1;
								if (preyCurrentPos[0] == 17
										&& preyCurrentPos[1] == 22)
									flagsCaught[3] = 1;

							}

							maze.movePrey(preyCurrentPos, preyPreviousPos);

							for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
								// maze.movePredator(predatorCurrentPos[i],
								// predatorPreviousPos[i]);
								maze.emptyPredatorCells(predatorPreviousPos[i]);
							}

							for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
								// maze.movePredator(predatorCurrentPos[i],
								// predatorPreviousPos[i]);
								maze.setPredatorCells(predatorCurrentPos[i]);
							}

							preyPreviousPos = preyCurrentPos;
							// predatorPreviousPos = predatorCurrentPos;
							for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
								predatorPreviousPos[i] = predatorCurrentPos[i];
							}

							frame.add("North", maze);
							frame.validate();
							frame.pack();
							frame.repaint();

							if (end == true)
								break;

							try {
								Thread.sleep(PacmanUtilities.timeStep);
							} catch (InterruptedException e) {
							}
							limit++;

						}

						double preyScore = PacmanUtilities.calculateScore(true,
								countflags, limit);
						double predatorScore = PacmanUtilities.calculateScore(
								false, countflags, limit);

						try {
							BufferedWriter out = new BufferedWriter(
									new FileWriter("GameLog.txt", true));

							out.write(teamNames[teamTwo.getSelectedIndex()]
									+ "\t" + prey.getName() + "\t" + preyScore
									+ "\t"
									+ teamNames[teamOne.getSelectedIndex()]
									+ "\t" + predator.getName() + "\t"
									+ predatorScore + "\t" + limit + "\n");
							// System.out.println("NO WINNER (TIE)!!!   Number of Steps: "
							// + limit);
							if (preyScore > predatorScore) {
								JOptionPane.showMessageDialog(null,
										"WINNER IS (pacman): " + prey.getName()
												+ "   Number of Steps: "
												+ limit + "\n Game Score:  "
												+ prey.getName() + "  "
												+ preyScore + "  -  "
												+ predator.getName() + "  "
												+ predatorScore, "Results...",
										JOptionPane.INFORMATION_MESSAGE,
										new ImageIcon(preyIcon));
							} else {
								JOptionPane.showMessageDialog(
										null,
										"WINNER IS (ghosts): "
												+ predator.getName()
												+ "   Number of Steps: "
												+ limit + "\n Game Score:  "
												+ prey.getName() + "  "
												+ preyScore + "  -  "
												+ predator.getName() + "  "
												+ predatorScore, "Results...",
										JOptionPane.INFORMATION_MESSAGE,
										new ImageIcon(predatorIcon));
							}

							out.close();
						} catch (IOException ioExc) {

						}

						chase.setEnabled(true);
						swap_chase.setEnabled(true);
						generateMaze.setEnabled(true);
						JOptionPane.showMessageDialog(null, showScore(),
								"SCORE TABLE", JOptionPane.INFORMATION_MESSAGE);

					}
				});
				t.start();
			}
		});

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}

}
