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
	 * Lire le fichier d'entr�e
	 * 
	 * @throws IOException
	 */
	public void lireDonnee(String filename) {
		try {
			Scanner scanner = new Scanner(new File(filename));

			// D'abord, lire le nombre d'agent et nombre de tache dans le
			// probleme
			nombreDeAgent = scanner.nextInt();
			nombreDeTache = scanner.nextInt();

			capacite = new int[nombreDeAgent];

			// Lire les cout d'execution
			for (int i = 0; i < nombreDeAgent; i++) {
				for (int j = 0; j < nombreDeTache; j++) {
					if (i == 0) {
						taches.add(new Tache(nombreDeAgent));
					}

					taches.get(j).addCout(i, scanner.nextInt());
				}
			}

			// Lire les ressources requis pour l'execution
			for (int i = 0; i < nombreDeAgent; i++) {
				for (int j = 0; j < nombreDeTache; j++) {
					taches.get(j).addRessource(i, scanner.nextInt());
				}
			}

			// Lire les capacites des agents
			for (int i = 0; i < nombreDeAgent; i++) {
				capacite[i] = scanner.nextInt();
			}

			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Traiter le probleme avec 1 de 3 algorithme - les NOMs des algorithmes
	 * sont dans le fichier Main.java
	 * 
	 * @param algorithme
	 *            : nom de l'algorithme
	 */
	public void traiter(int algorithme) {
		// Les algorithme modifient le tableau "capacite", donc on utilise un
		// autre tableau pour stocker
		int[] _capacite = Arrays.copyOf(capacite, capacite.length);
		long debut = System.currentTimeMillis();
		if (algorithme == Main.GREEDY) {
			Greedy greedy = new Greedy(taches, _capacite);
			if (greedy.traiter()) {
				greedy.afficherResultat();
			} else {
				System.err.println("NO RESULT ");
			}

		} else if (algorithme == Main.VOISINAGES) {
			Greedy greedy = new Greedy(taches, _capacite);
			if (greedy.traiter()) {
				Voisinage voisinage = new Voisinage(greedy.getAffectation(), capacite, taches);
				voisinage.traiter();
			} else {
				System.err.println("NO RESULT ");
			}

		} else if (algorithme == Main.VNS) {
			Greedy greedy = new Greedy(taches, _capacite);
			if (greedy.traiter()) {
				VNS vns = new VNS(greedy.getAffectation(), capacite, taches);
				vns.traiter();
			} else {
				System.err.println("NO RESULT ");
			}
		}
		System.out.println("Temps d'execution : " + (System.currentTimeMillis() - debut));
	}
}
