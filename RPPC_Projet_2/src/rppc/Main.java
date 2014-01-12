package rppc;

public class Main {
	public static final int GREEDY = 0;			// L'algorithme glouton 
	public static final int VOISINAGES = 1;		// L'algorithme basee sur les voisinages propose
	public static final int VNS = 2;			// La meta-heuristique Variable Neighborhood Search
	
	public static void main(String[] args) {
		Affectation affectation = new Affectation();
		// Lire les données - http://people.brunel.ac.uk/~mastjjb/jeb/orlib/gapinfo.html
		affectation.lireDonnee("data//20_200.in");
		
		// Traiter les donneés par les differents methodes
		affectation.traiter(GREEDY);
		affectation.traiter(VOISINAGES);
		affectation.traiter(VNS);
	}
}
