package org.pgist.tests.packages.knapsack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.pgist.packages.knapsack.KSChoices;
import org.pgist.packages.knapsack.KSEngine;
import org.pgist.packages.knapsack.KSItem;

/**
 * Used to test the KSEngine
 * 
 */
public class TestKSEngine {

	@Before
	public void setUp() {
	}
	
	/**
	 * Test adding a reference to the funding service
	 */
	@Test
	public void testDataKSEngine() throws Exception {
		
		int limit = 200;
		
		ArrayList<KSChoices> choices = new ArrayList<KSChoices>();
		KSChoices tempChoice;
		
		tempChoice = new KSChoices();
		tempChoice.getChoices().add(createKSItem(3,37));
		tempChoice.getChoices().add(createKSItem(0,25));
		tempChoice.getChoices().add(createKSItem(7,28));
		tempChoice.getChoices().add(createKSItem(0,28));
		tempChoice.getChoices().add(createKSItem(4,45));
		choices.add(tempChoice);

		tempChoice = new KSChoices();
		tempChoice.getChoices().add(createKSItem(1,21));
		tempChoice.getChoices().add(createKSItem(3,28));
		tempChoice.getChoices().add(createKSItem(6,43));
		tempChoice.getChoices().add(createKSItem(8,49));
		tempChoice.getChoices().add(createKSItem(3,50));
		choices.add(tempChoice);

		tempChoice = new KSChoices();
		tempChoice.getChoices().add(createKSItem(10,29));
		tempChoice.getChoices().add(createKSItem(6,25));
		tempChoice.getChoices().add(createKSItem(4,33));
		tempChoice.getChoices().add(createKSItem(5,44));
		tempChoice.getChoices().add(createKSItem(0,24));
		choices.add(tempChoice);

		tempChoice = new KSChoices();
		tempChoice.getChoices().add(createKSItem(0,41));
		tempChoice.getChoices().add(createKSItem(5,42));
		tempChoice.getChoices().add(createKSItem(2,39));
		tempChoice.getChoices().add(createKSItem(10,34));
		tempChoice.getChoices().add(createKSItem(4,31));
		choices.add(tempChoice);

		tempChoice = new KSChoices();
		tempChoice.getChoices().add(createKSItem(3,43));
		tempChoice.getChoices().add(createKSItem(6,32));
		tempChoice.getChoices().add(createKSItem(10,33));
		tempChoice.getChoices().add(createKSItem(7,27));
		tempChoice.getChoices().add(createKSItem(3,42));
		choices.add(tempChoice);

		tempChoice = new KSChoices();
		tempChoice.getChoices().add(createKSItem(8,40));
		tempChoice.getChoices().add(createKSItem(9,25));
		tempChoice.getChoices().add(createKSItem(10,27));
		tempChoice.getChoices().add(createKSItem(4,31));
		tempChoice.getChoices().add(createKSItem(2,42));
		choices.add(tempChoice);
		
		long startTime = System.currentTimeMillis();
		Collection<KSItem> items = KSEngine.mcknap(choices, limit);
		long endTime = System.currentTimeMillis();
		
		displayResults(items, limit, startTime, endTime);
	}	
	
	private MockKSItem createKSItem(double profit, double cost) {
		MockKSItem tempItem;
		tempItem = new MockKSItem();
		tempItem.setName("p=" + profit + " c=" + cost);
		tempItem.setProfit(profit);
		tempItem.setCost(cost);
		return tempItem;
	}
	
	/**
	 * Test adding a reference to the funding service
	 */
	
	public void xtestKSEngine() throws Exception {
		
		int choiceTotal = 8;
		int itemTotal = 3;
		int limit = 20;
		
		ArrayList<KSChoices> choices = new ArrayList<KSChoices>();
		KSChoices tempChoice;
		MockKSItem tempItem;
		for(int i = 0; i < choiceTotal; i++) {
			tempChoice = new KSChoices();
			for(int j = 0; j < itemTotal; j++) {
				tempItem = new MockKSItem();
				tempItem.setName("Class [" + i + "] Item [" + j + "]");
				tempItem.setCost(calcCost(i,j));
				tempItem.setProfit(calcProfit(i,j));
System.out.println("Created an Item (" + i + "," + j + ") p = " + tempItem.getProfit() + " c = " + tempItem.getCost());				
				tempChoice.getChoices().add(tempItem);
			}
			choices.add(tempChoice);
		}
		
		long startTime = System.currentTimeMillis();
		Collection<KSItem> items = KSEngine.mcknap(choices, limit);
		long endTime = System.currentTimeMillis();
		
		displayResults(items, limit, startTime, endTime);
	}
	
	public void displayResults(Collection<KSItem> items, float limit, long startTime, long endTime) {
		Iterator<KSItem> i = items.iterator();
		KSItem tempItem;
		System.out.println("Results.....");
		double tProfit = 0;
		double tCost = 0;
		while(i.hasNext()) {
			tempItem = i.next();
			System.out.println(tempItem);
			tProfit = tProfit + tempItem.getProfit();
			tCost = tCost + tempItem.getCost();
		}
		System.out.println("Limit = [" + limit + "] Total Cost = [" + tCost + "] Total Profit= [" + tProfit + "] Ran in [" + (endTime - startTime) + "]ms");
	}
	
	public float calcCost(int i, int j) {
		i = i + 1;
		return (float)i*(float)j + 1;
	}
	public float calcProfit(int i, int j) {
		return (float)i*(float)j + 1;
	}
}

