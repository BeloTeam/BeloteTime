package fr.belotime.view.activity;

import fr.belotime.R;
import fr.belotime.view.utils.MakeFont;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

public class ScoreActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_score);
		
		initFont();
	}


	private void initFont() {
		Typeface elegant_font = Typeface.createFromAsset(getAssets(),"fonts/ElegantIcons.ttf");
		Typeface roboto_font = Typeface.createFromAsset(getAssets(),"fonts/Roboto.ttf");
		
		MakeFont.makeFont((TextView)findViewById(R.id.activity_score_icon_score), elegant_font);
		MakeFont.makeFont((TextView)findViewById(R.id.activity_score_title), roboto_font);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_score, menu);
	    
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
	}

}
