package fr.belotime.view.activity;

import java.util.ArrayList;
import java.util.List;
import fr.belotime.R;
import fr.belotime.view.utils.IconFontTextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends Activity implements OnClickListener {
	private static final int DIALOG_CONFIRM_RESET = 0;
	private static final int DIALOG_EXPLICATIONS = 1;
	private SharedPreferences prefs;
	private List<Integer> listeScoreMancheEquipe1;
	private List<Integer> listeScoreMancheEquipe2;
	private Integer scorePartieEquipe1;
	private Integer scorePartieEquipe2;
	private Integer nbManches;
	private Dialog dialog;
	private TableLayout tableLayout;
	private TextView titre_eq1;
	private TextView titre_eq2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_score);
		this.initAttributs();
		this.calculScorePartie();
		this.refreshLayoutNomEquipe();
		// init nom dequipes + score a faire aussi dans le onpause
		this.calculerScore();
		this.empecherVeille();
		this.afficherExplications();
	}

	private void initAttributs() {
		this.findViewById(R.id.activity_score_button_ajouter).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_options).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_regles).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_reset).setOnClickListener(this);
		this.titre_eq1 = (TextView)this.findViewById(R.id.activity_score_text_equipe1);
		this.titre_eq2 = (TextView)this.findViewById(R.id.activity_score_text_equipe2);
		this.titre_eq1.setOnClickListener(this);
		this.titre_eq2.setOnClickListener(this);
		this.listeScoreMancheEquipe1 = new ArrayList<Integer>();
		this.listeScoreMancheEquipe2 = new ArrayList<Integer>();
		
		PreferenceManager.setDefaultValues(this, R.xml.score, true);
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		// recuperer les stats grace au preferences
		this.nbManches = Integer.parseInt(this.prefs.getString("nbManches", "0"));
	}

	@SuppressWarnings("deprecation")
	private void afficherExplications() {
		if(this.prefs.getBoolean("explications", true)){
			showDialog(DIALOG_EXPLICATIONS);
		}
	}

	private void calculScorePartie() {
		this.scorePartieEquipe1 = 0;
		this.scorePartieEquipe2 = 0;

		for (int score : this.listeScoreMancheEquipe1) {
			this.scorePartieEquipe1+=score;
		}
		for (int score : this.listeScoreMancheEquipe2) {
			this.scorePartieEquipe2+=score;
		}
	}


	private void empecherVeille() {
		if(this.prefs.getBoolean("veille",true)){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
		else {
			getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}

	private void refreshLayoutNomEquipe(){
		//initialisation des nom dequipe
		this.titre_eq1.setText(this.prefs.getString("nomEquipe1", "Nous"));
		this.titre_eq2.setText(this.prefs.getString("nomEquipe2", "Eux"));
	}
	
	private void refreshLayoutScoreTotaux() {
		((TextView) this.findViewById(R.id.activity_score_partie_equipe1))
				.setText(this.scorePartieEquipe1.toString());
		((TextView) this.findViewById(R.id.activity_score_partie_equipe2))
				.setText(this.scorePartieEquipe2.toString());
	}
	
	private void calculerScore() {
		
		loadScore();
		((TableLayout)this.findViewById(R.id.activity_score_table_layout)).removeAllViews();
		
		
		if(!this.prefs.getBoolean("cumule", false)){
			for (int i = 0; i < this.nbManches; i++) {
				ajouterDansTableRow(this.listeScoreMancheEquipe1.get(i),
						this.listeScoreMancheEquipe2.get(i));
			}
		}
		else {
			ArrayList<Integer> eq1Cumule = new ArrayList<Integer>();
			ArrayList<Integer> eq2Cumule = new ArrayList<Integer>();
			
			for (int i = 0;i<this.nbManches;i++) {
				if(i==0){
					eq1Cumule.add(this.listeScoreMancheEquipe1.get(i));
					eq2Cumule.add(this.listeScoreMancheEquipe2.get(i));
				}
				else{
					eq1Cumule.add(this.listeScoreMancheEquipe1.get(i)+eq1Cumule.get(i-1));
					eq2Cumule.add(this.listeScoreMancheEquipe2.get(i)+eq2Cumule.get(i-1));
				}
				ajouterDansTableRow(eq1Cumule.get(i),eq2Cumule.get(i));
			}
		}
			
		this.calculScorePartie();
		this.refreshLayoutScoreTotaux();
	}

	@SuppressWarnings("deprecation")
	private void ajouterDansTableRow(Integer mancheEq1, Integer mancheEq2) {
		this.tableLayout = (TableLayout) findViewById(R.id.activity_score_table_layout);
		
		TableRow tr = new TableRow(this);
		IconFontTextView txtView = new IconFontTextView(this);
		IconFontTextView txtView2 = new IconFontTextView(this);

	    TableRow.LayoutParams tr_layout = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
		tr_layout.setMargins(0, 0, 310, 0);
	    txtView.setLayoutParams(tr_layout);
		txtView.setText(mancheEq1.toString());
		txtView.setTextAppearance(this, android.R.attr.textAppearanceMedium);
		txtView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Roboto.ttf"));
		txtView.setGravity(Gravity.CENTER);
		txtView.setTextSize(20);
		
		
		txtView2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
		txtView2.setText(mancheEq2.toString());
		txtView2.setTextAppearance(this, android.R.attr.textAppearanceMedium);
		txtView2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Roboto.ttf"));
		txtView2.setTextSize(20);
		txtView2.setGravity(Gravity.CENTER);
		
		tr.addView(txtView);
		tr.addView(txtView2);
		this.tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
	}


	private void loadScore() {

		if(this.listeScoreMancheEquipe1.size()==0 && this.listeScoreMancheEquipe2.size()==0){
			//recuperation des scores
			for (int i = 1; i < nbManches+1 ; i++) {
				this.listeScoreMancheEquipe1.add(Integer.parseInt(this.prefs.getString("scoreManchesEquipe1."+i, "0")));
			}
			for (int i = 1; i < nbManches+1; i++) {
				this.listeScoreMancheEquipe2.add(Integer.parseInt(this.prefs.getString("scoreManchesEquipe2."+i, "0")));
			}
			
			//affichages des scores
			String affich = this.listeScoreMancheEquipe1.size()+"\n "+this.listeScoreMancheEquipe1.toString();
			Log.d("scores", affich);
		}
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
		this.empecherVeille();
		this.refreshLayoutNomEquipe();
		this.calculerScore();
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
		Intent intent;
		
		switch (v.getId()) {
		case R.id.activity_score_button_ajouter:
			ajouterScoreDialog();
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
			if(this.listeScoreMancheEquipe1.size() > 0)
				showDialog(DIALOG_CONFIRM_RESET);
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
				this.dialog.hide();
			}
			else{
				Toast.makeText(this, "Veuillez saisir les 2 scores",Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.activity_score_dialog_explication_button_ok:
			boolean explicationsAffichage = 
			((CheckBox)this.dialog.findViewById(R.id.activity_score_dialog_explication_checkbox_afficher)).isChecked();
			this.prefs.edit().putBoolean("explications",!explicationsAffichage ).commit();
			this.dialog.hide();
			break;
		case R.id.activity_score_text_equipe1:
			ajouterScoreDialog();
			break;
		case R.id.activity_score_text_equipe2:
			ajouterScoreDialog();
			break;
		default:
			//lever une exception
			break;
		}
	}

	
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;
		switch (id) {
		case DIALOG_CONFIRM_RESET:
			builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Voulez-vous vraiment rétinitialiser les scores ?")
					.setCancelable(true)
					.setPositiveButton("Oui",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// reinit les stat
									clearScore();
								}
							}).setNegativeButton("Non", null);
			builder.setTitle("REINITIALISATION SCORES");
			dialog = builder.create();
			break;
		case DIALOG_EXPLICATIONS:
			this.dialog = new Dialog(this);
			this.dialog.setContentView(R.layout.layout_explications_score);
			this.dialog.setTitle("FEUILLE DE SCORE");
			this.dialog.findViewById(R.id.activity_score_dialog_explication_button_ok).setOnClickListener(this);
			break;
		default:
			dialog = null;
		}
		return dialog;

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
		Log.d("scores", "AJOUT : \n"+this.prefs.getAll().toString());
		
		this.calculerScore();
		//this.ajouterTableRow(scoreEquipe1, scoreEquipe2);

		this.refreshLayoutScoreTotaux();
	}


	private void ajouterScoreDialog() {
		//appel de la dialog
		// custom dialog
		this.dialog = new Dialog(this);
		this.dialog.setContentView(R.layout.layout_addscoredialog);
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
		
		Log.d("scores","Reset : \n"+this.prefs.getAll().toString());
		((TableLayout)this.findViewById(R.id.activity_score_table_layout)).removeAllViews();
		
		this.refreshLayoutScoreTotaux();
	}
}
