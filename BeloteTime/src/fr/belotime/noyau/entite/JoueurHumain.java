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
import fr.belotime.noyau.classesMetier.FigureEnum;
import fr.belotime.noyau.classesMetier.Main;
import fr.belotime.noyau.classesMetier.Paquet;
import fr.belotime.noyau.classesMetier.PositionEnum;
import fr.belotime.noyau.classesMetier.TableDeJeu;
import fr.belotime.noyau.control.Terminal;


public class JoueurHumain extends Joueur {

	/**
	 * Surcharge du constructeur Joueur, création d'un joueur humain.
	 * @param position du joueur sur la table
	 * @param nom du joueur humain
	 * @param table ou est assie le joueur humain
	 * */
	public JoueurHumain(PositionEnum position, String nom, TableDeJeu table) {
		super(position, nom, table);

	}

	/**
	 * Retourne si oui ou non le joueur humain prend au premier tour
	 * @return boolean
	 */
	public boolean prendPremiereDonne() {
		Terminal.ecrireStringln("Votre main :\n" + this.getMain()
				+ "\nPrenez vous pour l'atout : " + this.getTable().getCarteRetournee().toString()
				+ " (1-oui/0-non)");
		int rep = 0;
		rep = Terminal.lireInt();
		if (rep == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retourne si oui ou non le joueur humain prend au deuxieme tour
	 * @return CouleurEnum
	 */
	@Override
	public CouleurEnum prendDeuxiemeDonne() {
		Terminal.ecrireStringln("Votre main :\n" + this.getMain()
				+ "Quelle couleur prenez vous : "
				+ " (1-Coeur/2-Pique/3-Carreau/4-Trefle/5-passe)");
		int rep = 0;
		rep = Terminal.lireInt();
		//TODO Ne pas proposer la couleur de la carte du paquet qui est retournée!
		switch (rep) {
		case 1:
			return CouleurEnum.Coeur;
		case 2:
			return CouleurEnum.Pique;
		case 3:
			return CouleurEnum.Carreau;
		case 4:
			return CouleurEnum.Trefle;
		case 5:
			return null;
		default : return null;
		}
	}

	/**
	 * Afficher et pouvoir selectionner une carte parmis la liste des cartes retenues
	 * @param cartesPossibles
	 * @return
	 */
	@Override
	public Carte selectionnerUneCarte(Main cartesPossibles) {
		int tailleEnsembleCartePropose;
		Carte carteSelectionnee = null;
		tailleEnsembleCartePropose = cartesPossibles.getTailleMain();
		
		Terminal.ecrireStringln("\nVous avez le choix entre : \n"+ cartesPossibles);		
		Terminal.ecrireStringln("\nChoisissez une carte : [entre 0 et "+ (cartesPossibles.getTailleMain() - 1) + "]");
		int rep = Terminal.lireInt();

		if (rep < tailleEnsembleCartePropose) {
			// Sur l'interface le mieux serait de récupérer directement la carte selectionnée 
			// comme ça on peut directement aller chercher la carte dans la main par le getteur adequat.			
			carteSelectionnee = cartesPossibles.hashtableToList().get(rep);		
			
			// Si le joueur possède la dame et le roi à l'atout... 
			if(super.hasBeloteEtRe()) {
				// ...alors il annonce la belote/rebelote lorsqu'il joue la dame / le roi.
				if (carteSelectionnee.getCouleur() == this.getTable().getCouleurAtout() && carteSelectionnee.getFigure() == FigureEnum.Dame) {
					this.getTable().setBeloteAnnoncee(true);
					Terminal.ecrireStringln("J'annonce (Re)Belote");
				}
				if (carteSelectionnee.getCouleur() == this.getTable().getCouleurAtout() && carteSelectionnee.getFigure() == FigureEnum.Roi) {
					this.getTable().setRebeloteAnnoncee(true);
					Terminal.ecrireStringln("J'annonce (Re)Belote");
				}
			}
			
			this.getMain().supprimer(carteSelectionnee);			 
		} 
		else {
			Terminal.ecrireStringln("ERREUR");
		}	
		
		return carteSelectionnee;
	}
	
	/**
	 * Action permettant de couper un tas de cartes
	 * @param tas Paquet
	 * @return boolean
	 */
	public boolean coupe(Paquet tas) {
		int pourcentageCoupe = 0;
		Terminal.ecrireString("Veuillez entrer une valeur de coupe(poucentage): ");
		pourcentageCoupe = Terminal.lireInt();
		return tas.couper(pourcentageCoupe);
	}
	
	/**
	 * Action permettant d'analyser la main courante (belotes?)
	 * @return void
	 */
	@Override
	public void analyserSonJeu() {
		if(this.getMain().hasBeloteRebelote(this.getTable().getCouleurAtout())) {
			super.setHasBeloteEtRe(true);
			this.getEquipeDuJoueur().setEquipeHasBeloteEtRe(true);
		} 
	}
}
