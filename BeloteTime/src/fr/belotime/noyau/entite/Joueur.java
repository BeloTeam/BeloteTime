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

import java.util.SortedSet;

import fr.belotime.noyau.classesMetier.Carte;
import fr.belotime.noyau.classesMetier.CouleurEnum;
import fr.belotime.noyau.classesMetier.Main;
import fr.belotime.noyau.classesMetier.Paquet;
import fr.belotime.noyau.classesMetier.PositionEnum;
import fr.belotime.noyau.classesMetier.TableDeJeu;
import fr.belotime.noyau.control.Terminal;


public abstract class Joueur {
	private Main main;
	private PositionEnum position;
	private String nom;
	private TableDeJeu table;
	private boolean hasBeloteEtRe;

	/**
	 * Constructeur Joueur, création d'un joueur.
	 * @param position du joueur sur la table
	 * @param nom du joueur humain
	 * @param table ou est assie le joueur humain
	 * */
	public Joueur(PositionEnum position, String nom, TableDeJeu table) {
		this.main = new Main();
		this.position = position;
		this.nom = nom;
		this.table = table;
		this.hasBeloteEtRe = false;
	}
	
	/**
	 * Surcharge de l'opérateur toString d'Object
	 * @return String
	 */
	@Override
	public String toString() {
		return this.nom;
	}
	
	/**
	 * Surcharge de l'opérateur equals d'Object
	 * @param joueur
	 * @return boolean
	 */
	@Override
	public boolean equals(Object joueur) {
		if (joueur instanceof Joueur) {
			if (((Joueur) joueur).nom.equals(this.nom) && ((Joueur) joueur).position.equals(this.position)) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * maj de la variable, vrai si le joueur a la belote et rebelote dans sa main de départ, sinon faux.
	 * @param b boolean
	 */
	public void setHasBeloteEtRe(boolean b) {
		this.hasBeloteEtRe = b;
	}

	/**
	 * Retourne vrai si le joueur a la belote et rebelote dans sa main de départ, sinon faux.
	 * @return boolean
	 */
	public boolean hasBeloteEtRe() {
		return this.hasBeloteEtRe;
	}
	
	/**
	 * Retourne la position du joueur sur la table
	 * @return PositionEnum
	 */
	public PositionEnum getPosition() {
		return position;
	}

	/**
	 * Retourne la main courante du joueur
	 * @return Main
	 */
	public Main getMain() {
		return main;
	}
	
	/**
	 * Retourne la table de jeu où est assis le joueur.
	 * @return TableDeJeu
	 */
	public TableDeJeu getTable() {
		return table;
	}
	
	/**
	 * Retourne si oui ou non le joueur prend au premier tour
	 * @return boolean
	 */
	public abstract boolean prendPremiereDonne();

	/**
	 * Retourne si oui ou non le joueur prend au deuxieme tour
	 * @return CouleurEnum
	 */
	public abstract CouleurEnum prendDeuxiemeDonne();

	/**
	 * Action permettant de jouer une carte lors d'un pli.
	 * @return Carte
	 */
	public Carte jouerPli() {
		Carte carteJouee = null;
		SortedSet<Carte> cartesPossibles = null;
		Main mainTemp = new Main();
		
		Terminal.ecrireStringln("-------------JEU--------------\n Joueur courant : " + this.toString() 
				+ " ("+this.getEquipeDuJoueur()+")");
		while (carteJouee == null) {
			// S'il n'y a aucune carte sur la table (le cas ou le joueur commence)
			if (this.getTable().getPliCourant().size() == 0) {	
				mainTemp = this.getMain();
				Terminal.ecrireStringln("Vous commencez.");
			} 
			// S'il y a au moins une carte sur la table
			else {				
				Terminal.ecrireStringln("Pli actuel : "+ this.getTable().getPliCourant());
				Terminal.ecrireStringln("Joueur maitre : "+ this.getTable().getPliCourant().getJoueurMaitre());
				Terminal.ecrireStringln("Couleur demande : "+ this.getTable().getPliCourant().getCouleurDemandee());
				Terminal.ecrireStringln("Votre main :\n" + this.getMain().toString());


				// Si nous avons la couleur demandee
				if (this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee()) != null
						&& !this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee()).isEmpty()) {

					//si c'est de l'atout
					if(this.getTable().getPliCourant().getCouleurDemandee() == this.getTable().getCouleurAtout()){
						cartesPossibles = this.getMain().filtrerAtoutsPourSurcoupe(this.getTable().getPliCourant().getCarteMaitre());
						mainTemp.getMain().put(this.getTable().getCouleurAtout(), cartesPossibles);
						mainTemp.setSize(cartesPossibles.size());
						Terminal.ecrireStringln("Vous devez jouer de l'atout.");
						// n'a pas obligé le joueur à surcouper alors qu'il le pouvait ?!
					}
					else {
						cartesPossibles = this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee());
						mainTemp.getMain().put(this.getTable().getPliCourant().getCouleurDemandee(), cartesPossibles);
						mainTemp.setSize(cartesPossibles.size());
						Terminal.ecrireStringln("Vous devez jouer la couleur demandée.");
					}					
					//Terminal.ecrireStringln("\nVous avez le choix entre : "+ cartesPossibles);
					//carteJouee = selectionnerUneCarte(cartesPossibles);
				} 
				// Sinon le joueur n'a pas la couleur demandee
				else { 
					// Si la couleur demandée est l'atout
					if (this.getTable().getPliCourant().getCouleurDemandee() == this.getTable().getCouleurAtout()){
						mainTemp = this.getMain();
						Terminal.ecrireStringln("Vous n'avez pas d'atout, jouez une autre couleur.");
					}	
					else {
						Terminal.ecrireStringln("\nVous n'avez pas la couleur demandée! ");	
						/********** on a peut-être le droit de se défausser! **********/
						// on regarde si le partenaire du joueur courant est maitre
						Joueur joueurMaitre = this.getTable().getPliCourant().getJoueurMaitre();
						Joueur joueurCoequipier = this.getTable().getEquipeDuJoueur(this).getPartenaire(this);

						// si le partenaire est maitre il peut se défausser 
						if(joueurMaitre == joueurCoequipier){
							// Il peut jouer la carte qu'il veut
							mainTemp = this.getMain();	
							Terminal.ecrireStringln("Votre partenaire est maitre, vous avez le droit de vous défausser (pisser)");	
							//TODO cas de test pas encore atteint : il a le droit de se défausser mais il choisit de prendre la main 
						} 
						// le partenaire n'est pas maître donc il ne peut pas se défausser 
						else{	
							// si le joueur a de l'atout il doit couper		
							cartesPossibles = this.getMain().get(this.getTable().getCouleurAtout());
							if (cartesPossibles != null && cartesPossibles.size() > 0) {
								// si la carte maitre est un atout il doit surcouper si il le peut
								if(this.getTable().getPliCourant().getCarteMaitre().getCouleur() == this.getTable().getCouleurAtout()){
									cartesPossibles = this.getMain().filtrerAtoutsPourSurcoupe(this.getTable().getPliCourant().getCarteMaitre());
								}									
								mainTemp.getMain().put(this.getTable().getCouleurAtout(), cartesPossibles);
								mainTemp.setSize(cartesPossibles.size());
								Terminal.ecrireStringln("\nVous devez jouer à l'atout");
							} 
							// sinon il doit jouer une autre carte.
							else {								
								mainTemp = this.getMain();								
								Terminal.ecrireStringln("Vous n'avez pas d'atout, vous devez vous défausser.\n");
							}
						}
					}
				}
			} 
			carteJouee = selectionnerUneCarte(mainTemp);
		}		
		return carteJouee;
	}

	public abstract Carte selectionnerUneCarte(Main mainTemp);

	/**
	 * Action permettant de couper un tas de cartes
	 * @return boolean
	 */
	public abstract boolean coupe(Paquet tas);
	

	/**
	 * Action permettant d'analyser la main courante (belotes?)
	 * @return boolean
	 */
	public abstract void analyserSonJeu();
	
	/**
	 * Permet de retourner l'equipe dans lequel est le joueur
	 * @return Equipe
	 */
	public Equipe getEquipeDuJoueur(){
		
		if( this.table.getEquipes().get(0).estDansEquipe(this)){
			return this.table.getEquipes().get(0);
		}
		else{
			return this.table.getEquipes().get(1);
		}
	}
}
