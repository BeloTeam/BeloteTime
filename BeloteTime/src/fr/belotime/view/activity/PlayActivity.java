package fr.belotime.view.activity;

import fr.belotime.R;
import fr.belotime.R.id;
import fr.belotime.noyau.classesMetier.PositionEnum;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

enum Etat{
	E1erTour,
	E2ndTour,
	EAttentePrendre,
	EPliEnCours,
	EFinPli,
	EAttenteJouerCarte,
	EMancheTerminee
}

public class PlayActivity extends Activity implements OnClickListener {
	ImageView asCoeur,neuf_carreau,roi_coeur,dame_trefle; 
	ImageView asCoeur2,neuf_carreau2,roi_coeur2,dame_trefle2,asCoeur3;
	ImageView carteNord,carteEst,carteSud,carteOuest;
	RelativeLayout ecran;
	Etat etat;
	PositionEnum joueurCourant;
	Toast toast;
	TextView jSud,jEst,jOuest,jNord;
	Button prendre,passer;
	int nbCartesDansPli, nbPlisDansManche;
	
	private final static int ID_DIALOG_TOUR1 = 0;
	private final static int ID_DIALOG_TOUR2 = 1;
    
	private OnClickListener cPrendre = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			switch (etat) {
			case EAttentePrendre:
				prendre.setVisibility(8); // == gone
				passer.setVisibility(View.GONE);
				joueurCourant = changerJoueur();//joueur après le donneur normalement
				distribuerFinPaquet();
				debuterPli();
				afficherJoueurCourant();
				etat=Etat.EPliEnCours;
				break;
			default://Interdit
			}		
		}	
	};
	
	private OnClickListener cPasser = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			switch (etat) {
			case EAttentePrendre:
				prendre.setVisibility(8);
				passer.setVisibility(8);
				passer();		
				etat=Etat.E1erTour;
				break;
			default://Interdit
			}		
		}
	};
	
	private OnClickListener cChangerJoueur = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			switch (etat) {
			case E1erTour:		
				joueurCourant = changerJoueur();
				afficherJoueurCourant();
				if(joueurCourant != PositionEnum.Sud){
					passer();
					etat=Etat.E1erTour;
				} 
				// après le joueur Est on passe à l'utilisateur
				else{
					// joueurCourant == PositionEnum.Est, il faut passer
					prendre.setVisibility(0);
					passer.setVisibility(0);
					etat=Etat.EAttentePrendre;											
				}				
				break;
			case EAttentePrendre:	//Interdit				
				break;
			case EPliEnCours:	
					//jouer une carte
					if(joueurCourant != PositionEnum.Sud){
						jouerCarte(R.drawable.dame_pique);
						if(nbCartesDansPli<4){
							joueurCourant = changerJoueur();
							afficherJoueurCourant();
							if (joueurCourant == PositionEnum.Sud){
								etat=Etat.EAttenteJouerCarte;
							} else {
								etat=Etat.EPliEnCours;
							}
						}else{
							//fin du pli
							etat=Etat.EFinPli;
						}
					}
				break;
			case EFinPli:
				terminerPli();
				if(nbPlisDansManche<8) {
					joueurCourant = changerJoueur();
					afficherJoueurCourant();
					etat=Etat.EPliEnCours;
				}else{
					//manche terminée
					finDeManche();
					etat=Etat.EMancheTerminee;
				}
			default://...
			}			
		}
	};

	private OnClickListener cCarte = new View.OnClickListener() {		
		@Override
		public void onClick(View v) {
			int carteId = v.getId();
			ImageView carteSelectionnee = (ImageView) findViewById(carteId);
			Drawable img = carteSelectionnee.getDrawable();
			switch (etat) {
			case EAttenteJouerCarte:
				nbCartesDansPli++;
				carteSelectionnee.setVisibility(View.GONE);
				carteSud.setVisibility(View.VISIBLE);
				//carteSud.setId(carteId);
				carteSud.setImageDrawable(img);
				if(nbCartesDansPli<4){
					joueurCourant = changerJoueur();
					afficherJoueurCourant();
					etat=Etat.EPliEnCours;
				}else{
					//fin du pli
					etat=Etat.EFinPli;
				}
				break;
			
			default://interdit
			}			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_play);	
		
		etat = Etat.E1erTour;
		joueurCourant = PositionEnum.Ouest;
		//ImageView donneur = (ImageView)findViewById(R.id.donneur);
		jSud = (TextView)findViewById(R.id.jSud);
		jEst = (TextView)findViewById(R.id.jEst);
		jOuest = (TextView)findViewById(R.id.jOuest);
		jNord = (TextView)findViewById(R.id.jNord);
		
		prendre = (Button)findViewById(R.id.prendre);
		passer = (Button)findViewById(R.id.passer);
		
		
		//donneur.setVisibility(0); // 0 correspond à visible
		
		//android:layout_alignTop="@+id/jSud"
        //android:layout_toRightOf="@+id/jSud"
		//toast.setText("Passe");

		asCoeur = (ImageView) findViewById(R.id.as_coeur);
		neuf_carreau = (ImageView) findViewById(R.id.neuf_carreau);
		roi_coeur = (ImageView) findViewById(R.id.roi_coeur);
		dame_trefle = (ImageView) findViewById(R.id.dame_trefle);		
		asCoeur2 = (ImageView) findViewById(R.id.as_coeur2);
		neuf_carreau2 = (ImageView) findViewById(R.id.neuf_carreau2);
		roi_coeur2 = (ImageView) findViewById(R.id.roi_coeur2);
		dame_trefle2 = (ImageView) findViewById(R.id.dame_trefle2);
		
		asCoeur3 = (ImageView) findViewById(R.id.as_coeur3);
		asCoeur3.setVisibility(View.VISIBLE);

		carteNord = (ImageView)findViewById(R.id.carteNord);
		carteSud = (ImageView)findViewById(R.id.carteSud);
		carteEst = (ImageView)findViewById(R.id.carteEst);
		carteOuest = (ImageView)findViewById(R.id.carteOuest);
		
		ecran = (RelativeLayout)findViewById(R.id.Plateau);
		prendre.setOnClickListener(cPrendre);
		passer.setOnClickListener(cPasser);
		ecran.setOnClickListener(cChangerJoueur);
		
		asCoeur.setOnClickListener(cCarte);
		neuf_carreau.setOnClickListener(cCarte);
		roi_coeur.setOnClickListener(cCarte);
		dame_trefle.setOnClickListener(cCarte);
		asCoeur2.setOnClickListener(cCarte);
		neuf_carreau2.setOnClickListener(cCarte);
		roi_coeur2.setOnClickListener(cCarte);
		dame_trefle2.setOnClickListener(cCarte);
		
		nbCartesDansPli = 0;
		nbPlisDansManche = 0;
		afficherJoueurCourant();
		passer();
	}



	private void afficherJoueurCourant() {
		switch (joueurCourant){
		case Nord:
			jNord.setTextColor(Color.GREEN);
			jEst.setTextColor(Color.BLACK);
			jSud.setTextColor(Color.BLACK);
			jOuest.setTextColor(Color.BLACK);
			break;
		case Est:
			jNord.setTextColor(Color.BLACK);
			jEst.setTextColor(Color.GREEN);
			jSud.setTextColor(Color.BLACK);
			jOuest.setTextColor(Color.BLACK);
			break;
		case Sud:
			jNord.setTextColor(Color.BLACK);
			jEst.setTextColor(Color.BLACK);
			jSud.setTextColor(Color.GREEN);
			jOuest.setTextColor(Color.BLACK);
			break;
		case Ouest:
			jNord.setTextColor(Color.BLACK);
			jEst.setTextColor(Color.BLACK);
			jSud.setTextColor(Color.BLACK);
			jOuest.setTextColor(Color.GREEN);
			break;
		}
	}


	/**
	 * affiche que le joueur passe et change le joueur en cours
	 */
	private void passer() {
		// affiche un toast pour le moment
		toast = Toast.makeText(getApplicationContext(), joueurCourant+" passe", Toast.LENGTH_SHORT);
		toast.show();
	}



	public PositionEnum changerJoueur(){
		PositionEnum aDroite = null;
		switch(joueurCourant){
		case Nord:
			aDroite = PositionEnum.Est;
			break;
		case Est:
			aDroite = PositionEnum.Sud;
			break;
		case Sud:
			aDroite = PositionEnum.Ouest;
			break;
		case Ouest:
			aDroite = PositionEnum.Nord;
			break;
		}
		return aDroite;
	}

	private void distribuerFinPaquet() {
		neuf_carreau2.setVisibility(0);
		roi_coeur2.setVisibility(0);
		dame_trefle2.setVisibility(0);
		asCoeur3.setVisibility(8); 
	}


	private void debuterPli() {
		carteNord.setVisibility(View.INVISIBLE);
		carteEst.setVisibility(View.INVISIBLE);
		carteSud.setVisibility(View.INVISIBLE);
		carteOuest.setVisibility(View.INVISIBLE);
	}	
	
	private void terminerPli() {
		carteNord.setVisibility(View.INVISIBLE);
		carteEst.setVisibility(View.INVISIBLE);
		carteSud.setVisibility(View.INVISIBLE);
		carteOuest.setVisibility(View.INVISIBLE);
		nbCartesDansPli=0;
		joueurCourant=PositionEnum.Sud;
		nbPlisDansManche++;
	}
	
	private void jouerCarte(int resId){
		nbCartesDansPli++;
		switch(joueurCourant){
		case Nord :
			carteNord.setVisibility(View.VISIBLE);
			carteNord.setImageResource(resId);
			break;
		case Est :
			carteEst.setVisibility(View.VISIBLE);
			carteEst.setImageResource(resId);
			break;
		case Ouest : 
			carteOuest.setVisibility(View.VISIBLE);
			carteOuest.setImageResource(resId);
			break;
		default:
			// ...
		}
	}
	
	private void finDeManche(){
		toast = Toast.makeText(getApplicationContext(), "Manche terminée. Nord/Sud marquent X points et Est/Ouest Y points.", Toast.LENGTH_LONG);
		toast.show();
	}
	@Override
	public void onClick(View v) {
		/*
		switch (etat) {
		case E1erTour:
			// si c'est à un bot de choisir 
			if(joueurCourant != PositionEnum.Sud){
				passer();
				etat=Etat.E1erTour;
			} else{
				prendre.setVisibility(0);
				passer.setVisibility(0);
				etat=Etat.EAttentePrendre;			
				etat=Etat.E1erTour;			
			}
			break;
		case EAttentePrendre:	
			
			break;
		default:

		}	*/	
	}
	
}
