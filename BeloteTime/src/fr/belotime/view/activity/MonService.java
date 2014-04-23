package fr.belotime.view.activity;

import fr.belotime.noyau.classesMetier.*;
import fr.belotime.noyau.control.*;
import fr.belotime.noyau.entite.*;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class MonService extends IntentService{	
	TableDeJeu table;
	
	public static final String DONNEUR = "le donneur";
	public static final String CARTERETOURNEE = "inconnue";
	public static final String MESSAGE = "MESSAGE1";
	
	public MonService(String name) {
		super("Partie de belote");
		table = new TableDeJeu();
		Toast.makeText(getBaseContext(),table.toString(),Toast.LENGTH_LONG).show();
		table.getGm().debuterPartie();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		System.out.println("coucou");	
	}

	private void envoyerMessage(){
		Intent i = new Intent(MESSAGE); 
		i.putExtra(DONNEUR, "il arrive");
		sendBroadcast(i);	
	}
	
}
