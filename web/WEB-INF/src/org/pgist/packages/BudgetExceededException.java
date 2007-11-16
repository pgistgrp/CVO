package org.pgist.packages;

/**
 * Exception used to tell the user that the budget has been exceeded
 * 
 * @author Matt Paulin
 */
public class BudgetExceededException extends Exception {

	public BudgetExceededException(String str) {
		super(str);
	}
}
