package org.pgist.projects;

/**
 * Thrown when an unknown objective is requested
 * 
 * @author Matt Paulin
 */
public class UnknownObjectiveException extends Exception {

	public UnknownObjectiveException(String msg) {
		super(msg);
	}	
}
