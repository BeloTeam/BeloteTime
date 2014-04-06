package fr.belotime.noyau.control;

import fr.belotime.noyau.entite.Joueur;

public class DonneurEvent {
	private Joueur oldDonneur;
	private Joueur newDonneur;
	
	public DonneurEvent(Joueur oldDonneur, Joueur newDonneur) {
        this.oldDonneur = oldDonneur;
        this.newDonneur = newDonneur;
    }

    public Joueur getOdlDonneur() {
        return this.oldDonneur;
    }

    public Joueur getNewPression() {
        return this.newDonneur;
    }
}
