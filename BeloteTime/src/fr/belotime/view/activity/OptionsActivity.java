package fr.belotime.view.activity;

import fr.belotime.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


public class OptionsActivity extends PreferenceActivity 
{
	
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);
	  
	  PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
	  /*Appel de la class Preference pour lancer l'écran des preferences*/
	  getFragmentManager().beginTransaction().replace(android.R.id.content,
	                new PreferencesFragment()).commit();
	 }
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
	}
}
