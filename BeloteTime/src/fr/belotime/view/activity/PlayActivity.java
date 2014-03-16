package fr.belotime.view.activity;

import fr.belotime.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class PlayActivity extends Activity implements OnClickListener {
	ImageView asCoeur,neuf_carreau,roi_coeur,dame_trefle; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_play);
		
		asCoeur = (ImageView) findViewById(R.id.as_coeur);
		neuf_carreau = (ImageView) findViewById(R.id.neuf_carreau);
		roi_coeur = (ImageView) findViewById(R.id.roi_coeur);
		dame_trefle = (ImageView) findViewById(R.id.dame_trefle);
		
		asCoeur.setOnClickListener(this);
		neuf_carreau.setOnClickListener(this);
		roi_coeur.setOnClickListener(this);
		dame_trefle.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		TranslateAnimation anim_jouer;
		switch (v.getId()) {
		case R.id.as_coeur:
			/*
			 * first set the view's location to the end position
			 * view.setLayoutParams(...); // set to (x, y)
			 * 
			 * then animate the view translating from (0, 0)
			 * TranslationAnimation ta = new TranslateAnimation(-x, -y, 0, 0);
			 * ta.setDuration(1000); view.startAnimation(ta);
			 */
			
			//a tester marche pas
			//asCoeur.setLayoutParams(new LayoutParams(-270,-200));
			
			//float fromXDelta, float toXDelta, float fromYDelta, float toYDelta
			anim_jouer = new TranslateAnimation(0,270,0,-200);
			anim_jouer.setDuration(500);
			anim_jouer.setFillAfter(true);
			asCoeur.startAnimation(anim_jouer);
			asCoeur.setOnClickListener(null);
			break;
		case R.id.neuf_carreau:
			anim_jouer = new TranslateAnimation(0,300,0,-290);
			anim_jouer.setDuration(500);
			anim_jouer.setFillAfter(true);
			neuf_carreau.startAnimation(anim_jouer);
			neuf_carreau.setOnClickListener(null);
			break;
		case R.id.roi_coeur:
			anim_jouer = new TranslateAnimation(0,0,0,-290);
			anim_jouer.setDuration(500);
			anim_jouer.setFillAfter(true);
			roi_coeur.startAnimation(anim_jouer);
			roi_coeur.setOnClickListener(null);
			break;
		case R.id.dame_trefle:
			anim_jouer = new TranslateAnimation(0,30,0,-350);
			anim_jouer.setDuration(500);
			anim_jouer.setFillAfter(true);
			dame_trefle.startAnimation(anim_jouer);
			dame_trefle.setOnClickListener(null);
			break;
		}
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition( R.anim.push_down,R.anim.push_up);
	}
	

}
