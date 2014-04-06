/*
 * Copyright 2014 BeloTeam
 * 
 * This file is part of BeloteTime.
 *	
 * BeloteTime is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeloteTime is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeloteTime.  If not, see <http://www.gnu.org/licenses/>. 
 */

package fr.belotime.noyau.classesMetier;

import java.util.ArrayList;

/**
 * Classe representant un tas de cartes qui hérite de AbstractPaquetNonTrier
 * @author BeloTeam
 * @version 1.0
**/
public class Paquet extends AbstractPaquetNonTrier {

	/**
	 * Constructeur par defaut d'un paquet de 32 cartes (initialisé).
	 * */
	public Paquet() {
		// Initialisation du tas
		for (CouleurEnum c : CouleurEnum.values()) {
			for (FigureEnum f : FigureEnum.values()) {
				this.ajouter(new Carte(c, f));
			}
		}
	}

	/**
	 * Permet de couper un tas de carte
	 * @param pourcentageCartesCoupees int
	 * @return boolean true si le tas a bien ete coupe
	 * */
	public boolean couper(int pourcentageCartesCoupees) {
		int nbCarteCoupees = 0;
		boolean estCoupe = false;
		if (pourcentageCartesCoupees >= 0 && pourcentageCartesCoupees <= 100) {
			estCoupe = true;
			nbCarteCoupees = (int) (31 * (pourcentageCartesCoupees / 100));
			for (int i = 0; i < nbCarteCoupees; i++) {
				Carte c = super.getCartes().remove(i);
				estCoupe &= super.getCartes().add(c);
			}
		}
		return estCoupe;
	}

	/**
	 * Permet de melanger le paquet de carte
	 * @param nbTentativeSwitch int
	 * */
	public void melanger(int nbTentativeSwitch) {
		for (int k = 0; k < nbTentativeSwitch; k++) {
			int i = (int) (Math.random() * (32 - 1));
			int j = (int) (Math.random() * (32 - 1));
			Carte c = super.getCartes().remove(i);
			super.getCartes().add(j, c);
		}
	}

	/**
	 * Retourne la premiere carte du paquet
	 * @return Carte
	 * */
	public Carte retirerPremiereCarte() {
		Carte carteDuDessus;
		carteDuDessus = this.getCartes().get(0);
		this.supprimer(carteDuDessus);
		return carteDuDessus;
	}

	/**
	 * Ajoute une liste de cartes donnees au tas de carte
	 * @param cartes ArrayList<Carte>
	 * */
	public void reposerListeCartes(ArrayList<Carte> cartes) {
		for (Carte carte : cartes) {
			this.ajouter(carte);
		}
	}
}
