package rppc;

import java.util.Arrays;
import java.util.List;

// VARIABLE EIGHBORHOOD SEARCH
public class VNS {
	private final int MAX_NON_IMPROVE = 5000;
	
	private int nombreNonImprove = 0;
	
	private int[] initial;
	private int[] capacite;
	private List<Tache> taches;
	
	public VNS(int[] initial, int[] capacite, List<Tache> taches) {
		this.initial = initial;
		this.capacite = capacite;
		this.taches = taches;
	}
	
	public void traiter() {
		int[] solutionCourant = Arrays.copyOf(initial, initial.length);
		int[] voisin;
		int[] meilleurVoisinDeVoisin;
		
		while (true) {
			voisin = genererRandomVoisin(solutionCourant);
			meilleurVoisinDeVoisin = chercherLocal(voisin);
			
			if (valider(meilleurVoisinDeVoisin) == -1) {
				if (getCout(meilleurVoisinDeVoisin) < getCout(solutionCourant)) {
					solutionCourant = Arrays.copyOf(meilleurVoisinDeVoisin, meilleurVoisinDeVoisin.length);
					nombreNonImprove = 0;
				} else {
					nombreNonImprove++;
					if (nombreNonImprove >= MAX_NON_IMPROVE) {
						break;
					}
				}
			} else {
				nombreNonImprove++;
				if (nombreNonImprove >= MAX_NON_IMPROVE) {
					break;
				}
			}			 
		}
		
		for (int i = 0; i < taches.size(); i++) {
//    		System.out.println("tache : " + i + " with agent " + solutionCourant[i]);
    	}
    	System.out.println("Cout total : " + getCout(solutionCourant));
	}
	
	public int[] genererRandomVoisin(int[] solution) {
		int t1, t2;
		if (random(2) == 0) {
			do {
				t1 = random(taches.size());
				t2 = random(taches.size());
			} while (t1 == t2);
			
			return getVoisin_Echanger(solution, t1, t2);
		} else {
			do {
				t1 = random(taches.size());
				t2 = random(capacite.length);
			} while (t1 == t2);
			
			return getVoisin_Shift(solution, t1, t2);
		}		
	}
	
	public int[] chercherLocal(int[] solution) {
		int[] solutionLocal = new int[solution.length];
		int[] meuilleurSolutionLocal = solution;
		
		for (int i = 0; i < taches.size(); i++) {
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
	
	// Shift neighborhood
	private int[] getVoisin_Shift(int[] solution, int tache, int agent) {
		int[] newSolution = Arrays.copyOf(solution, solution.length);		
		newSolution[tache] = agent;		
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
	
	private int random(int max) {
		return (int)(Math.random() * 1000) % max;
	}
}
