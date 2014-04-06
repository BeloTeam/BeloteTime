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

import java.util.Comparator;

/**
 * Classe representant un comparateur destiné a comaprer les atouts
 * @author BeloTeam
 * @version 1.0
**/
public class CompAtout implements Comparator<Carte>{
	
	private CouleurEnum atout;

	/**
	 * Constructeur de CompAtout
	 * @param couleur CouleurEnum couleur de l'atout
	 * */
	public CompAtout(CouleurEnum atout){
		this.atout = atout;
	}

	/**
	 * Getter sur le joueur associe a la carte jouee
	 * @param o1 Carte carte comparé
	 * @param o2 Carte carte comparé
	 * @return int negatif si o1 est plus petite que o2, positif si plus grande, 0 sinon.
	 * */
	@Override
	public int compare(Carte o1, Carte o2) {
		int res = 0;
		if(o1.calculerValeurCarte(this.atout) > o2.calculerValeurCarte(this.atout)){
			res = 1;
		} else {
			if(o1.calculerValeurCarte(this.atout) < o2.calculerValeurCarte(this.atout)){
				res = -1;
			} else {
				res = o1.compareTo(o2);
			}
		}
		return res;
	}
}