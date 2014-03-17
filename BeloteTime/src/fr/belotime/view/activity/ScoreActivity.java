package fr.belotime.view.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
	private int[] listeScoreMancheEquipe1;
	private int[] listeScoreMancheEquipe2;
	private int scorePartieEquipe1;
	private int scorePartieEquipe2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_score);
		
		this.findViewById(R.id.activity_score_button_ajouter).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_options).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_regles).setOnClickListener(this);
		this.findViewById(R.id.activity_score_button_reset).setOnClickListener(this);
		
		PreferenceManager.setDefaultValues(this, R.xml.score, false);
		// recuperer les stats grace au preferences
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.listeScoreMancheEquipe1 = getResources().getIntArray(R.array.activity_score_values_manches_equipe1);
		this.listeScoreMancheEquipe2 = getResources().getIntArray(R.array.activity_score_values_manches_equipe2);
		this.scorePartieEquipe1 = 0;
		this.scorePartieEquipe2 = 0;
		
		for (int i = 0; i < listeScoreMancheEquipe1.length; i++) {
			this.scorePartieEquipe1 += listeScoreMancheEquipe1[i];
		}
		for (int i = 0; i < listeScoreMancheEquipe2.length; i++) {
			this.scorePartieEquipe2 += listeScoreMancheEquipe2[i];
		}
		
		Toast.makeText(this, "Total : "+this.scorePartieEquipe1,Toast.LENGTH_SHORT).show();
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
		
	}



	private void clearScore() {
		//modifier le treeSet pour faire un RAZ
		Editor editor = prefs.edit();
		for (int i = 0; i < this.listeScoreMancheEquipe1.length; i++) {
			editor.remove("1."+(i+1));
		}
		for (int i = 0; i < this.listeScoreMancheEquipe2.length; i++) {
			editor.remove("2."+(i+1));
		}
		
		this.scorePartieEquipe1 = 0;
		this.scorePartieEquipe2 = 0;
		
		editor.commit();

		this.refreshScore();
		this.listeScoreMancheEquipe1 = getResources().getIntArray(R.array.activity_score_values_manches_equipe1);
		Toast.makeText(this, "Total : "+this.scorePartieEquipe1,Toast.LENGTH_SHORT).show();
	}



	private void refreshScore() {
		// TODO Auto-generated method stub
		
	}
	
	

}
