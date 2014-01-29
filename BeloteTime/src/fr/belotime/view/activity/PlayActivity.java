package fr.belotime.view.activity;

import fr.belotime.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
			//float fromXDelta, float toXDelta, float fromYDelta, float toYDelta
			anim_jouer = new TranslateAnimation(0,270,0,-200);
			anim_jouer.setDuration(500);
			anim_jouer.setFillAfter(true);
			asCoeur.startAnimation(anim_jouer);
			break;
		case R.id.neuf_carreau:
			anim_jouer = new TranslateAnimation(0,300,0,-290);
			anim_jouer.setDuration(500);
			anim_jouer.setFillAfter(true);
			neuf_carreau.startAnimation(anim_jouer);
			break;
		case R.id.roi_coeur:
			anim_jouer = new TranslateAnimation(0,0,0,-290);
			anim_jouer.setDuration(500);
			anim_jouer.setFillAfter(true);
			roi_coeur.startAnimation(anim_jouer);
			break;
		case R.id.dame_trefle:
			anim_jouer = new TranslateAnimation(0,30,0,-350);
			anim_jouer.setDuration(500);
			anim_jouer.setFillAfter(true);
			dame_trefle.startAnimation(anim_jouer);
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
