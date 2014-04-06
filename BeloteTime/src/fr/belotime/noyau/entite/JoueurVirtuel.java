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

package fr.belotime.noyau.entite;

import fr.belotime.noyau.classesMetier.Carte;
import fr.belotime.noyau.classesMetier.CouleurEnum;
import fr.belotime.noyau.classesMetier.Main;
import fr.belotime.noyau.classesMetier.Paquet;
import fr.belotime.noyau.classesMetier.PositionEnum;
import fr.belotime.noyau.classesMetier.TableDeJeu;

/**
 * JoueurVirtuel représante un joueur non humain, qui possède une intéligence artificielle
 * @author BeloTeam
 * @version 1.0
 **/
public class JoueurVirtuel extends Joueur {
	Intelligence ia;
	public static int SEUIL = 35;

	/**
	 * Surcharge du constructeur Joueur, création d'un joueur virtuel.
	 * @param position du joueur sur la table
	 * @param nom du joueur humain
	 * @param table ou est assie le joueur humain
	 * */
	public JoueurVirtuel(PositionEnum position, String nom, TableDeJeu table) {
		super(position, nom, table);
		ia = new Intelligence();
	}

	/**
	 * Retourne si oui ou non le joueur virtuel prend au premier tour
	 * @return boolean
	 */
	@Override
	public boolean prendPremiereDonne() {
		CouleurEnum couleurAtout = this.getTable().getCarteRetournee().getCouleur();
		int scoreMain = this.getMain().calculerValeurMain(couleurAtout);
		System.out.println(scoreMain);
		System.out.println(couleurAtout);
		System.out.println(this.getMain());
		if (scoreMain > SEUIL){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retourne la couleur choisie ou null si le joueur virtuel ne prend pas au deuxieme tour
	 * @return CouleurEnum
	 */
	@Override
	public CouleurEnum prendDeuxiemeDonne() {
		CouleurEnum couleurChoisie = null;
		int scoreMaxMain = 0;
		int score;

		System.out.println(this.getMain());
		
		for(CouleurEnum couleur : CouleurEnum.values()){
			score = this.getMain().calculerValeurMain(couleur);
			
			System.out.println(score);
			System.out.println(couleur);
		
			if (score > SEUIL && score > scoreMaxMain){
				couleurChoisie = couleur;
			}
		}
		return couleurChoisie;
	}

	/**
	 * Action permettant de couper un tas de cartes
	 * @return boolean
	 */
	@Override
	public boolean coupe(Paquet tas) {
		return false;
	}

	/**
	 * Action permettant d'analyser la main courante (belotes?)
	 * @return void
	 */
	@Override
	public void analyserSonJeu() {
		
	}

	@Override
	public Carte selectionnerUneCarte(Main mainTemp) {
		System.out.println(mainTemp);
		Carte carteSelectionnee;
		carteSelectionnee = mainTemp.hashtableToList().get(0);
		System.out.println(this.toString() + " joue : " + carteSelectionnee);

		this.getMain().supprimer(carteSelectionnee);	
		return carteSelectionnee;
	}

}
