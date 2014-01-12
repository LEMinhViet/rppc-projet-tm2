package rppc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Affectation {
	public int nombreDeAgent;
	public int nombreDeTache;
	public List<Tache> taches;
	public int[] capacite;
	    
	public Affectation() {
		taches = new ArrayList<Tache>();
	}
		
	/**
     * Lire le fichier d'entrée
     * @throws IOException 
     */
    public void lireDonnee() {
    	try {
    		Scanner scanner = new Scanner(new File("data//20_200.in"));
				
			nombreDeAgent = scanner.nextInt();
			nombreDeTache = scanner.nextInt();
			
			capacite = new int[nombreDeAgent];
			
			for (int i = 0; i < nombreDeAgent; i++) {
				for (int j = 0; j < nombreDeTache; j++) {
					if (i == 0) {
						taches.add(new Tache(nombreDeAgent));
					}
					
					taches.get(j).addCout(i, scanner.nextInt());				
				}			
			}
			
			for (int i = 0; i < nombreDeAgent; i++) {
				for (int j = 0; j < nombreDeTache; j++) {
					taches.get(j).addRessource(i, scanner.nextInt());				
				}			
			}			
		
			for (int i = 0; i < nombreDeAgent; i++) {
				capacite[i] = scanner.nextInt();	
			}
			
			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
    
    public void traiter(int algorithme) {
    	int[] _capacite = Arrays.copyOf(capacite, capacite.length);
		if (algorithme == Main.GREEDY) {
    		Greedy greedy = new Greedy(taches, _capacite);
    		greedy.traiter();
    	} else if (algorithme == Main.VOISINAGES) {
    		Greedy greedy = new Greedy(taches, _capacite);
    		greedy.traiter();
    		
    		Voisinage voisinage = new Voisinage(greedy.getAffectation(), capacite, taches);
    		voisinage.traiter();
    	} else if (algorithme == Main.VNS) {
    		Greedy greedy = new Greedy(taches, _capacite);
    		greedy.traiter();
    		
    		VNS vns = new VNS(greedy.getAffectation(), capacite, taches);
    		vns.traiter();
    	}
    }
}
