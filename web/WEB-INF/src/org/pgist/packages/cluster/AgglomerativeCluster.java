/**
 * Copyright 2003 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.6  2003/10/16 14:31:29  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.5  2003/10/09 15:06:48  mkmaier
 * IMP: sanity checks in constructors
 *
 * Revision 1.4  2003/10/08 11:05:02  mkmaier
 * IMP: changed constructor to make them less specialized
 * FIX: fixed distance calculation code
 *
 * Revision 1.3  2003/10/06 16:07:04  mkmaier
 * IMP: mainly comment cleanups
 *
 * Revision 1.2  2003/10/02 18:49:54  mkmaier
 * first superficially working version after the Great Refactoring
 *
 * Revision 1.1  2003/10/02 15:14:05  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 */
package org.pgist.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * Cluster subclass for this clusterer.
 * 
 * @version 1.0
 * @author Markus Maier
 */
public class AgglomerativeCluster extends AbstractCachable implements Cluster {
	/** A Collection of the Items in this cluster, excluding subclusters. */
	private final Collection items = new ArrayList();
	/** A Collection of the subclusters in this cluster. */
	private final Collection clusters = new ArrayList();
	/** This cluster's compactness. */
	private float compactness;
	/** Distance measure to use. */
	private final int distMeasure;
	/** Collection of all items contained in this cluster and ist subclusters. */
	private List allItems = new ArrayList();

	/**
	 * Default constructor.
	 * 
	 * @param items
	 *            Items this Cluster will contain.
	 * @param clusters
	 *            Clusters this Cluster will contain.
	 * @param distMeasure
	 *            Distance measure to use.
	 */
	public AgglomerativeCluster(final Collection clusters,
			final Collection items, final int distMeasure) {
		this.distMeasure = distMeasure;
		this.items.addAll(items);
		this.allItems.addAll(items);
		this.clusters.addAll(clusters);
		Iterator i = clusters.iterator();
		while (i.hasNext()) {
			this.allItems.addAll(((Cluster) i.next()).getAllItems());
		}

		if (allItems.size() == 0) {
			throw new IllegalArgumentException();
		}

		compactness = compactnessImpl();
	}

	/**
	 * Default constructor.
	 * 
	 * @param items
	 *            Items this Cluster will contain.
	 * @param clusters
	 *            Clusters this Cluster will contain.
	 * @param distMeasure
	 *            Distance measure to use.
	 */
	public AgglomerativeCluster(final Cluster[] clusters, final Item[] items,
			final int distMeasure) {
		this.distMeasure = distMeasure;
		int i;
		for (i = 0; i < clusters.length; i++) {
			this.clusters.add(clusters[i]);
			this.allItems.addAll(clusters[i].getAllItems());
		}
		for (i = 0; i < items.length; i++) {
			this.items.add(items[i]);
			this.allItems.add(items[i]);
		}

		if (allItems.size() == 0) {
			throw new IllegalArgumentException();
		}

		compactness = compactnessImpl();
	}

	/**
	 * @see mkm.clustering.data.Cluster#getItems()
	 */
	public Collection getItems() {
		return items;
	}

	/**
	 * @see mkm.clustering.data.Cluster#getClusters()
	 */
	public Collection getClusters() {
		return clusters;
	}

	/**
	 * Returns the distance to the given Item.
	 * 
	 * @param o
	 *            Item to calculate the distance to.
	 * @return float
	 */
	protected final float distance(final Item o) {
		switch (distMeasure) {
			case AbstractAgglomerativeClusterer.SINGLE_LINKAGE :
				return distSingle(o);
			case AbstractAgglomerativeClusterer.COMPLETE_LINKAGE :
				return distComplete(o);
			default :
			case AbstractAgglomerativeClusterer.AVERAGE_LINKAGE :
				return distAverage(o);
		}
	}

	/**
	 * Returns the distance to the given Cluster.
	 * 
	 * @param o
	 *            Cluster to calculate the distance to.
	 * @return float
	 */
	protected final float distance(final Cluster o) {
		switch (distMeasure) {
			case AbstractAgglomerativeClusterer.SINGLE_LINKAGE :
				return distSingle(o);
			case AbstractAgglomerativeClusterer.COMPLETE_LINKAGE :
				return distComplete(o);
			default :
			case AbstractAgglomerativeClusterer.AVERAGE_LINKAGE :
				return distAverage(o);
		}
	}

	/**
	 * Returns the Average Linkage distance to an Item.
	 * 
	 * @param o
	 *            Item to compute distance to.
	 * @return float
	 */
	protected final float distAverage(final Item o) {
		float dist = 0.0f;
		Iterator i = getAllItems().iterator();
		while (i.hasNext()) {
			Item p = (Item) i.next();
			dist += p.distance(o);
		}
		dist /= getAllItems().size();
		return dist;
	}

	/**
	 * Returns the Average Linkage distance to a Cluster.
	 * 
	 * @param o
	 *            Cluster to compute distance to.
	 * @return float
	 */
	protected final float distAverage(final Cluster o) {
		float dist = 0.0f;
		Iterator i = getAllItems().iterator();
		while (i.hasNext()) {
			Item p = (Item) i.next();
			Iterator j = o.getAllItems().iterator();
			while (j.hasNext()) {
				Item q = (Item) j.next();
				dist += p.distance(q);
			}
		}
		dist /= (getAllItems().size() * o.getAllItems().size());
		return dist;
	}

	/**
	 * Returns the Single Linkage distance to an Item.
	 * 
	 * @param o
	 *            Item to compute distance to.
	 * @return float
	 */
	protected final float distSingle(final Item o) {
		float dist = Float.MAX_VALUE;
		Iterator i = getAllItems().iterator();
		while (i.hasNext()) {
			Item p = (Item) i.next();
			float d = p.distance(o);
			if (d < dist) {
				dist = d;
			}
		}
		return dist;
	}

	/**
	 * Returns the Single Linkage distance to a Cluster.
	 * 
	 * @param o
	 *            Cluster to compute distance to.
	 * @return float
	 */
	protected final float distSingle(final Cluster o) {
		float dist = Float.MAX_VALUE;
		Iterator i = getAllItems().iterator();
		while (i.hasNext()) {
			Item p = (Item) i.next();
			Iterator j = o.getAllItems().iterator();
			while (j.hasNext()) {
				Item q = (Item) j.next();
				float d = p.distance(q);
				if (d < dist) {
					dist = d;
				}
			}
		}
		return dist;
	}

	/**
	 * Returns the Complete Linkage distance to an Item.
	 * 
	 * @param o
	 *            Item to compute distance to.
	 * @return float
	 */
	protected final float distComplete(final Item o) {
		float dist = 0.0f;
		// TODO replace iterators?
		Iterator i = getAllItems().iterator();
		while (i.hasNext()) {
			Item p = (Item) i.next();
			float d = p.distance(o);
			if (d > dist) {
				dist = d;
			}
		}
		return dist;
	}

	/**
	 * Returns the Complete Linkage distance to a Cluster.
	 * 
	 * @param o
	 *            Cluster to compute distance to.
	 * @return float
	 */
	protected final float distComplete(final Cluster o) {
		float dist = 0.0f;
		Iterator i = getAllItems().iterator();
		while (i.hasNext()) {
			Item p = (Item) i.next();
			Iterator j = o.getAllItems().iterator();
			while (j.hasNext()) {
				Item q = (Item) j.next();
				float d = p.distance(q);
				if (d > dist) {
					dist = d;
				}
			}
		}
		return dist;
	}

	/**
	 * @see mkm.clustering.data.Clusterable#distance(Clusterable)
	 */
	public float distance(final Clusterable c) {
		if (c instanceof Cluster) {
			return distance((Cluster) c);
		} else {
			return distance((Item) c);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "AgglomerativeCluster(" + getClusters() + "/" + getItems() + ")";
	}

	/**
	 * Computes the compactness of this cluster.
	 * 
	 * @return float
	 * @see #compactness()
	 */
	protected float compactnessImpl() {
		float rc = 0.0f;
		for (int i = 0; i < allItems.size() - 1; i++) {
			Item p = (Item) allItems.get(i);
			for (int j = i + 1; j < allItems.size(); j++) {
				rc += Math.pow(p.distance((Item) allItems.get(j)), 2);
			}
		}
		return 1
				- 2 * rc / ((getAllItems().size() * (getAllItems().size() - 1)));
	}

	/**
	 * @see mkm.clustering.data.Measureable#compactness()
	 */
	public float compactness() {
		return compactness;
	}

	/**
	 * @see mkm.clustering.data.Cluster#getAllItems()
	 */
	public Collection getAllItems() {
		return allItems;
	}
}