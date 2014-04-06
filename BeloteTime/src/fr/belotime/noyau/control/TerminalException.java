package fr.belotime.noyau.control;

public class TerminalException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4195095540981920839L;
	Exception ex;

	public TerminalException(Exception e) {
		ex = e;
	}
}
