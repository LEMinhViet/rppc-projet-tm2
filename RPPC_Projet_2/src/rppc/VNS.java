package rppc;

import java.util.Arrays;
import java.util.List;

// VARIABLE EIGHBORHOOD SEARCH
public class VNS {
	private final int MAX_NON_IMPROVE = 1000;
	// Le nombre de l'etape qui n'a pas l'improvement
	private int nombreNonImprove = 0;
	
	private int[] initial;
	private int[] capacite;
	private List<Tache> taches;
	
	/**
	 * Initialiser VNS a partir de la solution initiale
	 * @param initial : la solution initiale
	 * @param capacite : le tableau "capacite" des agents 
	 * @param taches : les taches
	 */
	public VNS(int[] initial, int[] capacite, List<Tache> taches) {
		this.initial = initial;
		this.capacite = capacite;
		this.taches = taches;
	}
	
	/**
	 * La meta-heuristique Variable Neiborhood Search
	 * - A partir de la solution initiale, chercher un voisinage 
	 * - A partir du voisinage, chercher les voisinages du voisinage
	 * - Trouver la meilleure solution dans les voisinages et la comparer avec la solution initiale
	 * - Repeter jusqu'a ne pas trouver la mieux solution
	 */
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
		
		afficherResultat(solutionCourant);
	}
	
	/**
	 * Generer aleatoire un voisinage a partir d'une solution
	 * Il y a 2 types de voisinage : - Shift neighborhood et Swap neighborhood
	 * @param solution : solution initiale
	 * @return : voisinage de la solution
	 */
	public int[] genererRandomVoisin(int[] solution) {
		int t1, t2;
		// Choisir un type de voisinage aleatoire
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
	
	/**
	 * Chercher tous les voisinages et trouver la meilleure solution dans ces ceux-la
	 * @param solution
	 * @return
	 */
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
	 * Shift neighborhood : changer l'agent d'un tache pour trouver un voisinage
	 * @param solution : solution initiale
	 * @param tache : tache qui va etre change
	 * @param agent : agent qui va etre assinge a la tache
	 * @return : un voisinage
	 */
	private int[] getVoisin_Shift(int[] solution, int tache, int agent) {
		int[] newSolution = Arrays.copyOf(solution, solution.length);		
		newSolution[tache] = agent;		
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
	
	/**
	 * Afficher la contenue d'une solution et le cout total
	 * @param solutionCourant
	 */
	private void afficherResultat(int[] solutionCourant) {
		for (int i = 0; i < taches.size(); i++) {
//    		System.out.println("tache : " + i + " with agent " + solutionCourant[i]);
    	}
    	System.out.println("Variable Neighborhood Search - Cout total : " + getCout(solutionCourant));
	}
	
	/**
	 * Generer un valeur aleatoire entre 0 et max - 1
	 * @param max
	 * @return
	 */
	private int random(int max) {
		return (int)(Math.random() * 1000) % max;
	}
}
