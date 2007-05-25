package org.pgist.tests.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pgist.packages.cluster.Item;
import org.pgist.packages.cluster.ItemCluster;
import org.pgist.packages.cluster.PAMClusterer;
import org.pgist.packages.cluster.PackageItem;

/**
 * Used to test the KSEngine
 * 
 */
public class TestClustering {

	@Before
	public void setUp() {
	}
	
	/**
	 * Test clustering binary items
	 */
	@Test
	@Ignore
	public void testClusteringBinary() {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new PackageItem(1l, createChoices(0,1,1,0,1)));
		items.add(new PackageItem(2l, createChoices(0,0,0,1,1)));
		items.add(new PackageItem(3l, createChoices(0,1,1,0,0)));
		items.add(new PackageItem(4l, createChoices(1,0,0,1,0)));
		items.add(new PackageItem(5l, createChoices(1,1,0,1,0)));
		items.add(new PackageItem(6l, createChoices(1,1,1,0,0)));

		printPAMCluster(PAMClusterer.calcClusters(2, items));		
		
//		PAMClusterer clusterer = new PAMClusterer(items);
//		clusterer.setNumClusters(2);
//		long start;
//		for(int i = 0; i < 1; i++) {
//			start = System.currentTimeMillis();
//			clusterer.step();			
//			System.out.println("Step " + i + " took " + (System.currentTimeMillis() - start) + " ms: overall compactness " + clusterer.getOverallCompactness());
//		}
//		printResults(clusterer.getClusters());
	
	}
	private List<Boolean> createChoices(int i1, int i2, int i3, int i4, int i5) {
		List<Boolean> results = new ArrayList<Boolean>();
		if(i1 == 1) {
			results.add(true);
		} else {
			results.add(false);			
		}
		if(i2 == 1) {
			results.add(true);
		} else {
			results.add(false);			
		}
		if(i3 == 1) {
			results.add(true);
		} else {
			results.add(false);			
		}
		if(i4 == 1) {
			results.add(true);
		} else {
			results.add(false);			
		}
		if(i5 == 1) {
			results.add(true);
		} else {
			results.add(false);			
		}
		return results;
	}
	
	public void printPAMCluster(Collection<ItemCluster> cluster) {
		Iterator<ItemCluster> iPCl = cluster.iterator();
		ItemCluster tempPAM;
		while(iPCl.hasNext()) {
			tempPAM = iPCl.next();
			System.out.println("Found Medoid " + tempPAM.getMediod());
			printCollection("Items....", tempPAM.getItems());
		}
	}
	public static void printCollection(String name, Collection<Item> items) {
		Iterator<Item> iItem = items.iterator();
		System.out.println("In collection " + name);
		while(iItem.hasNext()) {
			System.out.println("Found items " + iItem.next());
		}		
	}
	/**
	 * Test that the clustering works
	 */
	@Test
	@Ignore	
	public void testClustering2DPts() {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new PointItem( 1, 8));
		items.add(new PointItem( 2, 6));
		items.add(new PointItem( 3, 7));
		items.add(new PointItem( 12, 2));
		items.add(new PointItem( 11, 1));
		items.add(new PointItem( 13, 3));
		items.add(new PointItem( 15, 4));
		
		int numClusters = 2;
		
		printPAMCluster(PAMClusterer.calcClusters(numClusters, items));		

//		AbstractClusterer clusterer = new KMedoidClusterer(items);
//		PAMClusterer clusterer = new PAMClusterer(items);
//		clusterer.setNumClusters(numClusters);
//		long start;
//		for(int i = 0; i < 1; i++) {
//			start = System.currentTimeMillis();
//			clusterer.step();			
//			System.out.println("Step " + i + " took " + (System.currentTimeMillis() - start) + " ms: overall compactness " + clusterer.getOverallCompactness());
//		}
//		printResults(clusterer.getClusters());
		
	}
	
	/**
	 * Test that the clustering works
	 */
	@Test	
	public void testClustering() {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new LineItem(2));
//		items.add(new LineItem(3));
//		items.add(new LineItem(4));
//		items.add(new LineItem(20));
//		items.add(new LineItem(21));
//		items.add(new LineItem(22));
//		items.add(new LineItem(41));
//		items.add(new LineItem(42));
//		items.add(new LineItem(43));

//		Random rand = new Random(System.currentTimeMillis()); 
//		for(int i = 0; i < 300; i++) {
//			items.add(new LineItem(100* rand.nextFloat()));
//		}
		
		int numClusters = 0;
		
		printPAMCluster(PAMClusterer.calcClusters(numClusters, items));
		
		
//		AbstractClusterer clusterer = new KMedoidClusterer(items);
//		PAMClusterer clusterer = new PAMClusterer(items);
//		clusterer.setNumClusters(numClusters);
//		long start;
//		for(int i = 0; i < 1; i++) {
//			start = System.currentTimeMillis();
//			clusterer.step();			
//			System.out.println("Step " + i + " took " + (System.currentTimeMillis() - start) + " ms: overall compactness " + clusterer.getOverallCompactness());
//		}
//		printResults(clusterer.getClusters());
		
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
}

