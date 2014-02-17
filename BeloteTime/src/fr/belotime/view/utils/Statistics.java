package fr.belotime.view.utils;

import java.util.List;

public class Statistics {

	private int parties_jouees;
	private int parties_gagnees_pourcent;
	private int temps_heure;
	private int temps_minute;
	private int capots;
	private int dedans;
	private int belote;

	public Statistics(int pj, int pgp, int th, int tm, int cap, int ded, int bel) {
		// TODO Auto-generated constructor stub
		parties_jouees = pj;
		parties_gagnees_pourcent = pgp;
		temps_heure = th;
		temps_minute = tm;
		capots = cap;
		dedans = ded;
		belote = bel;
	}

	public int getParties_jouees() {
		return parties_jouees;
	}

	public void setParties_jouees(int parties_jouees) {
		this.parties_jouees = parties_jouees;
	}

	public int getParties_gagnees_pourcent() {
		return parties_gagnees_pourcent;
	}

	public void setParties_gagnees_pourcent(int parties_gagnees_pourcent) {
		this.parties_gagnees_pourcent = parties_gagnees_pourcent;
	}

	public int getTemps_heure() {
		return temps_heure;
	}

	public void setTemps_heure(int temps_heure) {
		this.temps_heure = temps_heure;
	}

	public int getTemps_minute() {
		return temps_minute;
	}

	public void setTemps_minute(int temps_minute) {
		this.temps_minute = temps_minute;
	}

	public int getCapots() {
		return capots;
	}

	public void setCapots(int capots) {
		this.capots = capots;
	}

	public int getDedans() {
		return dedans;
	}

	public void setDedans(int dedans) {
		this.dedans = dedans;
	}

	public int getBelote() {
		return belote;
	}

	public void setBelote(int belote) {
		this.belote = belote;
	}

	public List<String> getStatistics() {
		return null;
	}

}
