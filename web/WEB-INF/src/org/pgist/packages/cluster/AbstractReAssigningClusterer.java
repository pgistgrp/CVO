/**
 * Copyright 17.02.2003 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.5  2003/10/16 14:30:50  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.4  2003/10/09 15:01:00  mkmaier
 * NEW: close(Collection), initClusters(), open(Collection), reCenter(Collection), setNumClusters(int)
 *
 * Revision 1.3  2003/10/06 16:07:04  mkmaier
 * IMP: mainly comment cleanups
 *
 * Revision 1.2  2003/10/02 18:49:54  mkmaier
 * first superficially working version after the Great Refactoring
 *
 * Revision 1.1  2003/10/02 15:14:06  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 * Revision 1.4  2003/09/24 09:10:12  mkmaier
 * IMP: better comments
 *
 * Revision 1.3  2003/08/19 18:47:18  mkmaier
 * IMP: updated filecomments
 *
 */
package org.pgist.packages.cluster;

import java.util.Collection;
import java.util.Iterator;


/**
 * Slightly extended base class for Partitioning clusterers.
 * 
 * @version 1.0
 * @author Markus Maier
 */
public abstract class AbstractReAssigningClusterer extends AbstractClusterer {
	/**
	 * Constructor for AbstractPartitioningClusterer.
	 * 
	 * @param items
	 *            Initial items for the clusterer.
	 */
	public AbstractReAssigningClusterer(final Collection items) {
		super(items);
	}

	/**
	 * Assigns all Items to the normal clusters.
	 * 
	 * @return true if unchanged.
	 */
	protected boolean assignItems() {
		return assignItems(clusters);
	}

	/**
	 * Sets the number of clusters to produce.
	 * 
	 * @param num
	 *            Final number of clusters.
	 */
	public final void setNumClusters(final int num) {
		super.setNumClusters(num);
		if (clusters.size() != num) {
			initClusters();
		}
	}

	/**
	 * Initializes clusters when necessary.
	 */
	public abstract void initClusters();

	/**
	 * Assigns all Items to the normal clusters.
	 * 
	 * @param assignClusters
	 *            Clusters to assign Items to.
	 * @return true if unchanged.
	 */
	protected boolean assignItems(final Collection assignClusters) {
		open(assignClusters);

		Iterator i = getInitialItems().iterator();
		while (i.hasNext()) {
			Item item = (Item) i.next();
			ReAssigningCluster closest = null;
			double dist = Double.MAX_VALUE;
			Iterator j = assignClusters.iterator();
			while (j.hasNext()) {
				ReAssigningCluster clus = (ReAssigningCluster) j.next();
				double newDist = item.distance(clus);
				if (newDist < dist) {
					dist = newDist;
					closest = clus;
				}
			}
			closest.add(item);
		}

		return close(assignClusters);
	}

	/**
	 * Opens all clusters in a Collection.
	 * 
	 * @param openClusters
	 *            Clusters to open.
	 */
	protected void open(final Collection openClusters) {
		Iterator i = openClusters.iterator();
		while (i.hasNext()) {
			((ReAssigningCluster) i.next()).open();
		}
	}

	/**
	 * Closes all clusters in a Collection.
	 * 
	 * @param closeClusters
	 *            Clusters to close.
	 * @return true if clusters did not change.
	 */
	protected boolean close(final Collection closeClusters) {
		Iterator i = closeClusters.iterator();
		boolean unChanged = true;
		while (i.hasNext()) {
			if (!((ReAssigningCluster) i.next()).close()) {
				unChanged = false;
			}
		}
		return unChanged;
	}

	/**
	 * Re-centers all clusters in a Collection.
	 * 
	 * @param centerClusters
	 *            Clusters to re-center.
	 */
	protected void reCenter(final Collection centerClusters) {
		Iterator i = centerClusters.iterator();
		while (i.hasNext()) {
			((ReAssigningCluster) i.next()).reCenter();
		}
	}
}