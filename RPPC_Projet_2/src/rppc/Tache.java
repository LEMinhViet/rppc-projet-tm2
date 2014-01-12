package rppc;


public class Tache {
	// COUT - contient les couts d'effectuer la tache par les agents
	private int[] cout;
	// RESSOURCE - contient les ressources requis d'effectuer la tache par les agents
	private int[] ressource;
	// ORDER - contient un order des agents 
	private int[] order;
	
	// Le meilleur candidate pour faire la tache
	private int agent_candidate;
		
	/**
	 * Constructor
	 * @param nombreDeAgent - nombre de agent dans le systeme
	 */
	public Tache(int nombreDeAgent) {
		cout = new int[nombreDeAgent];
		ressource = new int[nombreDeAgent];
		order = new int[nombreDeAgent];
	}
	
	/**
	 * Trier les agents dans l'ordre croissant des couts d'execution
	 */
	public void trierCout() {
		int tmp;
		
		for (int i = 0; i < order.length; i++) {
			order[i] = i;
		}
		
		for (int i = 0; i < order.length; i++) {
			for (int j = i + 1; j < order.length; j++) {
				if (cout[order[i]] > cout[order[j]]) {
					tmp = order[j];
					order[j] = order[i];
					order[i] = tmp;
				}
			}
		}		
	}
	
	/**
	 * Trier les agent dans l'ordre croissant de l'utilisation des ressources
	 */
	public void trierRessource() {
		int tmp;
		
		for (int i = 0; i < order.length; i++) {
			order[i] = i;
		}
		
		for (int i = 0; i < order.length; i++) {
			for (int j = i + 1; j < order.length; j++) {
				if (ressource[order[i]] > ressource[order[j]]) {
					tmp = order[j];
					order[j] = order[i];
					order[i] = tmp;
				}
			}
		}		
	}
	
	/**
	 * Trouver l'agent qui peut faire cette tache (capacite) avec le moins cout
	 * @param capacite : tableau "capacite" des agents
	 */
	public void calculerCandidate(int[] capacite) {
		agent_candidate = -1;
		for (int i = 0; i < order.length; i++) {
			if (capacite[order[i]] < ressource[order[i]]) {
				continue;
			}
			
			agent_candidate = order[i];
			break;
		}
	}
	
	public void addCout(int i, int value) {
		cout[i] = value;
	}
	
	public void addRessource(int i, int value) {
		ressource[i] = value;
	}
	
	public int getRessource(int agent) {
		return ressource[agent];
	}
	
	public int getCout(int agent) {
		return cout[agent];
	}
	
	public int getAgentCandidate() {
		return agent_candidate;
	}
}
