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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import fr.belotime.noyau.control.Terminal;

/**
 * Classe representant une main de carte
 * @author BeloTeam
 * @version 1.0
**/
public class Main {
	
	private Map<CouleurEnum, SortedSet<Carte>> main;
	private int nbCarte;
	private final int TAILLEMAX;

	/**
	 * Constructeur surcharge de Main
	 * @param TAILLEMAX int
	 * */
	public Main(final int TAILLEMAX) {
		this.main = new HashMap<CouleurEnum, SortedSet<Carte>>();
		this.nbCarte = 0;
		this.TAILLEMAX = TAILLEMAX;
		for(CouleurEnum c : CouleurEnum.values()) {
			this.main.put(c, new TreeSet<Carte>()); 
		}
	}

	/**
	 * Constructeur par defaut de Main
	 * */
	public Main() {
		this.main = new HashMap<CouleurEnum, SortedSet<Carte>>();
		this.nbCarte = 0;
		this.TAILLEMAX = 8;
		for(CouleurEnum c : CouleurEnum.values()) {
			this.main.put(c, new TreeSet<Carte>()); 
		}
	}

	/**
	 * Permet de recuperer la main triee
	 * @return Map<CouleurEnum, SortedSet<Carte>>
	 * */
	public Map<CouleurEnum, SortedSet<Carte>> getMain() {
		return main;
	}

	/**
	 * Permet de redéfinir le nombre de carte dans la main lorsque l'on ajoute une liste de cartes
	 * @param nbCartesAjoutes
	 */
	public void setSize(int nbCartesAjoutes){
		nbCarte = nbCartesAjoutes;
	}

	/**
	 * Permet de calculer le total de points d'une couleur donnee de la main
	 * @param atout CouleurEnum
	 * @return int total de points
	 * */
	public int calculerValeurMain(CouleurEnum atout) {
		int valeur = 0;
		Set<CouleurEnum> keys = this.main.keySet();
		for (Iterator<CouleurEnum> itPaquet = keys.iterator(); itPaquet.hasNext();) {
			SortedSet<Carte> paquetCouleur = new TreeSet<Carte>(main.get(itPaquet.next()));
			for (Iterator<Carte> itPaquetCouleur = paquetCouleur.iterator(); itPaquetCouleur.hasNext();) {
				valeur += itPaquetCouleur.next().calculerValeurCarte(atout);
			}
		}
		return valeur;
	}



	/**
	 * Permet d'ajouter une carte a la main
	 * @param c Carte
	 * @param couleurAtout CouleurEnum 
	 * @return boolean succes ou echec de l'ajout
	 * */
	public boolean ajouter(Carte c, final CouleurEnum couleurAtout) {
		boolean estAjoute = false;
		if (this.nbCarte < this.TAILLEMAX) {
			// Si la carte est un atout 
			// on redefinit le TreeSet d'atout dans la HashMap avec un comparator adéquat
			if(c.getCouleur().equals(couleurAtout)){
				CompAtout comparator = new CompAtout(couleurAtout);
				SortedSet<Carte> paquetCouleur = new TreeSet<Carte>(comparator);
				paquetCouleur.addAll(this.main.get(couleurAtout));
				this.main.remove(couleurAtout);
				this.main.put(couleurAtout, paquetCouleur);
			}
			estAjoute = this.main.get(c.getCouleur()).add(c);
			if (estAjoute) {
				this.nbCarte++;
			}
		}
		return estAjoute;
	}

	/**
	 * Permet de supprimer une carte a la main
	 * @param c Carte
	 * @return boolean succes ou echec de la supression
	 * */
	public boolean supprimer(Carte c) {
		boolean estSupprimer = false;
		if (this.nbCarte > 0) {
			estSupprimer = this.main.get(c.getCouleur()).remove(c);
			if (estSupprimer) {
				this.nbCarte--;
			}
		}
		return estSupprimer;
	}


	/**
	 * Getter sur la taille de la main
	 * @return int taille de la main
	 * */
	public int getTailleMain() {
		return this.nbCarte;
	}


	/**
	 * Retourne la carte la plus forte en valeur d'une couleur donnee
	 * @param c CouleurEnum
	 * @return Carte carte la plus forte
	 * */
	public Carte getPlusForteCarteNormale(CouleurEnum c) {
		return this.main.get(c).first();
	}

	/**
	 * Retourne la carte la plus faible en valeur d'une couleur donnee
	 * @param c CouleurEnum
	 * @return Carte carte la plus faible
	 * */
	public Carte getPlusFaibleCarteNormale(CouleurEnum c) {
		return this.main.get(c).last();
	}

	/**
	 * Retourne le nombre de carte d'une couleur donnee
	 * @param couleur CouleurEnum
	 * @return int
	 * */
	public int getNbCarteCouleur(CouleurEnum couleur){
		return this.main.get(couleur).size();
	}

	/**
	 * Permet de savoir si la main possede le 9 a l'atout
	 * @param couleur CouleurEnum
	 * @return boolean true si le 9 est dans la main
	 * */
	public boolean hasValet(CouleurEnum couleur){
		return this.main.get(couleur).contains(new Carte(couleur, FigureEnum.Valet));
	}
	/**
	 * Permet de savoir si la main possede le valet a l'atout
	 * @param couleur CouleurEnum
	 * @return boolean true si le valet est dans la main
	 * */
	public boolean hasNeuf(CouleurEnum couleur){
		return this.main.get(couleur).contains(new Carte(couleur, FigureEnum.Neuf));
	}

	/**
	 * Permet de savoir si la main possede une carte donnee
	 * @param couleur CouleurEnum
	 * @param figure FigureEnum
	 * @return boolean true si le valet est dans la main
	 * */
	public boolean hasCarte(CouleurEnum couleur, FigureEnum figure){
		return this.main.get(couleur).contains(new Carte(couleur, figure));
	}

	/**
	 * Permet de savoir si la main possede la dame et le roi a l'atout
	 * @param couleurAtout CouleurEnum
	 * @return boolean true si le dame et roi sont dans la main
	 * */
	public boolean hasBeloteRebelote(CouleurEnum couleurAtout){
		return this.hasCarte(couleurAtout, FigureEnum.Dame) && this.hasCarte(couleurAtout, FigureEnum.Roi);
	}

	/**
	 * Surcharge de la methode toString() d'Object
	 * @return String 
	 * */
	public String toString(){
		int numCarte = 0;
		String res = "(";
		Set<CouleurEnum> keys = this.main.keySet();
		for (Iterator<CouleurEnum> it = keys.iterator(); it.hasNext();) {
			CouleurEnum key = (CouleurEnum) it.next();
			for (Iterator<Carte> it2 = this.main.get(key).iterator(); it2.hasNext();) {
				Carte carte = (Carte) it2.next();
				res += "\t"+ numCarte + " - " + carte + "\n";
				numCarte++;
			}
		}
		return res + ")";
	}

	/**
	 * Permet de savoir si la main possede la dame et le roi a l'atout
	 * @param c CouleurEnum
	 * @return SortedSet<Carte>
	 * */
	public SortedSet<Carte> get(CouleurEnum c){
		return this.main.get(c);
	}
	/**
	 * Retourne une liste de carte de la couleur donnee
	 * @param c CouleurEnum
	 * @return List<Carte>
	 * */
	public List<Carte> getList(CouleurEnum c){
		List<Carte> l = new ArrayList<Carte>(this.main.get(c));
		return l;
	}
	/**
	 * Retourne une liste de carte representant la main
	 * @return List<Carte>
	 * */
	public List<Carte> hashtableToList(){
		List<Carte> newMain = new ArrayList<Carte>();
		Set<CouleurEnum> keys = this.main.keySet();
		for (Iterator<CouleurEnum> it = keys.iterator(); it.hasNext();) {
			CouleurEnum key = (CouleurEnum) it.next();
			for (Iterator<Carte> it2 = this.main.get(key).iterator(); it2.hasNext();) {
				Carte carte = (Carte) it2.next();
				newMain.add(carte);
			}
		}
		return newMain;
	}

	/**
	 * Filtre les atouts. Si la main contient un/des atout(s) plus fort que la carte maître on retourne 
	 * uniquement les atouts plus fort que la carte donnee pour surcouper.
	 * @param carteMaitre CarteJouee
	 * @return les atouts en main, avec seulement ceux permettant de surcouper si cela est réalisable
	 * */
	public SortedSet<Carte> filtrerAtoutsPourSurcoupe(CarteJouee carteMaitre) {
		CompAtout comparateur = new CompAtout(carteMaitre.getCouleur());
		SortedSet<Carte> setCarteAtoutPlusForte = new TreeSet<Carte>(comparateur);

		for (Carte carte : this.get(carteMaitre.getCouleur())) {
			if(carte.calculerValeurCarte(carteMaitre.getCouleur()) > carteMaitre.calculerValeurCarte(carteMaitre.getCouleur())){
				setCarteAtoutPlusForte.add(carte);
			}
		}

		//si setCarteAtoutPlusForte est vide alors le joueur n'a pas d'atout plus forte
		if(setCarteAtoutPlusForte.size() == 0){
			Terminal.ecrireStringln("Vous ne pouvez pas monter à l'atout.");
			return this.get(carteMaitre.getCouleur());
		}
		else{
			//affichage temporaire
			Terminal.ecrireStringln("Vous devez monter a l'atout");
			return setCarteAtoutPlusForte;
		}

	}
}
