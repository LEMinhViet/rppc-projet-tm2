package rppc;

import java.util.Arrays;
import java.util.List;

public class Voisinage {
	private int[] capacite;
	private List<Tache> taches;
	private int[] initial;
	
	public Voisinage(int[] initial, int[] capacite, List<Tache> taches) {
		this.initial = initial;
		this.capacite = capacite;
		this.taches = taches;
	}
	
	public void traiter() {
		int[] solutionCourant = Arrays.copyOf(initial, initial.length);
		
		for (int i = 0; i < solutionCourant.length; i++) {
			solutionCourant = chercherVoisinage(solutionCourant, i);
		}
		
		System.out.println("Cout total : " + getCout(solutionCourant));
	}
	
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
	
	// Swap neighborhood
	private int[] getVoisin_Echanger(int[] solution, int tache1, int tache2) {
		int[] newSolution = Arrays.copyOf(solution, solution.length);
		
		int tmp = newSolution[tache1];
		newSolution[tache1] = newSolution[tache2];
		newSolution[tache2] = tmp;
		
		return newSolution;
	}
	
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
