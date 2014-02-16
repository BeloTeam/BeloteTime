package fr.belotime.view.activity;

import java.util.Timer;
import java.util.TimerTask;
import fr.belotime.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;


public class LauncherActivity extends /*List*/Activity implements OnClickListener {

	
	private static final long TIME_LOGO = 2000;
	private TimerTask task;
	private Timer t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_launcher);
		
		View logo_launcher= (View)findViewById(R.id.activity_launcher_view_logo);
		logo_launcher.setOnClickListener(this);
		
		this.t = new Timer();    
		this.task = new TimerTask() {   
		   @Override  
		   public void run() {  
		    runOnUiThread(new Runnable() {  
		  
		     @Override  
		     public void run() {  
		 		goToHomeScreen();
		     }  
		    });  
		   }  
		  };  
		  
		  this.t.scheduleAtFixedRate(task, TIME_LOGO, TIME_LOGO);
	}



	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.activity_launcher_view_logo){
			goToHomeScreen();
		}
		
	}



	private void goToHomeScreen() {
		this.t.cancel();
		Intent intent = new Intent(LauncherActivity.this,HomeActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
	}
	
}
