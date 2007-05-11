/**
 * Copyright 19.02.2003 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.5  2003/10/16 14:31:29  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.4  2003/10/09 15:07:47  mkmaier
 * IMP: sanity checks in constructors and other places
 * NEW: reCenter()
 *
 * Revision 1.3  2003/10/08 11:03:12  mkmaier
 * NEW: added getClusters(), toString(), getAllItems()
 *
 * Revision 1.2  2003/10/06 16:07:04  mkmaier
 * IMP: mainly comment cleanups
 *
 * Revision 1.1  2003/10/02 15:14:05  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 * Revision 1.1  2003/10/01 12:02:43  mkmaier
 * IMP: moved to ..data
 *
 * Revision 1.4  2003/09/24 09:13:08  mkmaier
 * IMP: better comments
 *
 * Revision 1.3  2003/08/25 20:51:33  mkmaier
 * IMP: minor name improvements
 *
 * Revision 1.2  2003/08/19 18:47:18  mkmaier
 * IMP: updated filecomments
 *
 */
package org.pgist.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Base class for clusters created by partitioning clusterers.
 * 
 * @version 1.0
 * @author Markus Maier
 */
public class ItemCluster implements Item, ReAssigningCluster {
	/** A collection of all Points in this cluster. */
	private Collection items = new ArrayList();
	/** The compactness of this cluster. */
	private float compactness;
	/** A collection of all new items. */
	private Collection newItems = new ArrayList();
	/** The representative Item of this Cluster. */
	private Item representative;
	/** Flag indicating the status of this cluster. */
	private boolean open = false;

	/**
	 * Default constructor.
	 * 
	 * @param representative
	 *            The representative for this cluster.
	 */
	protected ItemCluster(final Item representative) {
		if (representative == null) {
			throw new NullPointerException();
		}
		this.representative = representative;
	}

	/**
	 * Adds a new point to this clusterer.
	 * 
	 * @param p
	 *            Point to add to this cluster.
	 */
	public final void add(final Item p) {
		if (!open) {
			throw new IllegalStateException();
		}

		newItems.add(p);
	}

	/**
	 * "Closes" this cluster, i.e. makes new items the displayed items. Returns
	 * <code>true</code> if no change in items.
	 * 
	 * @return boolean
	 */
	public final boolean close() {
		if (!open) {
			throw new IllegalStateException();
		}
		open = false;
		boolean rc = false;
		if (items.containsAll(newItems) && newItems.containsAll(items)) {
			rc = true;
		}
		items = newItems;
		newItems = null;
		calcCompactness();
		return rc;
	}

	/**
	 * "Opens" this cluster so new items can be added to it again.
	 */
	public final void open() {
		if (open) {
			throw new IllegalStateException();
		}
		open = true;
		newItems = new ArrayList();
	}

	/**
	 * Returns the compactness of this cluster respective to a given
	 * representative item.
	 * 
	 * @param rep
	 *            Representative item to compute relative compactness to.
	 * @return Relative compactness of this cluster.
	 */
	public final float compactness(final Clusterable rep) {
		if (items.size() > 0) {
			float rc = 0.0f;
			Iterator i = items.iterator();
			while (i.hasNext()) {
				Item p = (Item) i.next();
				rc += Math.pow(rep.distance(p), 2);
			}
			return 1 - (rc / items.size());
		} else {
			return 1.0f;
		}
	}

	/**
	 * Forces a (re)calculation of this cluster's compactness.
	 */
	public final void calcCompactness() {
		compactness = compactness(representative);
	}
	/**
	 * @see mkm.clustering.data.Cluster#getItems()
	 */
	public Collection getItems() {
		return items;
	}

	/**
	 * @return EMPTY_LIST since this type of Cluster cannot contain other
	 *         clusters.
	 * @see mkm.clustering.data.Cluster#getClusters()
	 */
	public Collection getClusters() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @see mkm.clustering.data.Measureable#compactness()
	 */
	public float compactness() {
		return compactness;
	}

	/**
	 * Returns the new items.
	 * 
	 * @return newItems.
	 */
	public Collection getNew() {
		if (!open) {
			throw new IllegalStateException();
		}

		return newItems;
	}

	/**
	 * Returns the distance to a certain Clusterable.
	 * 
	 * @param c
	 *            Clusterable to compute distance to.
	 * @return The distance to a certain Clusterable.
	 * @see mkm.clustering.data.Clusterable#distance(mkm.clustering.data.Clusterable)
	 */
	public float distance(final Clusterable c) {
		return representative.distance(c);
	}
	/**
	 * Returns the representative Item of this cluster.
	 * 
	 * @return The representative Item of this cluster.
	 */
	public Item getRepresentative() {
		return representative;
	}

	/**
	 * Sets the representative Item of this cluster.
	 * 
	 * @param rep
	 *            The representative to set.
	 */
	protected void setRepresentative(final Item rep) {
		if (rep == null) {
			throw new NullPointerException();
		}
		representative = rep;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "ItemCluster(" + representative + ")";
	}

	/**
	 * @see mkm.clustering.data.Cluster#getAllItems()
	 */
	public Collection getAllItems() {
		return getItems();
	}

	/**
	 * Re-calculates the center.
	 */
	public void reCenter() {
		if (getItems().size() > 0) {
			Iterator i = getItems().iterator();
			Item a = null;
			float comp = 0f;
			while (i.hasNext()) {
				Item b = (Item) i.next();
				float newComp = compactness(b);
				if (newComp > comp) {
					comp = newComp;
					a = b;
				}
			}
			setRepresentative(a);
		} else {
			System.out.println("Can't recenter, only " + getNew().size()
					+ " new items.");
		}
	}

	/**
	 * Provides a means to use any subclass of ItemCluster for clustering.
	 * 
	 * @author Markus Maier
	 * @version 0.1
	 */
	public static class ItemClusterFactory {
		/**
		 * Returns a new ItemCluster instance.
		 * 
		 * @param representative
		 *            Representative Item for this cluster.
		 * @return A new ItemCluster instance.
		 */
		public ItemCluster produce(final Item representative) {
			System.out.println("MATT: Setting up random item " + representative);
			return new ItemCluster(representative);
		}
	}
}