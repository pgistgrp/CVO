package org.pgist.tests.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
	/**
	 * 
	 */
	/**
	 * 
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
		
		int numClusters = 3;
		

//		AbstractClusterer clusterer = new KMedoidClusterer(items);
		AbstractClusterer clusterer = new PAMClusterer(items);
		clusterer.setNumClusters(numClusters);
		clusterer.start();
		clusterer.go(2000);
		
		//How do you know when its done?
		System.out.println("Thinking");
		while(!clusterer.isDone()) {
			try {
				Thread.currentThread().sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("num steps " + clusterer.getSteps() + " togo " + clusterer.getToGo() );
		}
		
		//How do you get the results.
		Collection results = clusterer.getClusters();
		Iterator i = results.iterator();
		System.out.println("Got the results");
		ItemCluster temp;
		while(i.hasNext()) {
			temp = (ItemCluster)i.next();
			System.out.println(temp.getRepresentative());
		}
	}
}

