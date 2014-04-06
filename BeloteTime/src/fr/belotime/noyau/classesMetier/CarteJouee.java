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

import fr.belotime.noyau.entite.Joueur;



/**
 * Classe representant une carte jouee
 * @author BeloTeam
 * @version 1.0
**/
public class CarteJouee extends Carte {
	
	private Joueur joueur;
	
	/**
	 * Constructeur de CarteJouee
	 * @param couleur CouleurEnum couleur de la carte
	 * @param figure FigureEnum figure de la carte
	 * @param joueur Joueur associe a la carte jouee
	 * */
	public CarteJouee(CouleurEnum couleur, FigureEnum figure, Joueur joueur) {
		super(couleur, figure);
	}
	
	/**
	 * Constructeur surcharge de CarteJouee
	 * @param carte Carte
	 * @param joueur Joueur associe a la carte jouee
	 * */
	public CarteJouee(Carte carte,Joueur joueur) {
		super(carte.getCouleur(), carte.getFigure());
		this.joueur = joueur;
	}

	/**
	 * Getter sur le joueur associe a la carte jouee
	 * @return Joueur
	 * */
	public Joueur getJoueur() {
		return joueur;
	}
}
