package org.pgist.tests.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.pgist.packages.cluster.AbstractClusterer;
import org.pgist.packages.cluster.ItemCluster;
import org.pgist.packages.cluster.PAMClusterer;

/**
 * Used to test the KSEngine
 * 
 */
public class TestClustering {

	@Before
	public void setUp() {
	}
	
	/**
	 * Test that the clustering works
	 */
	@Test
	public void testClustering() {
		ArrayList<LineItem> items = new ArrayList<LineItem>();
		items.add(new LineItem(2));
		items.add(new LineItem(3));
		items.add(new LineItem(4));
		items.add(new LineItem(20));
		items.add(new LineItem(21));
		items.add(new LineItem(22));
		items.add(new LineItem(41));
		items.add(new LineItem(42));
		items.add(new LineItem(43));

//		Random rand = new Random(System.currentTimeMillis()); 
//		for(int i = 0; i < 300; i++) {
//			items.add(new LineItem(100* rand.nextFloat()));
//		}
		
		int numClusters = 3;
		

//		AbstractClusterer clusterer = new KMedoidClusterer(items);
		PAMClusterer clusterer = new PAMClusterer(items);
		clusterer.setNumClusters(numClusters);
		long start;
		for(int i = 0; i < 10; i++) {
			start = System.currentTimeMillis();
			clusterer.step();			
			System.out.println("Step " + i + " took " + (System.currentTimeMillis() - start) + " ms: overall compactness " + clusterer.getOverallCompactness());
		}
		printResults(clusterer.getClusters());
		
//		clusterer.start();
//		clusterer.go(2000);
//		
//		//How do you know when its done?
//		System.out.println("Thinking");
//		while(!clusterer.isDone()) {
//			try {
//				Thread.currentThread().sleep(50);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("num steps " + clusterer.getSteps() + " togo " + clusterer.getToGo() );
//		}
		
	}
	public void printResults(Collection results) {
		//How do you get the results.
		Iterator i = results.iterator();
		System.out.println("Got the results");
		ItemCluster temp;
		while(i.hasNext()) {
			temp = (ItemCluster)i.next();
			System.out.println(temp.getRepresentative());
			Iterator iItems = temp.getItems().iterator();
			while(iItems.hasNext()) {
				System.out.println("Member Item:" + iItems.next());
			}		
		}		
	}
}

