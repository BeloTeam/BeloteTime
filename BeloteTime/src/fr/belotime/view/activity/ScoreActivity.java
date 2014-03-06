package fr.belotime.view.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import fr.belotime.R;
import fr.belotime.view.utils.MakeFont;
import android.app.Activity;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_score);
	}

	

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // TODO
	 * Auto-generated method stub MenuInflater inflater = getMenuInflater();
	 * inflater.inflate(R.menu.menu_score, menu);
	 * 
	 * return super.onCreateOptionsMenu(menu); }
	 */

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(R.anim.push_down, R.anim.push_up);
	}

}
