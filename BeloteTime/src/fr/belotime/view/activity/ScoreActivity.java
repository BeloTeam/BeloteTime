package fr.belotime.view.activity;

import java.util.ArrayList;
import java.util.List;
import fr.belotime.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends Activity implements OnClickListener {
	private SharedPreferences prefs;
	private List<Integer> listeScoreMancheEquipe1;
	private List<Integer> listeScoreMancheEquipe2;
	private int scorePartieEquipe1;
	private int scorePartieEquipe2;
	private int nbManches;
	private Dialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_score);
		//init attributs
		this.findViewById(R.id.activity_score_button_ajouter).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_options).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_regles).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_reset).setOnClickListener(this);

		PreferenceManager.setDefaultValues(this, R.xml.score, true);
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		
		// recuperer les stats grace au preferences
		this.nbManches = Integer.parseInt(this.prefs.getString("nbManches", "0"));
		this.listeScoreMancheEquipe1 = new ArrayList<Integer>();
		this.listeScoreMancheEquipe2 = new ArrayList<Integer>();	

		
		//recuperation des scores
		for (int i = 1; i <= nbManches ; i++) {
			this.listeScoreMancheEquipe1.add(Integer.parseInt(this.prefs.getString("scoreManchesEquipe1."+i, "0")));
		}
		for (int i = 1; i <= nbManches; i++) {
			this.listeScoreMancheEquipe2.add(Integer.parseInt(this.prefs.getString("scoreManchesEquipe2."+i, "0")));
		}
		
		//affichages des scores
		String affich = this.listeScoreMancheEquipe1.size()+"\n "+this.listeScoreMancheEquipe1.toString();
		((TextView)findViewById(R.id.textViewScoreTest)).setText(affich);
		
		
		this.scorePartieEquipe1 = 0;
		this.scorePartieEquipe2 = 0;

		for (int score : this.listeScoreMancheEquipe1) {
			this.scorePartieEquipe1+=score;
		}
		for (int score : this.listeScoreMancheEquipe2) {
			this.scorePartieEquipe2+=score;
		}

		
		this.refreshScore();
	}


	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
	}



	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.activity_score_button_ajouter:
			ajouterScore();
			break;
		case R.id.activity_score_button_options:
			intent = new Intent(ScoreActivity.this, ScoreOptionActivity.class);
			startActivity(intent);
			break;
		case R.id.activity_score_button_regles:
			intent = new Intent(ScoreActivity.this, RulesActivity.class);
			startActivity(intent);
			break;
		case R.id.activity_score_button_reset:
			clearScore();
			break;
		case R.id.activity_score_button_valider:
			String scoreEquipe1 = ((EditText)this.dialog.findViewById(R.id.activity_score_editText_score_equipe1))
			.getText().toString();
			
			String scoreEquipe2 = ((EditText)this.dialog.findViewById(R.id.activity_score_editText_score_equipe2))
					.getText().toString();
			
			if(scoreEquipe1.length() !=0 && scoreEquipe2.length() !=0){
				int score1 = Integer.parseInt(scoreEquipe1);
				int score2 = Integer.parseInt(scoreEquipe2);
				ajouterScoreSaisie(score1,score2);
			}
			else{
				Toast.makeText(this, "Erreur de saisie",Toast.LENGTH_SHORT).show();
			}
			this.dialog.hide();
			break;
		default:
			//lever une exception
			break;
		}
	}



	private void ajouterScoreSaisie(Integer scoreEquipe1, Integer scoreEquipe2) {
		Editor editor = prefs.edit();
		editor.putString("scoreManchesEquipe1."+(this.nbManches+1), scoreEquipe1.toString());
		editor.putString("scoreManchesEquipe2."+(this.nbManches+1), scoreEquipe2.toString());
		this.listeScoreMancheEquipe1.add(scoreEquipe1);
		this.listeScoreMancheEquipe2.add(scoreEquipe2);
		this.scorePartieEquipe1+=scoreEquipe1;
		this.scorePartieEquipe2+=scoreEquipe2;
		this.nbManches++;
		editor.putString("nbManches", this.nbManches+"");
		editor.commit();
		((TextView)findViewById(R.id.textViewScoreTest)).setText("AJOUT : \n"+this.prefs.getAll().toString());
	}


	private void ajouterScore() {
		//appel de la dialog
		// custom dialog
		this.dialog = new Dialog(this);
		this.dialog.setContentView(R.layout.addscoredialog);
		this.dialog.setTitle("AJOUTER UN SCORE");
		this.dialog.findViewById(R.id.activity_score_button_valider).setOnClickListener(this);
		String nomEquipe1="Score équipe "+this.prefs.getString("nomEquipe1", "Nous");
		String nomEquipe2="Score équipe "+this.prefs.getString("nomEquipe2", "Eux");
		
		((TextView)this.dialog.findViewById(R.id.activity_score_text_score_equipe1))
		.setText(nomEquipe1);
		((TextView)this.dialog.findViewById(R.id.activity_score_text_score_equipe2))
		.setText(nomEquipe2);
		
		this.dialog.show();
	}


	private void clearScore() {
		//modifier le treeSet pour faire un RAZ
		Editor editor = prefs.edit();
		for (int i = 1; i <= this.listeScoreMancheEquipe1.size()+1; i++) {
			editor.remove("scoreManchesEquipe1."+i);
		}
		for (int i = 1; i <= this.listeScoreMancheEquipe2.size()+1; i++) {
			editor.remove("scoreManchesEquipe2."+i);
		}
		editor.putString("nbManches", "0");
		editor.commit();
		
		this.nbManches=0;
		this.scorePartieEquipe1 = 0;
		this.scorePartieEquipe2 = 0;
		this.listeScoreMancheEquipe1.clear();
		this.listeScoreMancheEquipe2.clear();
		
		((TextView)findViewById(R.id.textViewScoreTest)).setText("Reset : \n"+this.prefs.getAll().toString());
		this.refreshScore();
	}



	private void refreshScore() {
		
	}




	

}
