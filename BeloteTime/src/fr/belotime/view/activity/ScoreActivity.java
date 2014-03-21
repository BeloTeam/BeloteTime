package fr.belotime.view.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import fr.belotime.R;
import fr.belotime.view.utils.MakeFont;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends Activity implements OnClickListener {
	private SharedPreferences prefs;
	private List<Integer> listeScoreMancheEquipe1;
	private List<Integer> listeScoreMancheEquipe2;
	private int scorePartieEquipe1;
	private int scorePartieEquipe2;
	private int nbManches;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_score);
		//init attributs
		this.findViewById(R.id.activity_score_button_ajouter).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_options).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_regles).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_reset).setOnClickListener(this);
		
		PreferenceManager.setDefaultValues(this, R.xml.score, true);
		// recuperer les stats grace au preferences
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		

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
		((TextView)findViewById(R.id.textViewScoreTest)).
		setText("Taille : "+this.listeScoreMancheEquipe1.size()+"\nEquipe 1 : \n"+this.listeScoreMancheEquipe1.toString());
		
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

	

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // TODO
	 * Auto-generated method stub MenuInflater inflater = getMenuInflater();
	 * inflater.inflate(R.menu.menu_score, menu);
	 * 
	 * return super.onCreateOptionsMenu(menu); }
	 */

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
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
		default:
			break;
		}
		
	}



	private void ajouterScore() {
		// TODO Auto-generated method stub
		Editor editor = prefs.edit();
		editor.putString("scoreManchesEquipe1."+(this.nbManches+1), "50");
		editor.putString("scoreManchesEquipe2."+(this.nbManches+1), "20");
		this.nbManches++;
		editor.putString("nbManches", this.nbManches+"");
		editor.commit();
		((TextView)findViewById(R.id.textViewScoreTest)).setText("AJOUT : \n"+this.prefs.getAll().toString());
	}

	


	private void clearScore() {
		//modifier le treeSet pour faire un RAZ
		Editor editor = prefs.edit();
		/*for (int i = 1; i <= this.listeScoreMancheEquipe1.size(); i++) {
			editor.remove("scoreManchesEquipe1."+(i+1)).commit();
		}
		for (int i = 1; i <= this.listeScoreMancheEquipe2.size(); i++) {
			editor.remove("scoreManchesEquipe2."+(i+1)).commit();
			
		}*/
		editor.putString("nbManches", "0");
		editor.clear();
		
		this.nbManches=0;
		this.scorePartieEquipe1 = 0;
		this.scorePartieEquipe2 = 0;
		this.listeScoreMancheEquipe1.clear();
		this.listeScoreMancheEquipe2.clear();
		
		editor.commit();
		((TextView)findViewById(R.id.textViewScoreTest)).setText("Reset : \n"+this.prefs.getAll().toString());
		this.refreshScore();
	}



	private void refreshScore() {
		// TODO Auto-generated method stub
		
	}
	
	

}
