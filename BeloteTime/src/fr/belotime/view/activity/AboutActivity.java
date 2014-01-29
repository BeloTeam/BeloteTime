package fr.belotime.view.activity;

import fr.belotime.R;
import fr.belotime.view.utils.MakeFont;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity 
{
	
	private TextView activity_about_icon_about;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about);

		initAttributs();
		initFont();
	}


	private void initFont() {
		Typeface elegant_font = Typeface.createFromAsset(getAssets(),"fonts/ElegantIcons.ttf");
		Typeface roboto_font = Typeface.createFromAsset(getAssets(),"fonts/Roboto.ttf");
		MakeFont.makeFont(activity_about_icon_about,elegant_font);
		MakeFont.makeFont((TextView)findViewById(R.id.activity_about_title),roboto_font);
	}


	private void initAttributs() {
		activity_about_icon_about = (TextView)findViewById(R.id.activity_about_icon_about);
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
	}

}
