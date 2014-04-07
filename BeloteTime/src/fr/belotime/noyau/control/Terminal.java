package fr.belotime.noyau.control;
/**
 *  @class Terminal
 *  @author lacertus, Nathan
 *  @resume permet l'affichage ou l'ecriture sur le terminal
 * 
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Terminal {

	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static String lireString() // Lire un String
	{
		String tmp = "";
		//char C = '\0';
		try {
			tmp = in.readLine();
		} catch (IOException e) {
			exceptionHandler(e);
		}
		return tmp;
	} // fin de lireString()

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static int lireInt() // Lire un entier
	{
		int x = 0;
		try {
			x = Integer.parseInt(lireString());
		} catch (NumberFormatException e) {
			Terminal.ecrireStringln("Erreur de saisie, recommencez : ");
			x = lireInt();
		}
		return x;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static boolean lireBoolean() // Lire un entier
	{
		boolean b = true;
		try {
			b = Boolean.valueOf(lireString()).booleanValue();
		} catch (NumberFormatException e) {
			exceptionHandler(e);
		}
		return b;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static double lireDouble() // Lire un double
	{
		double x = 0.0;
		try {
			x = Double.valueOf(lireString()).doubleValue();
		} catch (NumberFormatException e) {
			exceptionHandler(e);
		}
		return x;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static char lireChar() // Lire un caractere
	{
		String tmp = lireString();
		if (tmp.length() == 0)
			return '\n';
		else {
			return tmp.charAt(0);
		}
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void ecrireString(String s) { // Afficher un String
		try {
			System.out.print(s);
		} catch (Exception ex) {
			exceptionHandler(ex);
		}
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void ecrireStringln(String s) // Afficher un String
	{
		ecrireString(s);
		sautDeLigne();
	} // fin de ecrireStringln()

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void ecrireInt(int i) // Afficher un entier
	{
		ecrireString("" + i);
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void ecrireIntln(int i) // Afficher un entier
	{
		ecrireString("" + i);
		sautDeLigne();
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void ecrireBoolean(boolean b) {
		ecrireString("" + b);
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void ecrireBooleanln(boolean b) {
		ecrireString("" + b);
		sautDeLigne();
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void ecrireDouble(double d) // Afficher un double
	{
		ecrireString("" + d);
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void ecrireDoubleln(double d) // Afficher un double
	{
		ecrireDouble(d);
		sautDeLigne();
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void ecrireChar(char c) // Afficher un caractere
	{
		ecrireString("" + c);
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void ecrireCharln(char c) // Afficher un caractere
	{
		ecrireChar(c);
		sautDeLigne();
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void sautDeLigne() {
		try {
			System.out.println();
		} catch (Exception ex) {
			exceptionHandler(ex);
		}
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	protected static void exceptionHandler(Exception ex) {
		TerminalException err = new TerminalException(ex);
		throw err;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static void ecrireException(Throwable ex) {
		ecrireString(ex.toString());
		ex.printStackTrace(System.err);
	}
}


