package fr.belotime.view.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

public class MakeFont  {
	
	public static void makeFont(TextView tv, Typeface tf)
	{
		tv.setTypeface(tf);
	}
	
	public static void makeFont(TextView tv, int color, Typeface tf)
	{
		tv.setTypeface(tf);
		tv.setTextColor(color);
	}
	public static void makeFont(TextView tv, String color, Typeface tf)
	{
		tv.setTypeface(tf);
		tv.setTextColor(Color.parseColor(color));
	}

}
