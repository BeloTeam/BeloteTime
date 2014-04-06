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

import java.util.ArrayList;

import fr.belotime.noyau.classesMetier.Carte;
import fr.belotime.noyau.classesMetier.Pli;



/**
 * Une equipe est composée de deux joueurs, d'un historique de score de manche
 * et d'un score de partie.
 * 
 * @author BeloTeam
 * @version 1.0
 **/
public class Equipe {
	private ArrayList<Joueur> joueurs;
	private ArrayList<Integer> historiqueScoreManches;
	private ArrayList<Pli> plisRemportee;
	private int scoreManche;
	private int scorePartie;
	private int nbManche;
	private String nomEquipe;
	private boolean equipeHasBeloteEtRe;
	

	/**
	 * Constructeur d'une equipe
	 * @param le joueur 1
	 * @param le joueur 2
	 * */
	public Equipe(Joueur joueur1, Joueur joueur2, String nom) {
		this.joueurs = new ArrayList<Joueur>();
		this.joueurs.add(joueur1);
		this.joueurs.add(joueur2);
		this.historiqueScoreManches = new ArrayList<Integer>();
		this.plisRemportee = new ArrayList<Pli>();
		this.scorePartie = 0;
		this.scoreManche = 0;
		this.nbManche = 0;
		this.nomEquipe = nom;
		this.equipeHasBeloteEtRe = false;
	}

	/**
	 * Surchage de la méthode equals d'Object
	 * @param equipe 
	 * @return boolean
	 */
	public boolean equals(Object equipe) {
		if (equipe.getClass() == this.getClass()) {
			if (this.joueurs.get(0).equals(((Equipe) equipe).joueurs.get(0))
					&& this.joueurs.get(1).equals(
							((Equipe) equipe).joueurs.get(1))) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Surcharge de la méthode toString d'Object
	 * @return String
	 */
	public String toString() {
		return "L'équipe '"+ this.nomEquipe + "' est composée de : " + this.getJoueurs();
	}
	
	/* **** Méthodes manipulant les joueurs de l'équipe **** **/
	/**
	 * Retourne la liste des membres de l'équipe
	 * @return ArrayList<Joueur>
	 */
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Retourne si oui ou non un joueur est membre de l'équipe
	 * @param le joueur à tester
	 * @return ArrayList<Joueur>
	 */
	public boolean estDansEquipe(Joueur joueur) {
		return joueurs.contains(joueur);
		/*for (Joueur j : joueurs) {
			if (j.equals(joueur)) {
				return true;
			}
		}
		return false;*/
	}

	/**
	 * Retourne le partenaire d'un joueur de l'equipe
	 * @return j Joueur
	 */
	public Joueur getPartenaire(Joueur j) {
		if (this.estDansEquipe(j)) {
			if (this.joueurs.get(0) != j) {
				return this.joueurs.get(0);
			} else {
				return this.joueurs.get(1);
			}
		}
		return null;
	}
	/* ***************************************************** **/
	
	/* **** Méthodes manipulant les scores de l'équipe **** **/
	/**
	 * Retourne le score de la manche dont le numero est passé en paramètre
	 * @param numéro de la manche int
	 * @return int
	 */
	 public int getScoreMancheOfHistorique(int numeroManche) {
		 return this.historiqueScoreManches.get(numeroManche);
	 }
	 
	 /**
	 * Ajoute le score d'une manche à l'historique des scores de chaque manches de la partie
	 * @return int un score
	 */
	 public boolean addScoreMancheToHistorique(int score) {
		 this.nbManche++;
		 this.scorePartie += score;
		 return this.historiqueScoreManches.add(score);
	 }
	 
	 /**
	 * Retourne le score courant de la manche
	 * @return int
	 */
	 public int getScoreManche() {
		 return this.scoreManche;
	 }
	 
	 /**
	 * Maj la score courant de la manche
	 * @param scoreManche int
	 */
	 public void setScoreManche(int scoreManche) {
		 this.scoreManche = scoreManche;
	 }
	 
	/**
	 * Retourne le score de la partie
	 * @return int
	 */
	public int getScorePartie() {
		return this.scorePartie;
	}
	
	/**
	 * Maj le score de la partie
	 * @param nouveau score de la partie int
	 */
	public void setScorePartie(int scorePartie) {
		this.scorePartie = scorePartie;
	}
	
	/**
	 * Retourne le numero de la manche courante (ou le nombre de manche effectué)
	 * @return int
	 */
	public int getNbManche() {
		return nbManche;
	}

	/**
	 * Maj le numero de la manche courante (ou le nombre de manche effectué)
	 * @param nombre de manche int
	 */
	public void setNbManche(int nbManche) {
		this.nbManche = nbManche;
	}
	
	/** 
	 * Reset de tous les attributs score de la partie
	 */
	public void resetScorePartie() {
		this.historiqueScoreManches.clear();
		this.historiqueScoreManches = new ArrayList<Integer>();
		this.scoreManche = 0;
		this.scorePartie = 0;
		this.nbManche = 0;
		this.equipeHasBeloteEtRe = false;
	}
	/* **************************************************** **/
	
	/* ***** Méthodes manipulant les plis de l'équipe ***** **/
	/**
	 * Ajoute un nouveau pli remporter à l'historique des plis et maj le score courant de la manche
	 * @param le pli remporté Pli
	 * @return boolean
	 */
	public boolean ajouterPliRemporte(Pli pli) {
		this.scoreManche += pli.getValeurPli();
		return this.plisRemportee.add(pli);
	}
	
	/**
	 * Retourne une liste de cartes contenues dans les plis remportés 
	 * @return boolean
	 */
	public ArrayList<Carte> rendreLesCartesDesPlisRemporter() {
		ArrayList<Carte> cartesARendre = new ArrayList<Carte>();
		for (Pli pli : plisRemportee) {
			cartesARendre.addAll(pli.getCartes());
		}
		plisRemportee.clear();
		plisRemportee = new ArrayList<Pli>();
		return cartesARendre;
	}
	/* **************************************************** **/

	/**
	 * Définit si l'équipe a la belote ou non
	 * @param boolean
	 */
	public void setEquipeHasBeloteEtRe(boolean b) {
		this.equipeHasBeloteEtRe = b;		
	}

	/**
	 * Retourne si l'équipe a le belote ou non
	 * @return boolean
	 */
	public boolean isEquipeHasBeloteEtRe() {
		return equipeHasBeloteEtRe;
	}
}
