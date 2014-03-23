package fr.belotime.view.activity;

import fr.belotime.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class ScoreOptionActivity extends PreferenceActivity {
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);
	  overridePendingTransition(R.anim.push_down, R.anim.push_up);
	  PreferenceManager.setDefaultValues(this, R.xml.preferencescore, false);
	  
	  /*Appel de la class Preference pour lancer l'écran des preferences*/
	  addPreferencesFromResource(R.xml.preferencescore);
	 }
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
	}
}
