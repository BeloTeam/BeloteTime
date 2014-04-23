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
import fr.belotime.noyau.classesMetier.EtatPartieEnum;
import fr.belotime.noyau.classesMetier.TableDeJeu;
import fr.belotime.noyau.classesMetier.TypePartieEnum;
import fr.belotime.noyau.control.Terminal;



/**
 * Le maître du jeu s'occupe du déroulement de la partie.
 * @author BeloTeam
 * @version 1.0
**/
public class GameMaster {
	
	private TableDeJeu table;
	private Joueur joueurCourant;
	private Joueur joueurDonneur;
	private Joueur joueurPrend;
	private EtatPartieEnum etat;
	private TypePartieEnum typePartie; 
	
	/**
	 * Constructeur de GameMaster.
	 * @param joueurs Les 4 joueurs de belote
	 * @param table	La table de jeu	
	 */
	public GameMaster(Joueur joueurs[], TableDeJeu table, TypePartieEnum typePartie) {		
		this.table = table;
		this.joueurDonneur = getDonneurRandom();
		this.etat = EtatPartieEnum.PremiereDistribution;
		this.typePartie = typePartie;
	}
	
	public boolean partieFinie() {
		switch (this.typePartie) {
		case CINQ_MANCHES:
			return (this.table.getEquipes().get(0).getNbManche() == 5);
		case DIX_MANCHES:
			return (this.table.getEquipes().get(0).getNbManche() == 10);
		case VINGT_MANCHES:
			return (this.table.getEquipes().get(0).getNbManche() == 20);
		case CINQ_CENTS_POINTS:
			return this.table.getEquipes().get(0).getScorePartie() > 500
					|| this.table.getEquipes().get(1).getScorePartie() > 500;
		case MILLE_POINTS:
			return this.table.getEquipes().get(0).getScorePartie() > 1000
					|| this.table.getEquipes().get(1).getScorePartie() > 1000;
		case DEUX_MILLE_POINTS:
			return this.table.getEquipes().get(0).getScorePartie() > 2000
					|| this.table.getEquipes().get(1).getScorePartie() > 2000;
		default:
			// lever un exception
			break;
		}
		return false;
	}
	
	public void changerEtat(EtatPartieEnum nouvelEtat){
		etat = nouvelEtat;
	}
	
	public void premiereDistribution(){
		Terminal.ecrireStringln("Donneur : " + this.joueurDonneur);
		this.joueurCourant = table.joueurSuivant(this.joueurDonneur);
		distribuer(3);
		distribuer(2);
		this.table.retournerUneCarte();
	}
	
	public void premierTourChoixAtout(){
		this.joueurPrend = quiPrendPremiereDonne();
		if (this.joueurPrend == null) {
			etat = EtatPartieEnum.DeuxiemeTourDonne;
		} else {
			this.table.attribuerCarteRetourneeA(this.joueurPrend);
			etat = EtatPartieEnum.DeuxiemeDistribution;
		}
	}

	/**
	 * Lancement de la partie.
	 */
	public void debuterPartie() {
		this.joueurPrend = null;
		while (!this.partieFinie()) {
			switch (etat) {
			case PremiereDistribution:
				premiereDistribution();
				//etat = EtatPartieEnum.PremierTourDonne;
				break;
			case PremierTourDonne:
				premierTourChoixAtout();				
				break;
			case DeuxiemeTourDonne:
				this.joueurPrend = quiPrendDeuxiemeDonne();
				if (this.joueurPrend == null) {
					// Personne n'a pris, on re-distribue les cartes
					// on récupère les cartes
					recupererCartesMain();
					this.table.remettreCarteRetourneeDansLePaquet();
					// on mélange
					this.table.getPaquet().melanger(50);
					etat = EtatPartieEnum.PremiereDistribution;
				} else {
					this.table.attribuerCarteRetourneeA(this.joueurPrend); //TODO ça devrait aller dans distribuerDeuxiemeTour ou au moins dans le case DeuxiemeDistribution
					Terminal.ecrireStringln("----FIN DE LA DONNE-----\n"
							+ this.joueurPrend.toString() + " a pris à : "
							+ this.table.getCouleurAtout());
					etat = EtatPartieEnum.DeuxiemeDistribution;
				}
				break;
			case DeuxiemeDistribution:
				distribuerDeuxiemeTour(this.joueurPrend);
				this.joueurCourant = table.joueurSuivant(joueurDonneur);
				etat = EtatPartieEnum.PhaseDePli;
				break;
			case PhaseDePli:
				jouerUnPli();
				if(this.joueurCourant.getMain().getTailleMain() == 0){
					/***************** FIN DE LA MANCHE *****************/
					determinerScoreMancheCourante();
					this.joueurPrend = null;
					remettreLesPlisDansLeTas();
					this.table.reinitialiserLesAnnonces();
					this.joueurDonneur.coupe(this.table.getPaquet());
					this.joueurDonneur = table.joueurSuivant(this.joueurDonneur);
					etat = EtatPartieEnum.PremiereDistribution;
				} else {
					etat = EtatPartieEnum.PhaseDePli;
				}
				break;
			}
		}
		Terminal.ecrireStringln("FIN DE LA PARTIE \n---------------------");
	}

	
	/**
	 * Permet de calculer le score de la manche pour chaque équipe. On utilise les règles suivantes comme référence :
	 * http://www.ffbelote.org/regles-officielle-belote/
	 */
	private void determinerScoreMancheCourante(){		
		Equipe equipeQuiAPris = this.table.getEquipeDuJoueur(joueurPrend);
		Equipe equipeAdverse = this.table.getEquipeDuJoueur(this.table.joueurSuivant(joueurPrend));
		int sousTotalQuiAPris = equipeQuiAPris.getScoreManche();
		int sousTotalAdverse = equipeAdverse.getScoreManche();		

		Terminal.ecrireStringln("-----FIN DE MANCHE : Récapitualtif des scores -----");
		Terminal.ecrireStringln("Les preneurs sont : " + equipeQuiAPris);
		
		// Ajout des 20 points de la belote
		if(this.table.isBeloteAnnoncee() && this.table.isRebeloteAnnoncee()){
			if(equipeQuiAPris.isEquipeHasBeloteEtRe()){
				sousTotalQuiAPris += 20;
			}
			else if(equipeAdverse.isEquipeHasBeloteEtRe()){
				sousTotalAdverse += 20;
			}
			else{
				Terminal.ecrireStringln("Erreur : une des deux équipes a annoncée la belote et elle n'a pas été prise en compte dans le calcul des scores!");
			}
		}
		
		if(sousTotalQuiAPris > sousTotalAdverse) {
			// L'équipe qui a pris a remplie son contrat
			Terminal.ecrireStringln("Contrat réussi!");
				// Si il y a capot
				if (equipeQuiAPris.getScoreManche() == 162){
					// Capot!!!
					Terminal.ecrireStringln("CAPOT !!!!");
					sousTotalQuiAPris += 90;
					equipeQuiAPris.addScoreMancheToHistorique(sousTotalQuiAPris);
					equipeAdverse.addScoreMancheToHistorique(sousTotalAdverse);					
				} else{
					equipeQuiAPris.addScoreMancheToHistorique(sousTotalQuiAPris);
					equipeAdverse.addScoreMancheToHistorique(sousTotalAdverse);
				}
			}
		else if (sousTotalQuiAPris == sousTotalAdverse){
			// Litige
			Terminal.ecrireStringln("Litige : égalité parfaite au score, la défense marque ses points, mais les points des preneurs sont remis en jeu et seront offerts en bonus aux vainqueurs de la prochaine manche.");
			equipeQuiAPris.addScoreMancheToHistorique(0); // ses points seront pour le vainqueur de la manche suivante
			equipeAdverse.addScoreMancheToHistorique(sousTotalAdverse);
			// TODO ajouter la gestion du litige
			
		}
		else {
			// L'équipe qui a pris est dedans
			Terminal.ecrireStringln("Contrat chuté!");
			// Si l'équipe adverse fait un capot
			if(equipeAdverse.getScoreManche() == 162){
				sousTotalAdverse += 90;
				equipeQuiAPris.addScoreMancheToHistorique(sousTotalQuiAPris);
				equipeAdverse.addScoreMancheToHistorique(sousTotalAdverse);
			} else{
				equipeQuiAPris.addScoreMancheToHistorique(sousTotalQuiAPris-equipeQuiAPris.getScoreManche());
				equipeAdverse.addScoreMancheToHistorique(sousTotalAdverse+equipeQuiAPris.getScoreManche());
			}
		}
			
		Terminal.ecrireStringln(equipeQuiAPris 
				+ "\n   Score manche: " + equipeQuiAPris.getScoreManche() 
				+ "\n   Belote: " + equipeQuiAPris.isEquipeHasBeloteEtRe()
				+ "\n   Résultat: " + equipeQuiAPris.getScoreMancheOfHistorique(equipeQuiAPris.getNbManche()-1));
		
		Terminal.ecrireStringln(equipeAdverse 
				+ "\n   Score manche: " + equipeAdverse.getScoreManche() 
				+ "\n   Belote: " + equipeAdverse.isEquipeHasBeloteEtRe()
				+ "\n   Résultat: " + equipeAdverse.getScoreMancheOfHistorique(equipeAdverse.getNbManche()-1));
		
		// reset le score de la manche courante pour l'equipe
		equipeQuiAPris.setScoreManche(0);
		equipeAdverse.setScoreManche(0);
	}

	
	/**
	 * On remet les cartes du plis dans le tas.
	 */
	private void remettreLesPlisDansLeTas(){
		for(Equipe equipe : this.table.getEquipes()){
			table.getPaquet().reposerListeCartes(equipe.rendreLesCartesDesPlisRemporter());
		}
	}

	/**
	 * Selectionne un des joueurs pour être le donneur
	 * @return Le donneur
	 */
	private Joueur getDonneurRandom(){
		int indiceJoueurDonneur = ((int) (Math.random() * 4));
		return this.table.getEquipes().get(indiceJoueurDonneur % 2).getJoueurs().get(indiceJoueurDonneur / 2);
	}

	/**
	 * Jouer un pli.
	 */
	private void jouerUnPli(){
		if(this.joueurCourant.getMain().getTailleMain() == 1){
			this.table.nouveauPliCourant(true);
		} else {
			this.table.nouveauPliCourant(false);
		}
		Carte carteJouee = null;
		while (this.table.getPliCourant().size() < 4) {
			// Au premier tour avant de jouer quoi que ce soit, on analyse son jeu (juste pour voir les annonces belote et re pour le moment)
			if(this.joueurCourant.getMain().getTailleMain() == 8) this.joueurCourant.analyserSonJeu();
			carteJouee = joueurCourant.jouerPli();

			this.table.jouerCarte(carteJouee,joueurCourant);
			joueurCourant = table.joueurSuivant(joueurCourant);
		}
		Terminal.ecrireStringln("-------PLI FINI------\nJoueurMaitre : " + this.table.getPliCourant().getJoueurMaitre());
		this.table.getEquipeDuJoueur(this.table.getPliCourant().getJoueurMaitre()).ajouterPliRemporte(this.table.getPliCourant());
		joueurCourant = this.table.getPliCourant().getJoueurMaitre();
	}

	/**
	 * Permet de récupérer les cartes dans la main des joueurs.
	 */
	private void recupererCartesMain() {
		Joueur premierJoueur = this.joueurCourant;
		do {
			for (Carte carte : joueurCourant.getMain().hashtableToList()) {
				this.table.getPaquet().ajouter(carte);
				joueurCourant.getMain().supprimer(carte);
			}
			this.joueurCourant = table.joueurSuivant(this.joueurCourant);
		} while (this.joueurCourant != premierJoueur);
	}

	/**
	 * Premier tour de table pour proposer aux joueurs de prendre à l'atout la carte retournée.
	 * @return Renvoit le jouer qui a pris, null le cas échéant.
	 */
	private Joueur quiPrendPremiereDonne() {
		Joueur joueurPrend = null;
		int i = 0;
		// tend que personne n'a pris ET que tout le monde n'as pas été interrogé
		while (joueurPrend == null && i < 4) {
			Terminal.ecrireStringln("---------PREMIERE DONNE----------\nJoueur courant : "
					+ this.joueurCourant + "\nCarte retournée:" + this.table.getCarteRetournee());
			if (joueurCourant.prendPremiereDonne()) {
				joueurPrend = joueurCourant;
				this.table.setCouleurAtout(this.table.getCarteRetournee().getCouleur());
				Terminal.ecrireStringln(joueurCourant + " PREND");
			} else {
				Terminal.ecrireStringln(joueurCourant + " PASSE");
			}
			i++;
			joueurCourant = table.joueurSuivant(joueurCourant);
		}
		return joueurPrend;
	}

	/**
	 * Deuxieme tour de table pour proposer aux joueurs de prendre à l'atout à une autre couleur.
	 * @return Renvoit le jouer qui a pris, null le cas échéant.
	 */
	private Joueur quiPrendDeuxiemeDonne() {
		this.table.setCouleurAtout(null);
		Joueur joueurPrend = null;
		int i = 0;
		while (this.table.getCouleurAtout() == null && i < 4) {
			Terminal.ecrireStringln("---------DEUXIEME DONNE----------\nJoueur courant : "
					+ joueurCourant);
			this.table.setCouleurAtout(joueurCourant.prendDeuxiemeDonne());
			Terminal.ecrireStringln("\ncouleur Choisie :" + this.table.getCouleurAtout());
			if (this.table.getCouleurAtout() == null) {
				joueurCourant = table.joueurSuivant(joueurCourant);
			} else {
				joueurPrend = joueurCourant;
			}
			i++;
		}
		return joueurPrend;
	}


	/** 
	 * Permet de distribuer les cartes du paquet aux joueurs.
	 * @param nbCarte nombre de cartes à distribuer
	 */
	private void distribuer(int nbCarte) {
		Joueur premierJoueur = this.joueurCourant;
		do {
			for (int i = 0; i < nbCarte; i++) {
				this.joueurCourant.getMain().ajouter(table.getPaquet().retirerPremiereCarte(), this.table.getCouleurAtout());
			}
			this.joueurCourant = table.joueurSuivant(this.joueurCourant);
		} while (this.joueurCourant != premierJoueur);
	}

	/**
	 * Distribue le reste des cartes. 2 cartes pour celui qui a pris à l'atout et 3 cartes pour les autres. 
	 * @param joueurPrend joueur ayant pris a l'atout
	 */
	private void distribuerDeuxiemeTour(Joueur joueurPrend) {
		Joueur premierJoueur = table.joueurSuivant(joueurDonneur);
		this.joueurCourant = table.joueurSuivant(joueurDonneur);
		int nbCarte = 2;
		do {
			for (int i = 0; i < nbCarte; i++) {
				this.joueurCourant.getMain().ajouter(
						table.getPaquet().retirerPremiereCarte(),
						this.table.getCouleurAtout());
			}
			if (this.joueurCourant != joueurPrend) { // voir .equals()
				this.joueurCourant.getMain().ajouter(table.getPaquet().retirerPremiereCarte(), this.table.getCouleurAtout());
			}
			this.joueurCourant = table.joueurSuivant(this.joueurCourant);
		} while (this.joueurCourant != premierJoueur);
	}
	 
	public Joueur getJoueurDonneur() {
		return this.joueurDonneur;
	}

}
