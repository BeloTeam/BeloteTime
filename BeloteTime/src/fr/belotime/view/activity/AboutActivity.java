package fr.belotime.view.activity;

import fr.belotime.R;
import fr.belotime.view.utils.MakeFont;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends Activity implements OnTouchListener {

	private TextView activity_about_icon_about;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about);

		initAttributs();
		initFont();
		findViewById(R.id.activity_about_text_contact_github)
				.setOnTouchListener(this);
		findViewById(R.id.activity_about_text_contact_mail).setOnTouchListener(
				this);

	}

	private void initFont() {
		Typeface elegant_font = Typeface.createFromAsset(getAssets(),
				"fonts/ElegantIcons.ttf");
		// MakeFont.makeFont(activity_about_icon_about,elegant_font);
		// MakeFont.makeFont((TextView)findViewById(R.id.activity_about_icon_contact_mail),elegant_font);
	}

	private void initAttributs() {
		activity_about_icon_about = (TextView) findViewById(R.id.activity_about_icon_about);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
	}

	@Override
	public boolean onTouch(View v, MotionEvent arg1) {
		switch (v.getId()) {
		case R.id.activity_about_text_contact_github:
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://beloteam.github.io/BeloteTime"));
			startActivity(browserIntent);
			break;
		case R.id.activity_about_text_contact_mail:
			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
					new String[] { "beloteam@gmail.com" });
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			break;
		default:
			// lever exception ici
			break;
		}
		return false;
	}

}
