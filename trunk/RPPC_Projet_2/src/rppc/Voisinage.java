package rppc;

import java.util.Arrays;
import java.util.List;

// L'ALGORITHME PROPOSE
public class Voisinage {
	private int[] capacite;
	private List<Tache> taches;
	private int[] initial;
	
	/**
	 * Initialiser VNS a partir de la solution initiale
	 * @param initial : la solution initiale
	 * @param capacite : le tableau "capacite" des agents 
	 * @param taches : les taches
	 */
	public Voisinage(int[] initial, int[] capacite, List<Tache> taches) {
		this.initial = initial;
		this.capacite = capacite;
		this.taches = taches;
	}
	
	/**
	 * L'algoritme propose - les voisinages sont trouves par Swap neighborhood
	 * - Chercher tous les voisinages qui contient le 1er element de la solution initiale
	 * - Trouver la meilleure solution dans les voisinages
	 * - Chercher tous les voisinages qui contient le 1er et 2eme element de la meilleure solution
	 * - ...
	 */
	public void traiter() {
		int[] solutionCourant = Arrays.copyOf(initial, initial.length);
		
		for (int i = 0; i < solutionCourant.length; i++) {
			solutionCourant = chercherVoisinage(solutionCourant, i);
		}
		
		System.out.println("Cout total : " + getCout(solutionCourant));
	}
	
	/**
	 * Chercher tous les voisinage en utilisant Swap Neighborhood
	 * @param solution : la solution initiale
	 * @param debut : la premiere tache qu'on commence a chercher
	 * @return : le meilleur voisinage
	 */
	public int[] chercherVoisinage(int[] solution, int debut) {
		int[] solutionLocal = new int[solution.length];
		int[] meuilleurSolutionLocal = solution;
		
		for (int i = debut; i < taches.size(); i++) {
			for (int j = i + 1; j < taches.size(); j++) {
				solutionLocal = getVoisin_Echanger(solution, i, j);
				
				if (valider(solutionLocal) == -1) {
					if (getCout(solutionLocal) < getCout(meuilleurSolutionLocal)) {
						meuilleurSolutionLocal = Arrays.copyOf(solutionLocal, solutionLocal.length);
					}
				}
			}
		}
		return meuilleurSolutionLocal;
	}
	
	/**
	 * Swap neighborhood : echanger 2 taches pour trouver un voisinage
	 * @param solution : solution initiale
	 * @param tache1 : tache qui va etre echange
	 * @param tache2 : tache qui va etre echange
	 * @return : un voisinage
	 */
	private int[] getVoisin_Echanger(int[] solution, int tache1, int tache2) {
		int[] newSolution = Arrays.copyOf(solution, solution.length);
		
		int tmp = newSolution[tache1];
		newSolution[tache1] = newSolution[tache2];
		newSolution[tache2] = tmp;
		
		return newSolution;
	}
	
	/**
	 * Calculer la cout d'execution d'une solution
	 * @param solution
	 * @return : le cout
	 */
	private int getCout(int[] solution) {
		int cout = 0;
		for (int i = 0; i < taches.size(); i++) {
			if (solution[i] == -1) {
				cout += 0;
			} else {
				cout += taches.get(i).getCout(solution[i]);
			}
		}
		return cout;
	}
	
	/**
	 * Verifier que la solution respecte la capacite des agents ou non
	 * @param solution : la solution
	 * @return : -1 si respecte, si non, retourner la tache qui ne respecte pas 
	 */
	private int valider(int[] solution) {
		int[] ressource = new int[capacite.length];
		
		for (int i = 0; i < taches.size(); i++) {
			if (solution[i] == -1) {
				return -1;
			}
			ressource[solution[i]] += taches.get(i).getRessource(solution[i]);
		}
		
		for (int i = 0; i < capacite.length; i++) {
			if (ressource[i] > capacite[i]) {
				return i;
			}
		}
		return -1;
	}
}
