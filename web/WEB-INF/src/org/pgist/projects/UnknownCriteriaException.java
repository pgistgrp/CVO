package org.pgist.projects;

/**
 * Thrown when an unknown criteria is requested
 * 
 * @author Matt Paulin
 */
public class UnknownCriteriaException extends Exception {

	public UnknownCriteriaException(String msg) {
		super(msg);
	}	
}
