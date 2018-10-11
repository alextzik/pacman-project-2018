package gr.auth.ee.dsproject.node89068978;

import gr.auth.ee.dsproject.pacman.PacmanUtilities;
import gr.auth.ee.dsproject.pacman.Room;

import java.util.ArrayList;

public class Node {

	int nodeX;
	int nodeY;
	int depth;
	int nodeMove;
	double nodeEvaluation;
	int[][] currentGhostPos;
	int[][] flagPos;
	boolean[] currentFlagStatus;
	Room[][] Maze;

	public boolean leaf;
	Node parent;
	ArrayList<Node> children = new ArrayList<Node>();

	// No argument constructor
	public Node() {
		nodeX = -1;
		nodeY = -1;
		nodeMove = -1;
		depth = 0;
		parent = null;
		Maze = null;
		currentGhostPos = null;
		flagPos = null;
		currentFlagStatus = null;
	}

	/*
	 * Constructor: Παίρνει ως ορίσματα το χώρο του παιχνιδιού(Space),τις
	 * συντεταγμένες του κόμβου (nodeX, nodeY), το βάθος του στο δέντρο, τον
	 * "πατέρα" του και την κίνηση που αντιπροσωπεύει ο συγκεκριμένος
	 * κόμβος(move). Παράλληλα, καλεί τις σχετικές συναρτήσεις για την εύρεση
	 * των φαντασμάτων(currentGhostPos), των σημαιών(flagPos), τον έλεγχο της
	 * κατάστασης της κάθε σημαίας(currentFlagStatus) και την αξιολόγηση του
	 * κόμβου evaluate().
	 */
	public Node(int dep, Node par, int x, int y, int move, Room[][] Space) {

		depth = dep;
		parent = par;
		nodeX = x;
		nodeY = y;
		nodeMove = move;
		Maze = Space;
		currentGhostPos = findGhosts();
		flagPos = findFlags();
		currentFlagStatus = checkFlags();
		nodeEvaluation = evaluate();

	}

	/*
	 * Find ghost position: Εκτελεί γραμμική αναζήτηση σε όλο το μήκος και
	 * πλάτος του Maze προκειμένου να εντοπίσει όλα τα φαντάσματα, επιστρέφοντας
	 * τη θέση τους σε έναν πίνακα.
	 */
	public int[][] findGhosts() {

		int count = 0;
		int[][] ghostPos = new int[PacmanUtilities.numberOfGhosts][2];
		for (int i = 0; i < PacmanUtilities.numberOfRows; i++) {
			for (int j = 0; j < PacmanUtilities.numberOfColumns; j++) {
				if (Maze[i][j].isGhost()) {
					if (count == 4) {
						break;
					}
					ghostPos[count][0] = i;
					ghostPos[count][1] = j;
					count++;
				}
			}
		}
		return ghostPos;
	}

	/*
	 * Find flag position: Εκτελεί γραμμική αναζήτηση σε όλο το μήκος του Maze
	 * προκειμένου να εντοπίσει όλες τις σημαίες, επιστρέφοντας τη θέση τους σε
	 * έναν πίνακα.
	 */
	private int[][] findFlags() {

		int count = 0;
		int[][] flagPosition = new int[PacmanUtilities.numberOfFlags][2];
		for (int i = 0; i < PacmanUtilities.numberOfRows; i++) {
			for (int j = 0; j < PacmanUtilities.numberOfColumns; j++) {
				if (Maze[i][j].isFlag()) {
					flagPosition[count][0] = i;
					flagPosition[count][1] = j;
					count++;
				}
			}
		}
		return flagPosition;
	}

	/*
	 * Find flag status: Ελέγχει για όλες τις σημαίες του παιχνιδιού το άν έχουν
	 * κακτακτηθεί από τον Pacman (true) ή όχι (false), επιστρέφοντας τα
	 * αποτελέσματα σε έναν πίνακα.
	 */
	private boolean[] checkFlags() {

		boolean[] flagstatus = new boolean[PacmanUtilities.numberOfFlags];
		for (int i = 0; i < PacmanUtilities.numberOfFlags; i++) {
			flagstatus[i] = Maze[flagPos[i][0]][flagPos[i][1]].isCapturedFlag();
		}
		return flagstatus;
	}

	///// Helpful
	///// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///// functions
	///// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * #1 Find the Manhattan distance of two points: Υπολογισμός της απόστασης 2
	 * σημείων (coord[0],coord[1]) και (x,y) μέσω του αθροίσματος της
	 * κατακόρυφης και της κάθετής τους απόστασης.
	 */
	public static int pointToPointDistance(int[] coord, int x, int y) {

		int dist = Math.abs(x - coord[0]) + Math.abs(y - coord[1]);
		return dist;
	}

	/*
	 * #2 Find the distance between the node and every flag: Υπολογίζει την
	 * απόσταση του (x,y) από κάθε σημαία. Όλες αυτές οι αποστάσεις
	 * επιστρέφονται σε έναν πίνακα.
	 */
	private int[] pointToEveryFlagDistance(int x, int y) {

		int[] distance = new int[PacmanUtilities.numberOfFlags];
		for (int i = 0; i < PacmanUtilities.numberOfFlags; i++) {
			distance[i] = pointToPointDistance(flagPos[i], x, y);
		}
		return distance;
	}

	/*
	 * #3 Density of ghosts around every flag: Υπολογίζει την "πυκνότητα"
	 * φαντασμάτων γύρω από μία σημαία, αθροίζοντας τις αποστάσεις από κάθε
	 * φάντασμα. Όσο μικρότερος προκύψει αυτός ο αριθμός, τόσο πιο μακριά κατά
	 * μέσο όρο βρίσκονται τα φαντάσματα από τη συγκεκριμένη σημαία. Το
	 * αποτέλεσμα επιστρέφεται σε πίνακα.
	 */
	private double[] densityAroundFlags() {

		double[] density = new double[PacmanUtilities.numberOfFlags];
		for (int i = 0; i < PacmanUtilities.numberOfFlags; i++) {
			density[i] = 0;
			for (int j = 0; j < PacmanUtilities.numberOfGhosts; j++) {
				density[i] += pointToPointDistance(flagPos[i], currentGhostPos[j][0], currentGhostPos[j][1]);
			}
			density[i] = (PacmanUtilities.numberOfGhosts / density[i]);
		}
		return density;
	}

	/*
	 * #4 Find the best flag to chase: Βρίσκει τη σημαία η οποία θεωρείται η
	 * "ιδανική" ώστε να κυνηγηθεί από τον Pacman. Η αξιολόγηση αυτή γίνεται με
	 * 2 κριτήρια: α) Το πόσο κοντά βρίσκεται μία σημαία στον Pacman (πιο
	 * σημαντικό) (οι αποστάσεις του pacman από τις σημαίες αποθηκεύονται στον
	 * πίνακα distance) β) Το πόσα φαντάσματα υπάρχουν "γύρω" από τη σημαία
	 * (λιγότερο σημαντικό) (η πυκνότητα φαντασμάτων γύρω από κάθε σημαία
	 * αποθηκεύεται στον πίνακα density) Το πρώτο κριτήριο ποσοτικοποιείται από
	 * τη συνάρτηση pointToEveryFlagDistance() ενώ Το δεύτερο κριτήριο
	 * ποσοτικοποιείται από την pointToEveryFlagDistance(). O πίνακας
	 * flagEvaluation περιέχει την βαθμολογία κάθε σημαίας. Επιστρέφεται ο
	 * δείκτης της σημαίας με το μεγαλύτερο σκορ
	 */
	private int bestFlag() {

		double[] flagEvaluation = new double[PacmanUtilities.numberOfFlags];
		double[] density = densityAroundFlags();
		int[] distance = pointToEveryFlagDistance(nodeX, nodeY);
		// Υπολογίζει το flagEvaluation για κάθε σημαία.
		for (int i = 0; i < PacmanUtilities.numberOfFlags; i++) {
			flagEvaluation[i] = 1.5 * 1 / ((double) distance[i]) - 1.1 * density[i];
		}
		// Υπολογίζει τη σημαία που πρέπει να κυνηγήσει ο Pacman από αυτές που
		// δεν έχουν κατακτηθεί.
		int best = -1;
		double max = -Double.MAX_VALUE;
		for (int i = 0; i < PacmanUtilities.numberOfFlags; i++) {
			if (!currentFlagStatus[i] && flagEvaluation[i] > max) {
				max = flagEvaluation[i];
				best = i;
			}
		}
		/*
		 * Χρησιμοποιείται για την περίπτωση που το παιχνίδι τελειώνει με τον
		 * Pacman να κατακτά όλες τις σημαίες, για να αποφευχθεί η εμφάνιση
		 * idexOutOfBounds exception στη συνάρτηση evaluation().
		 */
		return (best != -1 ? best : 0);
	}

	/*
	 * #5 Distance of node to the closest ghost Υπολογίζει την απόσταση από το
	 * κοντινότερο φάντασμα.
	 */
	private int pointToClosestGhostDistance() {

		int minDist = pointToPointDistance(currentGhostPos[0], nodeX, nodeY);
		for (int i = 1; i < PacmanUtilities.numberOfGhosts; i++) {
			if (pointToPointDistance(currentGhostPos[i], nodeX, nodeY) < minDist) {
				minDist = pointToPointDistance(currentGhostPos[i], nodeX, nodeY);
			}
		}
		return minDist;
	}

	/*
	 * #6 Is this node a possible next ghost position?: Ελέγχει αν ο
	 * συγκεκριμένος κόμβος είναι πιθανή θέση κατάληξης φαντάσματος στην αμέσως
	 * επόμενη κίνηση. Εξετάζει δηλαδή εάν κάποιο φάντασμα απέχει απόσταση
	 * Μανχάταν ίση με 1 από τον κόμβο και ο κόμβος αυτός δεν είναι σημαία.
	 */
	private boolean possibleNextGhostPosition() {

		boolean flag = false;
		for (int i = 0; i < PacmanUtilities.numberOfGhosts; i++) {
			if (pointToPointDistance(currentGhostPos[i], nodeX, nodeY) == 1 && !Maze[nodeX][nodeY].isFlag()) {
				flag = true;
			}
		}
		return flag;
	}

	/*
	 * #7 Distance of node to the closest edge: Υπολογίζει την κοντινότερη
	 * απόσταση τοίχου που βρίσκεται περιμετρικά, από τον εν λόγω κόμβο.
	 */
	private int distToEdge() {

		int[] edge = new int[4];
		edge[0] = nodeX;
		edge[1] = PacmanUtilities.numberOfRows - nodeX - 1;
		edge[2] = nodeY;
		edge[3] = PacmanUtilities.numberOfColumns - nodeY - 1;
		int min = edge[0];
		for (int i = 1; i < 4; i++) {
			if (edge[i] < min) {
				min = edge[i];
			}
		}
		return min;
	}

	/*
	 * #8 Is this node a leaf?: Ελέγχει αν ο κόμβος είναι φύλλο και αποθηκεύει
	 * το λογικό αποτέλεσμα στο instance variable leaf. Ώς φύλλα θεωρούμε
	 * κόμβους οι οποίοι είναι τερματικοί κόμβοι του δένδρου. Φτάνοντας σε
	 * αυτούς είτε τελειώνει το παιχνίδι, είτε αντιπροσωπεύουν την καλύτερη
	 * κίνηση και συνεπώς δε χρειάζεται να προχωρήσουμε βαθύτερα. Καλύτερη
	 * κίνηση είναι αυτή που μας οδηγεί σε σημαία, οπότε σίγουρα θέλουμε να την
	 * επιλέξουμε, χωρίς εξερεύνηση της κίνησης των φαντασμάτων. Κίνηση που
	 * τελειώνει το παιχνίδι είναι αυτή που μας δίνει nodeEvaluation=-100 και που
	 * ταυτόχρονα δεν προέρχεται από κόμβο που παριστά σημαία. Αν προέρχεται από
	 * κόμβο-σημαία, τότε ένα φάντασμα που βρίσκεται δίπλα στον Pacman δε θα
	 * μπορέσει να κινηθεί προς τη σημαία (δηλαδή να ανταλλάξει θέση με αυτόν),
	 * ούτε να παραμείνει ακίνητο. Συνεπώς θα απομακρυνθεί από τον Pacman. Έτσι,
	 * υπάρχει μια αρκετά καλή πιθανότητα ο Pacman να γλυτώσει. Για να είμαστε
	 * σίγουροι, συνεχίζουμε την ανάπτυξη του δένδρου ώστε να δούμε και τις
	 * κινήσεις των υπολοίπων φαντασμάτων, από τις οποίες τελικά θα καθοριστεί
	 * το αν τελικά ο Pacman ξεφεύγει ή όχι.
	 * 
	 */
	public boolean isLeaf(boolean wasOnFlag) {
		leaf = (nodeEvaluation == 100 || (nodeEvaluation == -100 && !wasOnFlag));
		return leaf;
	}

	// #9 Getter for nodeMove
	public int getMove() {
		return nodeMove;
	}

	// #10 Getter for depth
	public int getDepth() {
		return depth;
	}

	// #11 Getter for nodeEvaluation
	public double getEval() {
		return nodeEvaluation;
	}
	
	// #12 Getter for children
	public ArrayList<Node> getChildren() {
		return children;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * #8 Evaluate this node: Η συγκεκριμένη συνάρτηση αξιολογεί το πόσο καλός
	 * είναι ο συγκεκριμένος κόμβος για να αποτελέσει την επόμενη κίνηση του
	 * Pacman. Ξεκινάει με τις δύο "ακραίες" περιπτώσεις του να είναι θέση όπου
	 * θα καταλήξει σίγουρα φάντασμα (παίρνοντας τη μέγιστη αρνητική τιμή) και
	 * του να είναι θέση στην οποία υπάρχει σημαία (παίρνοντας τη μέγιστη θετική
	 * τιμή). Σε κάθε άλλη περίπτωση, ελέγχεται αν ο συγκεκριμένος κόμβος είναι
	 * πιθανή, αλλά όχι σίγουρη, επόμενη θέση φαντάσματος και υπολογίζεται η
	 * τελική τιμή της συνάρτησης αξιολόγησης, ανάλογα με την απόσταση από το
	 * κοντινότερο φάντασμα (-), την απόσταση από την καλύτερη σημαία (+) που
	 * μπορεί να κυνηγηθεί και την απόσταση από τους τοίχους που βρίσκονται
	 * περιμετρικά (-).
	 */
	private double evaluate() {

		double evaluation = 0;
		if (Maze[nodeX][nodeY].isGhost() || (possibleNextGhostPosition() && nodeMove % 2 == 1)  ) {
			evaluation = -1000;
		} else if (Maze[nodeX][nodeY].isFlag() && !Maze[nodeX][nodeY].isCapturedFlag()) {
			evaluation = 1000;
		} else {
			if (possibleNextGhostPosition()) {
				evaluation -= 500;
			}
			evaluation += (-0.03 * (1 / ((double) pointToClosestGhostDistance())) * 100
					+ 10 * (1 / ((double) pointToPointDistance(flagPos[bestFlag()], nodeX, nodeY))) * 100
					+0* (0.05 * (double) (distToEdge()) * 100)) / 20;
		}

		return (evaluation / 10);
	}

}
