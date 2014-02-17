package fr.belotime.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import fr.belotime.R;
import fr.belotime.view.utils.MakeFont;
import fr.belotime.view.utils.Rotate3dAnimation;

public class HomeActivity extends Activity implements OnTouchListener,
		OnClickListener {

	private static final long DUREE_ANIM_BOUTON = 1000;
	private View button_jouer;
	private View button_option;
	private View button_stat;
	private View button_apropos;
	private View button_regles;
	private Button button_saisie_score;
	private TranslateAnimation anim_up;
	private Rotate3dAnimation anim_down;
	private Rotate3dAnimation anim_down2;
	// private TextView text_boutton_jouer;
	private TextView icon_option;
	private TextView icon_stat;
	private TextView icon_regles;
	private TextView icon_apropos;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home);

		initAttributs();
		initListener();
		initFont();
		initAnimationButton();
	}

	private void initAnimationButton() {
		// Rotate3dAnimation skew3 = new Rotate3dAnimation(90,0,0,0,0, true);
		// skew3.setDuration(DUREE_ANIM_BOUTON);
		// skew3.setFillAfter(true);
		TranslateAnimation anim_jouer = new TranslateAnimation(0, 0, -1000, 0);
		anim_jouer.setDuration(DUREE_ANIM_BOUTON);
		anim_jouer.setFillAfter(true);
		button_jouer.startAnimation(anim_jouer);

		TranslateAnimation anim_option = new TranslateAnimation(-1000, 0, 0, 0);
		anim_option.setDuration(DUREE_ANIM_BOUTON);
		anim_option.setFillAfter(true);
		button_option.startAnimation(anim_option);

		TranslateAnimation anim_statistique = new TranslateAnimation(1000, 0,
				0, 0);
		anim_statistique.setDuration(DUREE_ANIM_BOUTON);
		anim_statistique.setFillAfter(true);
		button_stat.startAnimation(anim_statistique);

		anim_up = new TranslateAnimation(0, 0, 0, 0);
		anim_up.setFillAfter(true);
		anim_down = new Rotate3dAnimation(0, 4, 0, 5, 0, false);
		anim_down.setFillAfter(true);
		anim_down2 = new Rotate3dAnimation(0, 2, 0, 5, 0, false);
		anim_down2.setFillAfter(true);
	}

	private void initFont() {
		// ICON FONT
		Typeface elegant_font = Typeface.createFromAsset(getAssets(),
				"fonts/ElegantIcons.ttf");
		// Typeface roboto_font =
		// Typeface.createFromAsset(getAssets(),"fonts/Roboto.ttf");

		MakeFont.makeFont(button_saisie_score, "#1f8dd6", elegant_font);
		MakeFont.makeFont(icon_option, Color.WHITE, elegant_font);
		MakeFont.makeFont(icon_stat, Color.WHITE, elegant_font);
		MakeFont.makeFont(icon_regles, Color.WHITE, elegant_font);
		MakeFont.makeFont(icon_apropos, Color.WHITE, elegant_font);
	}

	private void initListener() {
		button_jouer.setOnClickListener(this);
		button_option.setOnClickListener(this);
		button_stat.setOnClickListener(this);
		button_apropos.setOnClickListener(this);
		button_regles.setOnClickListener(this);
		button_saisie_score.setOnClickListener(this);
		button_option.setOnTouchListener(this);
		button_stat.setOnTouchListener(this);
		button_apropos.setOnTouchListener(this);
		button_jouer.setOnTouchListener(this);
		button_regles.setOnTouchListener(this);
		button_saisie_score.setOnTouchListener(this);
	}

	private void initAttributs() {
		button_jouer = findViewById(R.id.activity_home_layout_button_jouer);
		button_option = findViewById(R.id.activity_home_layout_button_option);
		button_stat = findViewById(R.id.activity_home_layout_button_statistiques);
		button_apropos = findViewById(R.id.activity_home_layout_button_apropos);
		button_regles = findViewById(R.id.activity_home_layout_button_regles);
		button_saisie_score = (Button) findViewById(R.id.activity_home_button_saisie_score);
		// ImageView perso1 = (ImageView)findViewById(R.id.imageView2);
		icon_option = (TextView) findViewById(R.id.activity_home_icon_button_option);
		icon_stat = (TextView) findViewById(R.id.activity_home_icon_button_statistiques);
		icon_regles = (TextView) findViewById(R.id.activity_home_icon_button_rules);
		icon_apropos = (TextView) findViewById(R.id.activity_home_icon_button_apropos);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.activity_home_layout_button_jouer:
			intent = new Intent(HomeActivity.this, PlayActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_down, R.anim.push_up);
			break;
		case R.id.activity_home_layout_button_option:
			intent = new Intent(HomeActivity.this, OptionsActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_down, R.anim.push_up);
			break;
		case R.id.activity_home_layout_button_statistiques:
			intent = new Intent(HomeActivity.this, StatistiquesActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_down, R.anim.push_up);
			break;
		case R.id.activity_home_layout_button_apropos:
			intent = new Intent(HomeActivity.this, AboutActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_down, R.anim.push_up);
			break;
		case R.id.activity_home_layout_button_regles:
			intent = new Intent(HomeActivity.this, RulesActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_down, R.anim.push_up);
			break;
		case R.id.activity_home_button_saisie_score:
			intent = new Intent(HomeActivity.this, ScoreActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_down, R.anim.push_up);
			break;
		default:
			//lever exception ici
			break;
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			switch (v.getId()) {
			case R.id.activity_home_layout_button_jouer:
				button_jouer.startAnimation(anim_down2);
				// button_jouer.setScrollX(20);
				break;
			case R.id.activity_home_layout_button_option:
				button_option.startAnimation(anim_down);
				break;
			case R.id.activity_home_layout_button_statistiques:
				button_stat.startAnimation(anim_down);
				break;
			case R.id.activity_home_layout_button_apropos:
				button_apropos.startAnimation(anim_down);
				break;
			case R.id.activity_home_layout_button_regles:
				button_regles.startAnimation(anim_down);
				break;
			case R.id.activity_home_button_saisie_score:
				button_saisie_score.startAnimation(anim_down);
				break;
			default:
				//lever exception ici
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			switch (v.getId()) {
			case R.id.activity_home_layout_button_jouer:
				button_jouer.startAnimation(anim_up);
				break;
			case R.id.activity_home_layout_button_option:
				button_option.startAnimation(anim_up);
				break;
			case R.id.activity_home_layout_button_statistiques:
				button_stat.startAnimation(anim_up);
				break;
			case R.id.activity_home_layout_button_apropos:
				button_apropos.startAnimation(anim_up);
				break;
			case R.id.activity_home_layout_button_regles:
				button_regles.startAnimation(anim_up);
				break;
			case R.id.activity_home_button_saisie_score:
				button_saisie_score.startAnimation(anim_up);
				break;
			default:
				//lever exception ici
				break;
			}
		}
		return false;
	}

}
