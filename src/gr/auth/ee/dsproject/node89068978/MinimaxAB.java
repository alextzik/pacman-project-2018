package gr.auth.ee.dsproject.node89068978;

/*
 * H συγκεκριμένη κλάση αποτελεί μία υλοποίηση του γνωστού αλγορίθμου MiniMax
 * με την προσθήκη του ΑΒ-pruning για μεγαλύτερη αποδοτικότητα στην αναζήτηση
 */
public class MinimaxAB {

	private static int count;
	private static int maxDepth=1;
	//Καλύτερη, μέχρι στιγμής, διαθέσιμη επιλογή για τον MAX
	private static double a;
	//Καλύτερη, μέχρι στιγμής, διαθέσιμη επιλογή για τον MIN
	private static double b;

	public static Node minimax(Node root) {
		a=-100;
		b=100;
		//Measure of complexity
		count=0;
		return max(root, a, b);
	}

	public static Node max(Node d, double a, double b) {

		count++;
		double x;
		Node bestMaxNode;
		//Αν φτάσω στο maxDepth που έχω ορίσει ή σε φύλλο, επιστρέφω τον κόμβο
		if (d.getDepth() == maxDepth || d.leaf) {
			return d;
		} else {
			//Αν δεν είμαι στο τέλος, πηγαίνω βαθύτερα, καλώντας αρχικά τη min για κάθε παιδί του τρέχοντος κόμβου
			x=-101;
			bestMaxNode=null;
			for (int i = 0; i < d.getChildren().size(); i++) {
				Node y=(min(d.getChildren().get(i), a, b));
				//Αφού είναι έτοιμος ο ελάχιστος κόμβος, ελέγχω σε σχέση με τον καλύτερο, μέχρι στιγμης, σε αυτό το επίπεδο
				if( y.getEval() > x){
					x=y.getEval();
					bestMaxNode=y;
				}
				//Ανανεώνεται η καλύτερη διαθέσιμη τιμή του MAX σε αυτό το επίπεδο, αν είναι απαραίτητο.
				a=Math.max(a, x);
				//Αν η καλύτερη τιμή του MIN στο προηγούμενο επίπεδο είναι μικρότερη από το a, τότε μην ψάχνεις άλλο
				//επειδή όποια κίνηση και να επιστρέψεις από αυτόν τον MAX, θα είναι χειρότερη για τον MIN από αυτές
				//που έχει ήδη διαθέσιμες και συνεπώς σίγουρα θα την απορρίψει
				if(b<=a){
					break;
				}				
			}
			return bestMaxNode;
		}
	}

	public static Node min(Node d, double a, double b) {

		count++;
		double x;
		Node bestMinNode;
		//Αν φτάσω στο maxDepth που έχω ορίσει ή σε φύλλο, επιστρέφω τον κόμβο
		if (d.getDepth() == maxDepth || d.leaf) {
			return d;
		} else {
			//Αν δεν είμαι στο τέλος, πηγαίνω βαθύτερα, καλώντας αρχικά τη max για κάθε παιδί του τρέχοντος κόμβου
			x=101;
			bestMinNode=null;
			for (int i = 0; i < d.getChildren().size(); i++) {
				Node y=(max(d.getChildren().get(i), a, b));
				if( y.getEval() < x){
					x=y.getEval();
					bestMinNode=y;
				}
				//Ανανεώνεται η καλύτερη διαθέσιμη τιμή του ΜΙΝ σε αυτό το επίπεδο, αν είναι απαραίτητο.
				b=Math.min(b, x);
				//Αν η καλύτερη τιμή του MΑΧ στο προηγούμενο επίπεδο είναι μεγαλύτερη από το b, τότε μην ψάχνεις άλλο
				//επειδή όποια κίνηση και να επιστρέψεις από αυτόν τον MΙΝ, θα είναι χειρότερη για τον MΑΧ από αυτές
				//που έχει ήδη διαθέσιμες και συνεπώς σίγουρα θα την απορρίψει
				if(b<=a){
					break;
				}	
			}
			return bestMinNode;
		}
	}
}