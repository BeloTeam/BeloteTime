package fr.belotime.view.activity;

import fr.belotime.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class AboutActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about);

		findViewById(R.id.activity_about_text_contact_github)
				.setOnClickListener(this);
		findViewById(R.id.activity_about_text_contact_mail).setOnClickListener(
				this);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
	}


	@Override
	public void onClick(View v) {
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
	}

}
