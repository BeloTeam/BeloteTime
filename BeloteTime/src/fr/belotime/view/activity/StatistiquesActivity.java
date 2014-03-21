package fr.belotime.view.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import fr.belotime.R;

public class StatistiquesActivity extends Activity implements OnClickListener {
	private static final int CONFIRM_RESET = 0;
	private TextView stat_nb_parties_jouees;
	private TextView stat_pourcentage_parties_gagnees;
	private TextView stat_temps_heure;
	private TextView stat_temps_minute;
	private TextView stat_nb_capots;
	private TextView stat_nb_dedans;
	private TextView stat_nb_belotes;
	private TextView etoile_stat_nb_parties_jouees;
	private TextView etoile_stat_pourcentage_parties_gagnees;
	private TextView etoile_stat_temps;
	private TextView etoile_stat_nb_capots;
	private TextView etoile_stat_nb_belotes;
	private TextView etoile_stat_nb_dedans;
	private TextView stat_title;
	private ArrayList<Pair<String, Integer>> statsList;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_statistique);

		initAttributs();
		PreferenceManager.setDefaultValues(this, R.xml.statistics, true);

		// recuperer les stats grace au preferences
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		initDataStat();
		
		findViewById(R.id.activity_statistique_button_reset)
				.setOnClickListener(this);

	}

	private void initDataStat() {
		this.stat_nb_parties_jouees.setText(prefs.getString("parties_jouees",
				"0"));
		this.stat_pourcentage_parties_gagnees.setText(prefs.getString(
				"parties_gagnees_pourcent", "0"));
		this.stat_temps_heure.setText(prefs.getString("temps_heure", "0"));
		this.stat_temps_minute.setText(prefs.getString("temps_minute", "0"));
		this.stat_nb_capots.setText(prefs.getString("capots", "0"));
		this.stat_nb_dedans.setText(prefs.getString("dedans", "0"));
		this.stat_nb_belotes.setText(prefs.getString("belotes", "0"));
		
		
		initEtoiles(this.etoile_stat_temps,prefs.getString("temps_etoiles", "0"));
		initEtoiles(this.etoile_stat_nb_belotes,prefs.getString("belotes_etoiles", "0"));
		initEtoiles(this.etoile_stat_nb_capots,prefs.getString("capots_etoiles", "0"));
		initEtoiles(this.etoile_stat_nb_dedans,prefs.getString("dedans_etoiles", "0"));
		initEtoiles(this.etoile_stat_nb_parties_jouees,prefs.getString("parties_jouees_etoiles", "0"));
		initEtoiles(this.etoile_stat_pourcentage_parties_gagnees,prefs.
				getString("parties_gagnees_pourcent_etoiles", "0"));
	}

	private void initEtoiles(TextView etoileView, String nbEtoile) {
		int nb;
		try {
			nb = Integer.parseInt(nbEtoile);
		} catch (Exception e) {
			e.printStackTrace();
			nb = 0;
		}

		// TODO Auto-generated method stub
		switch (nb) {
		case 0:
			etoileView.setText(R.string.activity_statistique_icon_0_etoile);
			break;
		case 1:
			etoileView.setText(R.string.activity_statistique_icon_1_etoile);
			break;
		case 2:
			etoileView.setText(R.string.activity_statistique_icon_2_etoile);
			break;
		case 3:
			etoileView.setText(R.string.activity_statistique_icon_3_etoile);
			break;
		case 4:
			etoileView.setText(R.string.activity_statistique_icon_4_etoile);
			break;
		case 5:
			etoileView.setText(R.string.activity_statistique_icon_5_etoile);
			break;
		default:
			//lever un exception
			break;
		}
	}

	private void initAttributs() {
		stat_title = (TextView) findViewById(R.id.title_stat);
		stat_nb_parties_jouees = (TextView) findViewById(R.id.stat_nb_parties_jouees);
		stat_pourcentage_parties_gagnees = (TextView) findViewById(R.id.stat_pourcentage_parties_gagnees);
		stat_temps_heure = (TextView) findViewById(R.id.stat_temps_heure);
		stat_temps_minute = (TextView) findViewById(R.id.stat_temps_minute);
		stat_nb_capots = (TextView) findViewById(R.id.stat_nb_capots);
		stat_nb_dedans = (TextView) findViewById(R.id.stat_nb_dedans);
		stat_nb_belotes = (TextView) findViewById(R.id.stat_nb_belotes);
		etoile_stat_nb_parties_jouees = (TextView) findViewById(R.id.etoile_stat_nb_parties_jouees);
		etoile_stat_pourcentage_parties_gagnees = (TextView) findViewById(R.id.etoile_stat_pourcentage_parties_gagnees);
		etoile_stat_temps = (TextView) findViewById(R.id.etoile_stat_temps);
		etoile_stat_nb_capots = (TextView) findViewById(R.id.etoile_stat_nb_capots);
		etoile_stat_nb_belotes = (TextView) findViewById(R.id.etoile_stat_nb_belotes);
		etoile_stat_nb_dedans = (TextView) findViewById(R.id.etoile_stat_nb_dedans);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_statistique_button_reset:
			showDialog(CONFIRM_RESET);
			break;
		default:
			// lever exception ici
			break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog;
		AlertDialog.Builder builder;
		switch (id) {
		case CONFIRM_RESET:
			builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Voulez-vous vraiment rétinitialiser les statistiques ?")
					.setCancelable(true)
					.setPositiveButton("Oui",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// reinit les stat
									resetStats();
								}
							}).setNegativeButton("Non", null);
			builder.setTitle("REINITIALISATION STATISTIQUES");
			dialog = builder.create();
			break;
		default:
			dialog = null;
		}
		return dialog;

	}
	
	private void resetStats(){
		Editor editor = prefs.edit(); 
		editor.putString("parties_jouees", "0");
		editor.putString("parties_gagnees_pourcent", "0");
		editor.putString("temps_heure", "0");
		editor.putString("temps_minute", "0");
		editor.putString("capots", "0");
		editor.putString("dedans", "0");
		editor.putString("belotes", "0");
		
		editor.putString("parties_jouees_etoiles", "0");
		editor.putString("parties_gagnees_pourcent_etoiles", "0");
		editor.putString("temps_etoiles", "0");
		editor.putString("capots_etoiles", "0");
		editor.putString("dedans_etoiles", "0");
		editor.putString("belotes_etoiles", "0");
		
		editor.commit(); 
		this.initDataStat();
	}

}
