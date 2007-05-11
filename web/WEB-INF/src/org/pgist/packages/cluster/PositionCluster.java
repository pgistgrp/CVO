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
 * Revision 1.5  2003/10/09 15:08:27  mkmaier
 * IMP: sanity checks in constructors and other places
 * NEW: reCenter(), getAllItems()
 *
 * Revision 1.4  2003/10/08 11:02:35  mkmaier
 * NEW: added getClusters() and toString()
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
import java.util.Collections;
import java.util.Iterator;

/**
 * @author Markus Maier
 * @version 0.1
 */
public class PositionCluster extends AbstractPosition
		implements
			ReAssigningCluster {
	/** A collection of all Points in this cluster. */
	private Collection items = new ArrayList();
	/** The compactness of this cluster. */
	private float compactness;
	/** A collection of all new items. */
	private Collection newItems = new ArrayList();
	/** Flag indicating the status of this cluster. */
	private boolean open = false;

	/**
	 * Default constructor.
	 * 
	 * @param center
	 *            The center for this cluster.
	 */
	protected PositionCluster(final Position center) {
		super(center);
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
	 * Returns the compactness of this Cluster respective to a given Position.
	 * 
	 * @param center
	 *            Position to calculate relative compactness to.
	 * @return Compactness of this Cluster.
	 */
	public final float compactness(final Position center) {
		if (items.size() > 0) {
			float rc = 0.0f;
			Iterator i = items.iterator();
			while (i.hasNext()) {
				Position p = (Position) i.next();
				rc += Math.pow(center.distance(p), 2);
			}
			return 1 - (rc / items.size());
		} else {
			return 1.0f;
		}
	}

	/**
	 * Forces the (re)calculation of this cluster's compactness.
	 */
	protected final void calcCompactness() {
		compactness = compactness(this);
	}

	/**
	 * Returns this cluster's compactness.
	 * 
	 * @return This cluster's compactness.
	 * @see mkm.clustering.data.Measureable#compactness()
	 */
	public float compactness() {
		return compactness;
	}
	/**
	 * Returns the Items contained in this cluster.
	 * 
	 * @return The Items contained in this cluster.
	 * @see mkm.clustering.data.Cluster#getItems()
	 */
	public Collection getItems() {
		return items;
	}

	/**
	 * @return EMPTY_LIST since this type of Cluster does not allow
	 *         subclusters.
	 * @see mkm.clustering.data.Cluster#getClusters()
	 */
	public Collection getClusters() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * Returns the new Items contained in this cluster.
	 * 
	 * @return The new Items contained in this cluster.
	 * @see mkm.clustering.data.ReAssigningCluster#getNew()
	 */
	public final Collection getNew() {
		if (!open) {
			throw new IllegalStateException();
		}

		return newItems;
	}

	/**
	 * Returns the distance between the center of this Cluster and the given
	 * Clusterable.
	 * 
	 * @param c
	 *            Clusterable to compute distance to.
	 * @return Distance between the center of this Cluster and the given
	 *         Clusterable.
	 * @see mkm.clustering.data.Clusterable#distance(mkm.clustering.data.Clusterable)
	 */
	public float distance(final Clusterable c) {
		Position p = (Position) c;
		return distance(p);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "PositionCluster(" + coordinates + ")";
	}

	/**
	 * @see mkm.clustering.data.ReAssigningCluster#reCenter()
	 */
	public void reCenter() {
		int j;
		for (j = 0; j < coordinates.length; j++) {
			coordinates[j] = 0f;
		}
		Iterator i = getItems().iterator();
		while (i.hasNext()) {
			Position p = (Position) i.next();
			for (j = 0; j < coordinates.length; j++) {
				coordinates[j] += p.getCoordinate(j);
			}
		}
		int size = getItems().size();
		for (j = 0; j < coordinates.length; j++) {
			coordinates[j] /= size;
		}
	}

	/**
	 * @see mkm.clustering.data.Cluster#getAllItems()
	 */
	public Collection getAllItems() {
		return items;
	}

	/**
	 * Interface allowing the use of PositionCluster subclasses for clustering.
	 * 
	 * @author Markus Maier
	 * @version 0.1
	 */
	public static class PositionClusterFactory {
		/**
		 * Produces a new PositionCluster instance.
		 * 
		 * @param p
		 *            Position for the new instance.
		 * @return New PositionCluster instance.
		 */
		public PositionCluster produce(final Position p) {
			return new PositionCluster(p);
		}
	}
}