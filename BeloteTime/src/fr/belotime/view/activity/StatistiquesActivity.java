package fr.belotime.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fr.belotime.R;
import fr.belotime.view.utils.MakeFont;
import fr.belotime.view.utils.StatisticsXML;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_statistique);

		initAttributs();
		initDataStat();
		initFont();

		// recuperer les preferences dans une autre activites
		// SharedPreferences prefs =
		// PreferenceManager.getDefaultSharedPreferences(this);
		// stat_nb_parties_jouees.setText(prefs.getString("nomEquipe",
		// "equipe1"));
		findViewById(R.id.activity_statistique_button_reset)
				.setOnClickListener(this);

	}

	private void initDataStat() {
		// init des valeurs des stats
		this.stat_nb_parties_jouees.setText("84");
		this.stat_pourcentage_parties_gagnees.setText("62%");
		this.stat_temps_heure.setText("4");
		this.stat_temps_minute.setText("52");
		this.stat_nb_capots.setText("8");
		this.stat_nb_belotes.setText("36");
		this.stat_nb_dedans.setText("25");
	}

	private void initFont() {
		Typeface elegant_font = Typeface.createFromAsset(getAssets(),
				"fonts/ElegantIcons.ttf");
		Typeface roboto_font = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto.ttf");

		MakeFont.makeFont((TextView) findViewById(R.id.icon_stat), "#1f8dd6",
				roboto_font);
		MakeFont.makeFont(this.stat_title, roboto_font);
		MakeFont.makeFont(
				(TextView) findViewById(R.id.activity_statistique_button_reset),
				"#1f8dd6", elegant_font);
		MakeFont.makeFont(etoile_stat_nb_parties_jouees, "#f29c3b",
				elegant_font);
		MakeFont.makeFont(etoile_stat_pourcentage_parties_gagnees, "#f29c3b",
				elegant_font);
		MakeFont.makeFont(etoile_stat_temps, "#f29c3b", elegant_font);
		MakeFont.makeFont(etoile_stat_nb_capots, "#f29c3b", elegant_font);
		MakeFont.makeFont(etoile_stat_nb_belotes, "#f29c3b", elegant_font);
		MakeFont.makeFont(etoile_stat_nb_dedans, "#f29c3b", elegant_font);
		MakeFont.makeFont((TextView) findViewById(R.id.text_stat_nb_belotes),
				roboto_font);
		MakeFont.makeFont((TextView) findViewById(R.id.text_stat_nb_capots),
				roboto_font);
		MakeFont.makeFont((TextView) findViewById(R.id.text_stat_nb_dedans),
				roboto_font);
		MakeFont.makeFont(
				(TextView) findViewById(R.id.text_stat_nb_parties_jouees),
				roboto_font);
		MakeFont.makeFont(
				(TextView) findViewById(R.id.text_stat_pourcentage_parties_gagnees),
				roboto_font);
		MakeFont.makeFont((TextView) findViewById(R.id.text_stat_temps),
				roboto_font);
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

}
