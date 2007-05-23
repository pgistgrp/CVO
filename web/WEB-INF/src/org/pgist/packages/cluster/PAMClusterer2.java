package org.pgist.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PAMClusterer2 {

	public static Collection<PAMCluster> calcClusters(int numClusters, Collection<Item> items) {
		
		
		//Separate out the medoids, referred to as the "build"
		Collection<Item> medoids = new ArrayList<Item>();
		Collection<Item> remaining = new ArrayList<Item>(items);


		
		getFirstMedoid(medoids, remaining);
		for(int i = 0; i < numClusters-1; i++) {
			getNextMedoid(medoids, remaining);
		}		

		
		//SWAP, in this phase we try seening if we can beat the overall objective by swapping the medoids and the items
		swap(medoids, remaining);
		
		//Create the number of clusters needed
		Collection<PAMCluster> clusters = new ArrayList<PAMCluster>();

		Iterator<Item> iItem = medoids.iterator();
		Item tempItem;
		while(iItem.hasNext()) {
			tempItem = iItem.next();
			PAMCluster tempCluster = new PAMCluster();
			tempCluster.setMediod(tempItem);
			clusters.add(tempCluster);
		}
		
		//Assign all of the items to the correct cluster
		iItem = remaining.iterator();
		PAMCluster favCluster = null;
		PAMCluster tempPAMCl;
		float dist = Float.MAX_VALUE;
		float newDist = Float.MAX_VALUE;
		while(iItem.hasNext()) {
			tempItem = iItem.next();
			dist = Float.MAX_VALUE;
			
			Iterator<PAMCluster> iPAMCL = clusters.iterator();
			while(iPAMCL.hasNext()) {
				tempPAMCl = iPAMCL.next();
				newDist = tempPAMCl.getMediod().distance(tempItem);
				System.out.println("Found distance " + newDist + " old dist" + dist + " from mediod " + tempPAMCl.getMediod() + " to item " + tempItem);
				if(newDist < dist) {
					dist = newDist;
					favCluster = tempPAMCl;
				}
			}
			
			//Now that you have the favorite, add it
			favCluster.getItems().add(tempItem);
		}
		
		return clusters;
	}

	
	/**
	 * Gets the first medoid from the group of remaining items and puts it into the mediod collection
	 */
	public static void getFirstMedoid(Collection<Item> medoids, Collection<Item> remaining) {
		//find mediod 1
		//Find the item that is the shortest distance from all other items
		Item tempItem = null;
		float compactness = Float.MAX_VALUE;
		float tempComp = 0;
		Iterator<Item> iItem = remaining.iterator();
		Item medoid = null;
		while(iItem.hasNext()) {
			tempItem = iItem.next();
			tempComp = calcCompactness(tempItem, remaining);
			if(tempComp < compactness) {
				compactness = tempComp;
				medoid = tempItem;
			}
		}
		
		remaining.remove(medoid);
		medoids.add(medoid);
	}

	public static void swap(Collection<Item> medoids, Collection<Item> remaining) {
		Iterator<Item> iRemaining;
		Iterator<Item> iMedoids;
		Item tempMedoid;
		Item tempRemaining;
		boolean swapped = true;
		int i = 0;
		while(swapped) {
			swapped = false;

			ArrayList tempRemainingMeds = new ArrayList<Item>(remaining);
			ArrayList tempMedoids = new ArrayList<Item>(medoids);
			
			long started = System.currentTimeMillis();
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
			i++;
			System.out.println("Iteration " + i + " time = " + (System.currentTimeMillis() - started) + "(ms) compactness = " + calcOverallObjective(medoids, remaining));
		}
	}
	
	/**
	 * Tries swapping the items to see if the overall objective can be improved, if it can then it is swapped permanently
	 * 
	 * @return	true if a swap was performed
	 */
	public static boolean checkSwap(Collection<Item> medoids, Item tempMedoid, Collection<Item> remaining, Item swapmedoid) {
		float overallObjective = Float.MAX_VALUE;
		float overallObjective2 = Float.MAX_VALUE;
		float tempComp = 0;

		//System.out.println("Trying to swap medoid [" + tempMedoid + "] with item [" + swapmedoid + "]");
		
		overallObjective = calcOverallObjective(medoids,remaining);

		//Find the objective with the mediod in the collection
		Collection<Item> tempMedoids = new ArrayList<Item>(medoids);
		Collection<Item> tempRemaining = new ArrayList<Item>(remaining);
		tempMedoids.add(swapmedoid);
		tempRemaining.add(tempMedoid);
		tempMedoids.remove(tempMedoid);
		tempRemaining.remove(swapmedoid);

		overallObjective2 = calcOverallObjective(tempMedoids,tempRemaining);

		System.out.println("before[" + overallObjective + "] after [" + overallObjective2 + "]");
		
		if(overallObjective2 < overallObjective) {
			System.out.println("FOUND A BETTER MEDOID ");
			medoids.remove(tempMedoid);
			remaining.remove(swapmedoid);
			medoids.add(swapmedoid);
			remaining.add(tempMedoid);
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the next medoid from the group of remaining items and puts it into the mediod collection
	 */
	public static void getNextMedoid(Collection<Item> medoids, Collection<Item> remaining) {
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
						
			tempComp = calcOverallObjective(tempMedoids, tempRemaining);
			if(tempComp < compactness) {
				//The smallest one is the new medoid
				compactness = tempComp;
				medoid = tempItem;
			}
		}
		
		remaining.remove(medoid);
		medoids.add(medoid);
		
	}
	
	public static void printCollection(String name, Collection<Item> items) {
		Iterator<Item> iItem = items.iterator();
		System.out.println("In collection " + name);
		while(iItem.hasNext()) {
			System.out.println("Found items " + iItem.next());
		}		
	}
	
	/**
	 * Calculates the overall objective
	 */
	public static float calcOverallObjective(Collection<Item> mediods, Collection<Item> remaining) {
		float total = 0;

//		System.out.println("Over Objective:::");
//		printCollection("remaining", remaining);
//		printCollection("medoids", mediods);
		
		Item tempItem;
		Iterator<Item> iItem = remaining.iterator();
		while(iItem.hasNext()) {
			tempItem = iItem.next();
			
			total = total + calcObjective(tempItem, mediods);
		}
		
		return total;
	}
	
	/**
	 * Figures out the compactness by summing the distances from this point to all other points
	 */
	public static float calcObjective(Item item, Collection<Item> mediods) {
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
	 */
	//RENAME THIS, it calculates the middle for the first item
	public static float calcCompactness(Item item, Collection<Item> items) {
		float total = 0;
		
		Item tempItem;
		Iterator<Item> iItem = items.iterator();
		while(iItem.hasNext()) {
			tempItem = iItem.next();
			total = total + item.distance(tempItem);
		}
		return total;
	}
}
