package org.pgist.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;

public class PAMCluster {

	/**
	 * The center of the cluster
	 */
	private Item mediod;
	
	/**
	 * The collection of items from this cluster
	 */
	private Collection<Item> items = new ArrayList<Item>();
	
	public PAMCluster() {
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
