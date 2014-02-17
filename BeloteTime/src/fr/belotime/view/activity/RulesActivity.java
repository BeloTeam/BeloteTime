package fr.belotime.view.activity;

import fr.belotime.R;
import fr.belotime.view.utils.MakeFont;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class RulesActivity extends Activity implements OnClickListener,
		OnTouchListener {
	private TextView activity_rules_text_rules;
	private TextView activity_rules_text_atout;
	private TextView activity_rules_text_non_atout;
	private ViewSwitcher activity_rules_view_switcher;
	private Animation slide_in_left, slide_out_right, slide_in_right,
			slide_out_left;
	private TextView activity_rules_text_list_rules;
	private TextView activity_rules_text_list_order;
	private boolean list_rules_selected = true;
	private TextView activity_rules_title;
	private final GestureDetector gestureDetector = new GestureDetector(
			new GestureListener());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_rules);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		initAttributs();
		initFont();
		initRulesText();
		initListener();
		initAnimationRules();

	}

	private void initListener() {
		activity_rules_text_list_rules.setOnClickListener(this);
		activity_rules_text_list_order.setOnClickListener(this);

		activity_rules_text_rules.setOnTouchListener(this);
		// findViewById(R.id.activity_rules_layout_atout).setOnTouchListener(this);
		activity_rules_text_atout.setOnTouchListener(this);
		activity_rules_text_non_atout.setOnTouchListener(this);
	}

	private void initAnimationRules() {
		slide_in_left = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left);

		slide_out_right = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right);
		slide_out_left = AnimationUtils.loadAnimation(this,
				R.anim.slide_out_left);
		slide_in_right = AnimationUtils.loadAnimation(this,
				R.anim.slide_in_right);

		activity_rules_view_switcher.setInAnimation(slide_in_left);
		activity_rules_view_switcher.setOutAnimation(slide_out_right);
	}

	private void initRulesText() {
		activity_rules_text_atout.setText(Html
				.fromHtml(getString(R.string.activity_rules_text_atout)));
		activity_rules_text_non_atout.setText(Html
				.fromHtml(getString(R.string.activity_rules_text_non_atout)));
		activity_rules_text_rules.setText(Html
				.fromHtml(getString(R.string.activity_rules_text_rules)));
	}

	private void initFont() {
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/ElegantIcons.ttf");
		Typeface roboto_font = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto.ttf");

		MakeFont.makeFont(activity_rules_text_rules, roboto_font);
		MakeFont.makeFont(activity_rules_text_atout, roboto_font);
		MakeFont.makeFont(activity_rules_text_non_atout, roboto_font);
		MakeFont.makeFont(activity_rules_title, roboto_font);
		MakeFont.makeFont(activity_rules_text_list_order, roboto_font);
		MakeFont.makeFont(activity_rules_text_list_rules, roboto_font);
		MakeFont.makeFont(
				(TextView) findViewById(R.id.activity_rules_icon_rules),
				"#1f8dd6", tf);
	}

	private void initAttributs() {
		activity_rules_text_rules = (TextView) findViewById(R.id.activity_rules_text_rules);
		activity_rules_text_atout = (TextView) findViewById(R.id.activity_rules_text_atout);
		activity_rules_text_list_rules = (TextView) findViewById(R.id.activity_rules_text_list_rules);
		activity_rules_text_list_order = (TextView) findViewById(R.id.activity_rules_text_list_order);
		activity_rules_text_non_atout = (TextView) findViewById(R.id.activity_rules_text_non_atout);
		activity_rules_view_switcher = (ViewSwitcher) findViewById(R.id.activity_rules_viewSwitcher);
		activity_rules_title = (TextView) findViewById(R.id.activity_rules_title);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// Toast.makeText(this,
		// v.getId()+" "+R.id.activity_rules_text_list_rules,
		// Toast.LENGTH_SHORT).show();
		switch (v.getId()) {
		case R.id.activity_rules_text_list_rules:
			switchRulesLeft();
			break;
		case R.id.activity_rules_text_list_order:
			switchRulesRight();
			break;
		}
	}

	public void switchRulesLeft() {
		if (!list_rules_selected) {
			list_rules_selected = true;
			this.activity_rules_text_list_rules.setBackgroundColor(Color
					.parseColor("#1f8dd6"));
			this.activity_rules_text_list_rules.setTextColor(Color.WHITE);
			this.activity_rules_text_list_order
					.setBackgroundResource(R.drawable.activity_rules_font_list_view);
			this.activity_rules_text_list_order.setTextColor(Color.BLACK);
			this.activity_rules_view_switcher.showNext();
			activity_rules_view_switcher.setInAnimation(slide_in_left);
			activity_rules_view_switcher.setOutAnimation(slide_out_right);
		}
	}

	private void switchRulesRight() {
		if (list_rules_selected) {
			list_rules_selected = false;
			this.activity_rules_text_list_order.setBackgroundColor(Color
					.parseColor("#1f8dd6"));
			this.activity_rules_text_list_order.setTextColor(Color.WHITE);
			this.activity_rules_text_list_rules
					.setBackgroundResource(R.drawable.activity_rules_font_list_view);
			this.activity_rules_text_list_rules.setTextColor(Color.BLACK);
			this.activity_rules_view_switcher.showNext();
			activity_rules_view_switcher.setInAnimation(slide_in_right);
			activity_rules_view_switcher.setOutAnimation(slide_out_left);
		}
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		return gestureDetector.onTouchEvent(arg1);
	}

	private final class GestureListener extends SimpleOnGestureListener {

		private static final int SWIPE_THRESHOLD = 50;
		private static final int SWIPE_VELOCITY_THRESHOLD = 50;

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			boolean result = false;
			try {
				float diffY = e2.getY() - e1.getY();
				float diffX = e2.getX() - e1.getX();
				if (Math.abs(diffX) > Math.abs(diffY)) {
					if (Math.abs(diffX) > SWIPE_THRESHOLD
							&& Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffX > 0) {
							onSwipeRight();
						} else {
							onSwipeLeft();
						}
					}
				} else {
					if (Math.abs(diffY) > SWIPE_THRESHOLD
							&& Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffY > 0) {
							onSwipeBottom();
						} else {
							onSwipeTop();
						}
					}
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return result;
		}
	}

	public void onSwipeRight() {
		switchRulesRight();
	}

	public void onSwipeLeft() {
		switchRulesLeft();
	}

	public void onSwipeTop() {
	}

	public void onSwipeBottom() {
	}
}
