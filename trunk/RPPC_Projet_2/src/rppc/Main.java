package rppc;

public class Main {
	public static final int GREEDY = 0;
	public static final int VOISINAGES = 1;
	public static final int VNS = 2;
	
	public static void main(String[] args) {
		Affectation affectation = new Affectation();
		affectation.lireDonnee();
		
		affectation.traiter(GREEDY);
		affectation.traiter(VOISINAGES);
		affectation.traiter(VNS);
	}
}
