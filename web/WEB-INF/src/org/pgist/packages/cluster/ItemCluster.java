package org.pgist.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;

public class ItemCluster {

	/**
	 * The center of the cluster
	 */
	private Item mediod;
	
	/**
	 * The collection of items from this cluster
	 */
	private Collection<Item> items = new ArrayList<Item>();
	
	public ItemCluster() {
		super();
	}

	public Collection<Item> getItems() {
		return items;
	}

	public void setItems(Collection<Item> items) {
		this.items = items;
	}

	public Item getMediod() {
		return mediod;
	}

	public void setMediod(Item mediod) {
		this.mediod = mediod;
	}
	
	
}
