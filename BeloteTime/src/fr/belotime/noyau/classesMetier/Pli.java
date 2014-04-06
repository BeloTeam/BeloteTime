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
 * Classe representant un pli au cours d'une manche
 * @author BeloTeam
 * @version 1.0
 **/
public class Pli extends AbstractPaquetNonTrier {
	private boolean hasDixDeDer;
	private CarteJouee carteMaitre;
	private CouleurEnum couleurDemandee;
	private CouleurEnum couleurAtout;

	/**
	 * Constructeur d'un pli
	 * @param couleurAtout CouleurEnum
	 * @param hasDixDeDer boolean
	 * */
	public Pli(CouleurEnum couleurAtout, boolean hasDixDeDer) {
		super();
		this.couleurAtout = couleurAtout;
		this.hasDixDeDer = hasDixDeDer;
		this.carteMaitre = null;
		this.couleurDemandee = null;
	}

	/**
	 * Permet d'ajouter une carte au pli
	 * @param c Carte
	 * @param j Joueur
	 * @return boolean true si ajout�, false sinon
	 * */
	public boolean ajouter(Carte c, Joueur j) {
		CarteJouee carteJouee = new CarteJouee(c, j);
		updateAttributs(c, carteJouee);
		return super.ajouter(carteJouee);
	}

	/**
	 * Met � jour la carte maitre du pli et la couleur demand�e suivant la nouvelle carte jou�e
	 * @param c Carte
	 * @param j CarteJouee
	 * */
	private void updateAttributs(Carte c, CarteJouee carteJouee) {
		// si c'est la 1ere carte pos�e du pli
		if (this.carteMaitre == null && this.couleurDemandee == null) {
			this.carteMaitre = carteJouee;
			this.couleurDemandee = carteJouee.getCouleur();
		} else {
			// si aucun atout n'a encore �t� pos�e (aucune coupe)
			if (this.carteMaitre.getCouleur() != couleurAtout) {
				// si on joue la couleur demand�e
				if (carteJouee.getCouleur() == this.couleurDemandee) {
					if (this.carteMaitre.compareTo(carteJouee) < 0) {
						this.carteMaitre = carteJouee;
					}
				}
				// si on coupe
				else if (carteJouee.getCouleur() == couleurAtout) {
					this.carteMaitre = carteJouee;
				}
			}
			// si un atout a d�j� �t� pos�
			else { 
				// si on pose un atout (surcoupe ou couleur atout demand�e)
				if (c.getCouleur() == couleurAtout) { 
					CompAtout comparateur = new CompAtout(couleurAtout);
					if (comparateur.compare(this.carteMaitre, carteJouee) < 0) {
						this.carteMaitre = carteJouee;
					}
				}
			}
		}
	}

	/**
	 * Calcule la valeur du pli en point
	 * @return int nombre de points
	 * */
	public int getValeurPli() {
		int points = super.valeurPaquet(couleurAtout);
		if (this.hasDixDeDer) {
			points += 10;
		}
		return points;
	}

	/**
	 * Retourne la carte maitre du pli
	 * @return CarteJouee
	 * */
	public CarteJouee getCarteMaitre() {
		return carteMaitre;
	}

	/**
	 * Retourne le joueur maitre du pli
	 * @return Joueur
	 * */
	public Joueur getJoueurMaitre() {
		return carteMaitre.getJoueur();
	}

	/**
	 * Retourne la couleur demand�e
	 * @return CouleurEnum
	 * */
	public CouleurEnum getCouleurDemandee() {
		return this.couleurDemandee;
	}
}