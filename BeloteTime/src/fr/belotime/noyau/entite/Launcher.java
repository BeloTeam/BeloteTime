package fr.belotime.noyau.entite;

import fr.belotime.noyau.classesMetier.TableDeJeu;

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



public class Launcher {
	public static void main(String[] args) {
		TableDeJeu t = new TableDeJeu();
		//t.getTas().melanger(200); Pour les tests, evitons de melanger les cartes au debut
		t.getGm().debuterPartie();
	}
}
