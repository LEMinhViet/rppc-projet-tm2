package rppc;


public class Tache {
	private int[] cout;
	private int[] ressource;
	private int[] order;
	
	private int agent_candidate;
	private int distance;
	
	public Tache(int nombreDeAgent) {
		cout = new int[nombreDeAgent];
		ressource = new int[nombreDeAgent];
		order = new int[nombreDeAgent];
	}
	
	public void addCout(int i, int value) {
		cout[i] = value;
	}
	
	public void addRessource(int i, int value) {
		ressource[i] = value;
	}
	
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
	
	public int getRessource(int agent) {
		return ressource[agent];
	}
	
	public int getDistance() {
		return distance;
	}
	
	public int getAgentCandidate() {
		return agent_candidate;
	}
	
	public int getCout(int agent) {
		return cout[agent];
	}
}
