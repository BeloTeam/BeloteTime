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



/**
 * Classe representant une carte
 * @author BeloTeam
 * @version 1.0
**/
public class Carte implements Comparable<Carte>{
	
	private CouleurEnum couleur;
	private FigureEnum figure;
	
	/**
	 * Constructeur par defaut de Carte
	 * @param couleur CouleurEnum couleur de la carte
	 * @param figure FigureEnum figure de la carte
	 * */
	public Carte(CouleurEnum couleur, FigureEnum figure) {
		this.couleur = couleur;
		this.figure = figure;
	}

	/**
	 * Retourne la couleur de la carte
	 * @return CouleurEnum 
	 * */
	public CouleurEnum getCouleur() {
		return couleur;
	}
	/**
	 * Retourne la figure de la carte
	 * @return FigureEnum 
	 * */
	public FigureEnum getFigure() {
		return figure;
	}
	/**
	 * Surcharge de la methode toString() d'Object
	 * @return String 
	 * */
	public String toString() {
		return "(" + this.figure + " de " + this.couleur + ")";
	}
	/**
	 * Surcharge de la methode equals() d'Object
	 * @param carte Carte 
	 * @return boolean true si c'est la même carte 
	 * */
	public boolean equals(Carte carte) {
		return (this.couleur.equals(carte.couleur) && this.figure.equals(carte.figure));
	}
	
	/**
	 * Surcharge de la methode compareTo() d'Object
	 * @param c Carte
	 * @return int 
	 * */
	@Override
	public int compareTo(Carte c) {
		int res = this.getCouleur().compareTo(c.getCouleur()) + this.getFigure().compareTo(c.getFigure());
		//Terminal.ecrireStringln("Carte " + this + " compareTo carte " + c + " = " + res);
		return res;
	}
	
	/**
	 * Calcule la valeur d'une carte en fonction de la couleur d'atout donee
	 * @param atout CouleurEnum
	 * @return int 
	 * */
	public int calculerValeurCarte(CouleurEnum atout){
		int point = 0;
		switch (this.figure) {
		case Neuf:
			if(atout.equals(this.getCouleur())) {
				point = 14;
			}else{
				point = 0;
			}
			break;
		case Dix:
			point = 10;
			break;
		case Valet:
			if(atout.equals(this.getCouleur())) {
				point = 20;
			}else{
				point = 2;
			}
			break;
		case Dame:
			point = 3;
			break;
		case Roi:
			point = 4;
			break;
		case As:
			point = 11;
			break;
		default: // Sept  et huit
			point = 0;
			break;
		}
		return point;
	}
}