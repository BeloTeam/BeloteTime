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

import fr.belotime.noyau.entite.Equipe;
import fr.belotime.noyau.entite.GameMaster;
import fr.belotime.noyau.entite.Joueur;
import fr.belotime.noyau.entite.JoueurHumain;
import fr.belotime.noyau.entite.JoueurVirtuel;

/**
 * La table de jeu contient toutes les informations nécessaire pour réaliser une partie de belote.
 * @author BeloTeam
 * @version 1.0
**/
public class TableDeJeu {
	//TODO Tous les joueurs doivent pouvoir savoir qui est le donneur, qui a pris et quel est le joueur courant. Déplacer ces attributs de GameMaster ici?
	// (ce serait utilisé dans le todo suivant, cf attribuerCarteRetourneeA(Joueur) )
	private Joueur[] joueurs;
	private ArrayList<Equipe> equipes;
	private GameMaster gm;
	private Paquet paquet;
	private boolean sensDesAiguilleDuneMontre;
	private Pli pliCourant;
	private CouleurEnum couleurAtout;
	private Carte carteRetournee;
	private boolean beloteAnnoncee;
	private boolean rebeloteAnnoncee;

	/**
	 * Constructeur d'une table de jeu
	 * */
	public TableDeJeu() {
		sensDesAiguilleDuneMontre = true;
		joueurs = new Joueur[4];
		joueurs[0] = new JoueurVirtuel(PositionEnum.Nord,"BNord",this);
		joueurs[1] = new JoueurHumain(PositionEnum.Sud,"JSud",this);
		joueurs[2] = new JoueurVirtuel(PositionEnum.Est,"BEst",this);
		joueurs[3] = new JoueurVirtuel(PositionEnum.Ouest,"BOuest",this);
		this.equipes = new ArrayList<Equipe>();
		equipes.add(new Equipe(joueurs[0], joueurs[1],"Nord/Sud"));
		equipes.add(new Equipe(joueurs[2], joueurs[3],"Est/Ouest")); 
		gm = new GameMaster(joueurs,this,TypePartieEnum.DEUX_MILLE_POINTS);
		paquet = new Paquet();
		paquet.melanger(50);
		couleurAtout = null;
		beloteAnnoncee = false;
		rebeloteAnnoncee = false;
	}
	
	/**
	 * Retourne l'équipe d'un joueur donné.
	 * @param joueur
	 * @return L'équipe du joueur donné
	 */
	public Equipe getEquipeDuJoueur(Joueur joueur) {		
		Equipe e;
		if (joueur.getPosition() == PositionEnum.Nord || joueur.getPosition() == PositionEnum.Sud)
			e = this.equipes.get(0);
		else 
			e = this.equipes.get(1);
		return e;
	}
	
	/**
	 * Retourne les équipes.
	 * @return ArrayList<Equipe>
	 */
	public ArrayList<Equipe> getEquipes() {
		return equipes;
	}

	/**
	 * Retourne le paquet de cartes.
	 * @return Paquet
	 */
	public Paquet getPaquet() {
		return paquet;
	}
	
	/**
	 * Retourne le pli courant.
	 * @return Pli
	 */
	public Pli getPliCourant(){
		return this.pliCourant;
	}
	
	/**
	 * Créé le nouveau pli courant.
	 * @param dixDeDer boolean le dernier pli vaut 10 points supplémentaires
	 */
	public void nouveauPliCourant(boolean dixDeDer){
		this.pliCourant = new Pli(this.couleurAtout,dixDeDer);
	}
	
	/**
	 * Permet à un joueur de jouer une carte.
	 * @param carte jouée
	 * @param joueur qui joue 
	 */
	public void jouerCarte(Carte carte, Joueur joueur){
		if(this.pliCourant != null && this.couleurAtout != null){
			this.pliCourant.ajouter(carte, joueur);
		}
	}

	/**
	 * Retourne la couleur de l'atout.
	 * @return CouleurEnum
	 */
	public CouleurEnum getCouleurAtout() {
		return couleurAtout;
	}
	
	/**
	 * Défini la couleur à l'atout.
	 * @param couleurAtout 
	 */
	public void setCouleurAtout(CouleurEnum couleurAtout) {
		this.couleurAtout = couleurAtout;
	}

	/**
	 * Retourne la carte du dessus du paquet.
	 */
	public void retournerUneCarte(){
		this.carteRetournee = this.paquet.retirerPremiereCarte();
	}
	
	/**
	 * Renvoit la carte retournée.
	 * @return Carte
	 */
	public Carte getCarteRetournee() {
		return this.carteRetournee;
	}
	
	/**
	 * Ajouter la carte retournée au joueur qui a choisi l'atout.
	 * @param joueur
	 */
	public void attribuerCarteRetourneeA(Joueur joueur){
		//TODO En suivant le todo en haut de la page pas besoin d'indiquer le joueur qui récupère la carte retournée en paramètre
		joueur.getMain().ajouter(this.carteRetournee, this.couleurAtout);
		this.carteRetournee = null;
	}
	
	/**
	 * Remet la carte retournée dans le paquet.
	 */
	public void remettreCarteRetourneeDansLePaquet(){
		this.paquet.ajouter(carteRetournee);
		this.carteRetournee = null;
	}
	
	/**
	 * Indique le joueur suivant en fonction du sens de parcours
	 * @param le joueur de référence
	 * @return Joueur le joueur suivant
	 */
	public Joueur joueurSuivant(Joueur j){
		if(sensDesAiguilleDuneMontre){
			return aGaucheDe(j);
		} else {
			return aDroiteDe(j);
		}
	}
	
	/**
	 * Indique le joueur se trouvant à gauche du joueur donné.
	 * @param le joueur de référence
	 * @return Joueur le joueur à sa gauche
	 */
	public Joueur aGaucheDe(Joueur j){
		PositionEnum aGauche = null;
		switch(j.getPosition()){
		case Nord:
			aGauche = PositionEnum.Est;
			break;
		case Est:
			aGauche = PositionEnum.Sud;
			break;
		case Sud:
			aGauche = PositionEnum.Ouest;
			break;
		case Ouest:
			aGauche = PositionEnum.Nord;
			break;
		}
		for(Joueur joueur : joueurs){
			if(joueur.getPosition() == aGauche){
				return joueur;
			}
		}
		return null;
	}
	
	/**
	 * Indique le joueur se trouvant à droite du joueur donné.
	 * @param le joueur de référence
	 * @return Joueur le joueur à sa droite
	 */
	public Joueur aDroiteDe(Joueur j){
		PositionEnum aGauche = null;
		switch(j.getPosition()){
		case Nord:
			aGauche = PositionEnum.Ouest;
			break;
		case Ouest:
			aGauche = PositionEnum.Sud;
			break;
		case Sud:
			aGauche = PositionEnum.Est;
			break;
		case Est:
			aGauche = PositionEnum.Nord;
			break;
		}
		for(Joueur joueur : joueurs){
			if(joueur.getPosition() == aGauche){
				return joueur;
			}
		}
		return null;
	}
	
	/**
	 * Indique le nombre de cartes dans la main d'un joueur donné.
	 * @param joueur
	 * @return int le nombre de cartes
	 */
	public int nbCartesDe(Joueur joueur){
		//TODO La méthode ne semble pas utilisée (du moins pas dans GameMaster), c'est dommage! 
		int nbCartes = 0;
		for(Joueur j : this.joueurs){
			if(j == joueur){
				nbCartes = joueur.getMain().getTailleMain();
			}
		}
		return nbCartes;
	}
	
	/**
	 * Renvoit le maître du jeu de la partie
	 * @return GameMaster
	 */
	public GameMaster getGm() {
		return gm;
	}
	
	/**
	 * Renvoit les 4 joueurs de belote
	 * @return Joueur]
	 */
	public Joueur[] getJoueurs() {
		return joueurs;
	}
	
	/**
	 * Réinitialise les annonces de belote/rebelote
	 */
	public void reinitialiserLesAnnonces() {
		this.beloteAnnoncee = false;
		this.rebeloteAnnoncee = false;
		for (Equipe e : this.getEquipes()){
			e.setEquipeHasBeloteEtRe(false);
			for (Joueur j : e.getJoueurs()){
				j.setHasBeloteEtRe(false);
			}
		}
	}
	
	/**
	 * Renvoit vrai si la belote a été annoncée, faux sinon
	 * @return boolean
	 */
	public boolean isBeloteAnnoncee() {
		return beloteAnnoncee;
	}

	/**
	 * Maj de la variable booleene, vrai si la belote a été annoncée, faux sinon.
	 * @param beloteAnnoncee boolean
	 */
	public void setBeloteAnnoncee(boolean beloteAnnoncee) {
		this.beloteAnnoncee = beloteAnnoncee;
	}
	
	/**
	 * Renvoit vrai si la rebelote a été annoncé, faux sinon
	 * @return boolean
	 */
	public boolean isRebeloteAnnoncee() {
		return rebeloteAnnoncee;
	}
	
	/**
	 * Maj de la variable booleene, vrai si la rebelote a été annoncée, faux sinon.
	 * @param rebeloteAnnoncee boolean
	 */
	public void setRebeloteAnnoncee(boolean rebeloteAnnoncee) {
		this.rebeloteAnnoncee = rebeloteAnnoncee;
	}

}
