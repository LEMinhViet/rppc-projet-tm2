package rppc;

import java.util.List;

public class Greedy {
	private List<Tache> taches;

	private int[] affectation;
	private int[] capacite;

	private int nombreDeTache;
	private int cout;
	    
	public Greedy(List<Tache> taches, int[] capacite) {
		this.taches = taches;
		this.capacite = capacite;
		
		nombreDeTache = taches.size();
		affectation = new int[nombreDeTache];
		for (int i = 0; i < nombreDeTache; i++) {
			affectation[i] = -1;
		}
	}
	
	public void traiter() {
		for (int i = 0; i < nombreDeTache; i++) {
    		taches.get(i).trierCout();
    		taches.get(i).calculerCandidate(capacite);
    		
    		if (taches.get(i).getAgentCandidate() == -1) {
    			System.out.println("NO RESULT in step " + i);
    			continue;
    		} else {
    			affectation[i] = taches.get(i).getAgentCandidate();
    			capacite[affectation[i]] -= taches.get(i).getRessource(affectation[i]);
    		}
    	}
    	
    	cout = 0;
    	for (int i = 0; i < nombreDeTache; i++) {
//    		System.out.println("tache : " + i + " with agent " + affectation[i]);
    		cout += affectation[i] != -1 ? taches.get(i).getCout(affectation[i]) : 0;
    	}
    	System.out.println("Cout total : " + cout);
    }
	
	public int[] getAffectation() {
    	return affectation;
    }
}
