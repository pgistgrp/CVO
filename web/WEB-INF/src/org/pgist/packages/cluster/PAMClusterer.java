package org.pgist.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Clusteres a collection of items into the specified number of clusters using the PAM algorithm
 * 
 * @author paulin
 */
public class PAMClusterer {

	
	/**
	 * This method will return a collection of items that have been clustered together
	 * 
	 * @param numClusters	The number of clusters you would like returned
	 * @param items			The items to turn into clusters
	 * @return	A collection of item clusters identifying what items go into what cluster
	 */
	public static Collection<ItemCluster> calcClusters(int numClusters, Collection<Item> items) {
				
		//Create two collections one will store the medoids, the other will store all the other items
		Collection<Item> medoids = new ArrayList<Item>();
		Collection<Item> remaining = new ArrayList<Item>(items);
		
		//BUILD Phase: Separate out the medoids
		getFirstMedoid(medoids, remaining);
		for(int i = 0; i < numClusters-1; i++) {
			getNextMedoid(medoids, remaining);
		}		
		
		//SWAP Phase: in this phase we try seening if we can beat the overall objective by swapping the medoids and the items
		swap(medoids, remaining);
		
		//Create the number of clusters needed
		Collection<ItemCluster> clusters = new ArrayList<ItemCluster>();

		Iterator<Item> iItem = medoids.iterator();
		Item tempItem;
		while(iItem.hasNext()) {
			tempItem = iItem.next();
			ItemCluster tempCluster = new ItemCluster();
			tempCluster.setMediod(tempItem);
			clusters.add(tempCluster);
		}
		
		//Assign all of the items to cluster with the medoid that is closes to it
		assignItems(remaining, clusters);
		
		return clusters;
	}

	/**
	 * Assigns the remaining items to the appropriate cluster
	 * 
	 * @param	remaining	The items that are remainging
	 * @param	clusters	The clusters to put the items in
	 */
	private static void assignItems(Collection<Item> remaining, Collection<ItemCluster> clusters) {
		Iterator<Item> iItem = remaining.iterator();
		Item tempItem;
		ItemCluster favCluster = null;
		ItemCluster tempPAMCl;
		float dist = Float.MAX_VALUE;
		float newDist = Float.MAX_VALUE;
		while(iItem.hasNext()) {
			tempItem = iItem.next();
			dist = Float.MAX_VALUE;
			
			Iterator<ItemCluster> iPAMCL = clusters.iterator();
			while(iPAMCL.hasNext()) {
				tempPAMCl = iPAMCL.next();
				newDist = tempPAMCl.getMediod().distance(tempItem);
				//System.out.println("Found distance " + newDist + " old dist" + dist + " from mediod " + tempPAMCl.getMediod() + " to item " + tempItem);
				if(newDist < dist) {
					dist = newDist;
					favCluster = tempPAMCl;
				}
			}
			
			//Now that you have the favorite, add it
			favCluster.getItems().add(tempItem);
		}		
	}
	
	
	/**
	 * Figures out what the first medoid is, and then moves it from the remaining group to the medoids group
	 * 
	 * @param	medoids	The collection of mediods that the first medoid will end up in (its empty when passed in)
	 * @param	remaining	These are all the items that are remaining in the system 
	 */
	private static void getFirstMedoid(Collection<Item> medoids, Collection<Item> remaining) {
		//Find the item that is the shortest distance from all other items
		Item tempItem = null;
		float compactness = Float.MAX_VALUE;
		float tempComp = 0;
		Iterator<Item> iItem = remaining.iterator();
		Item medoid = null;
		while(iItem.hasNext()) {
			tempItem = iItem.next();
			tempComp = calcItemCompactness(tempItem, remaining);
			if(tempComp < compactness) {
				compactness = tempComp;
				medoid = tempItem;
			}
		}
		
		remaining.remove(medoid);
		medoids.add(medoid);
	}

	/**
	 * Gets the next medoid from the group of remaining items and puts it into the mediod collection
	 * 
	 * @param	medoids		The collection of medoids the new medoid will end up in
	 * @param	remaining	The remaining items
	 */
	private static void getNextMedoid(Collection<Item> medoids, Collection<Item> remaining) {
		//Pick each item as a potential medoid
		Item tempItem = null;
		float compactness = Float.MAX_VALUE;
		float tempComp = 0;
		Iterator<Item> iItem = remaining.iterator();
		Item medoid = null;
		Collection<Item> tempMedoids;
		Collection<Item> tempRemaining;
		while(iItem.hasNext()) {
			//See how close everything is to it. Pretending that it is the medoid 
			tempItem = iItem.next();
			
			tempMedoids = new ArrayList<Item>(medoids);
			tempRemaining = new ArrayList<Item>(remaining);
			tempMedoids.add(tempItem);
			tempRemaining.remove(tempItem);
						
			tempComp = calcOverallCompactness(tempMedoids, tempRemaining);
			if(tempComp < compactness) {
				//The smallest one is the new medoid
				compactness = tempComp;
				medoid = tempItem;
			}
		}
		
		remaining.remove(medoid);
		medoids.add(medoid);
		
	}
		
	/**
	 * This is a greedy algorithm that goes through all the remaining items and trys swaping them with the medoids.  After every 
	 * swap it checks to see if the clusteres are more compact.  If they are then the swap is left alone.  It will keep 
	 * trying to swap things until no swap is done when it iterates through the whole collection
	 * 
	 * @param medoids		The collection of medoids
	 * @param remaining		The remaining items
	 */
	private static void swap(Collection<Item> medoids, Collection<Item> remaining) {
		Iterator<Item> iRemaining;
		Iterator<Item> iMedoids;
		Item tempMedoid;
		Item tempRemaining;
		boolean swapped = true;
		//int i = 0;
		while(swapped) {
			swapped = false;

			ArrayList<Item> tempRemainingMeds = new ArrayList<Item>(remaining);
			ArrayList<Item> tempMedoids = new ArrayList<Item>(medoids);
			
			//long started = System.currentTimeMillis();
			iRemaining = tempRemainingMeds.iterator();
			while(iRemaining.hasNext()) {
				//See how close everything is to it. Pretending that it is the medoid 
				tempRemaining = iRemaining.next();
				
				iMedoids = tempMedoids.iterator();
				while(iMedoids.hasNext()) {
					tempMedoid = iMedoids.next();
					
					swapped = swapped || checkSwap(medoids, tempMedoid, remaining, tempRemaining);
				}
			}			
			//i++;
			//System.out.println("Iteration " + i + " time = " + (System.currentTimeMillis() - started) + "(ms) compactness = " + calcOverallObjective(medoids, remaining));
		}
	}
	
	/**
	 * Tries swapping the items to see if the overall compactness can be improved, if it can then it is swapped permanently
	 * 
	 * @return	true if a swap was performed
	 */
	private static boolean checkSwap(Collection<Item> medoids, Item tempMedoid, Collection<Item> remaining, Item swapmedoid) {
		float overallCompactness = Float.MAX_VALUE;
		float overallCompactness2 = Float.MAX_VALUE;
		float tempComp = 0;

		//System.out.println("Trying to swap medoid [" + tempMedoid + "] with item [" + swapmedoid + "]");
		
		overallCompactness = calcOverallCompactness(medoids,remaining);

		//Find the objective with the mediod in the collection
		Collection<Item> tempMedoids = new ArrayList<Item>(medoids);
		Collection<Item> tempRemaining = new ArrayList<Item>(remaining);
		tempMedoids.add(swapmedoid);
		tempRemaining.add(tempMedoid);
		tempMedoids.remove(tempMedoid);
		tempRemaining.remove(swapmedoid);

		overallCompactness2 = calcOverallCompactness(tempMedoids,tempRemaining);

		//System.out.println("before[" + overallCompactness + "] after [" + overallCompactness2 + "]");
		
		if(overallCompactness2 < overallCompactness) {
			//System.out.println("FOUND A BETTER MEDOID ");
			medoids.remove(tempMedoid);
			remaining.remove(swapmedoid);
			medoids.add(swapmedoid);
			remaining.add(tempMedoid);
			return true;
		}
		return false;
	}
		
	/**
	 * Calculates the overall compactness for the collection of medoids and the remaining points
	 * 
	 * @param	medoids	The medoids for the system
	 * @param	remaining	All of the remaining items in the system
	 */
	private static float calcOverallCompactness(Collection<Item> mediods, Collection<Item> remaining) {
		float total = 0;

		Item tempItem;
		Iterator<Item> iItem = remaining.iterator();
		while(iItem.hasNext()) {
			tempItem = iItem.next();
			
			total = total + calcMedioidCompactness(tempItem, mediods);
		}
		
		return total;
	}
	
	/**
	 * Figures out the compactness of this item against the medoids.  It does this by returning the smallest distance to the medoids
	 * 
	 * @param	item	The item to check against the medoids
	 * @param	medoids	The collection of items that represent the medoids
	 */
	private static float calcMedioidCompactness(Item item, Collection<Item> mediods) {
		float total = Float.MAX_VALUE;
		float tempTotal = 0;
		
		Item tempItem;
		Iterator<Item> iItem = mediods.iterator();
		while(iItem.hasNext()) {
			tempItem = iItem.next();
			tempTotal = item.distance(tempItem);
			if(tempTotal < total) {
				total = tempTotal;
			}
		}
		return total;		
	}
	
	/**
	 * Figures out the compactness by summing the distances from this point to all other points
	 * 
	 * @param	item	The item to check
	 * @param	items	The items to check against
	 */
	private static float calcItemCompactness(Item item, Collection<Item> items) {
		float total = 0;
		
		Item tempItem;
		Iterator<Item> iItem = items.iterator();
		while(iItem.hasNext()) {
			tempItem = iItem.next();
			total = total + item.distance(tempItem);
		}
		return total;
	}
	
	/**
	 * Used in debugging to see what items are in each collection
	 * @param name	The name of the collection to print out
	 * @param items	The items to print out
	 */
	private static void printCollection(String name, Collection<Item> items) {
		Iterator<Item> iItem = items.iterator();
		System.out.println("In collection " + name);
		while(iItem.hasNext()) {
			System.out.println("Found items " + iItem.next());
		}		
	}	
}
