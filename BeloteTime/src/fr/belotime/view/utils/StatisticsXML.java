package fr.belotime.view.utils;

import java.io.File;

import java.util.List;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import android.widget.Toast;



public class StatisticsXML {
	
	public StatisticsXML() {
		// TODO Auto-generated constructor stub
	}
	
	public static List<String> getStatistics(){
		
		Element racine = new Element("statistics");
		Document document = new Document();
		//On crée une instance de SAXBuilder
	    SAXBuilder sxb = new SAXBuilder();
	    try
	    {
	       //On crée un nouveau document JDOM avec en argument le fichier XML
	       //Le parsing est terminé ;)
	       document = sxb.build(new File("save/statistics.xml"));
	    }
	    catch(Exception e){e.printStackTrace();}

	    //On initialise un nouvel élément racine avec l'élément racine du document.
	    racine = document.getRootElement();
	    String path = (String) System.getProperties().get("user.dir");
	    
			
		return null;
	}
	
	
	
	
	public static void save(List<String> list_stat){
		Element racine = new Element("Statistics");
		Document document = new Document(racine);
		String directory = "save";
		
		String parties_jouees = list_stat.get(0);
		String parties_gagnees_pourcent = list_stat.get(1);
		String temps_heure = list_stat.get(2);
		String temps_minute = list_stat.get(3);
		String capots = list_stat.get(4);
		String dedans = list_stat.get(5);
		String belotes = list_stat.get(6);
		

	}
	


}
