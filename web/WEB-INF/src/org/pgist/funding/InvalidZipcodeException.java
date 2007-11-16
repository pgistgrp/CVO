package org.pgist.funding;

/**
 * Thrown if a bad zipcode is provided
 * 
 * @author Matt Paulin
 */
public class InvalidZipcodeException extends Exception {

	public InvalidZipcodeException(String str) {
		super(str);
	}	
}
