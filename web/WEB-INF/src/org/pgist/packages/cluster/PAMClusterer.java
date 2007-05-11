/**
 * Copyright 26.01.2003 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.12  2003/10/16 14:30:50  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.11  2003/10/09 15:05:48  mkmaier
 * IMP: big cleanup of cluster subclasses and factories
 * NEW: initClusters()
 * NEW: use of a clusterFactory
 * IMP: minor code cleanups
 *
 * Revision 1.10  2003/10/09 08:22:29  mkmaier
 * IMP: better encapsulation following changes in Cluster
 *
 * Revision 1.9  2003/10/06 16:07:04  mkmaier
 * IMP: mainly comment cleanups
 *
 * Revision 1.8  2003/10/02 18:49:54  mkmaier
 * first superficially working version after the Great Refactoring
 *
 * Revision 1.7  2003/10/02 15:14:06  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 * Revision 1.6  2003/09/25 12:45:23  mkmaier
 * IMP: minor code beautifying
 *
 * Revision 1.5  2003/09/24 09:12:00  mkmaier
 * IMP: removed reset()
 * IMP: method names reflect producer/consumer pattern
 *
 * Revision 1.4  2003/08/27 11:51:08  mkmaier
 * FIX: getColor()/getElements() instead of field accesses
 *
 * Revision 1.3  2003/08/19 18:47:18  mkmaier
 * IMP: updated filecomments
 *
 */
package org.pgist.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A partitioning clusterer using the PAM algorithm by Kaufman & Rousseeuw,
 * 1990 which exchanges a cluster-center with a non-cluster-center in each
 * step.
 * 
 * @version 1.0
 * @author Markus Maier
 */
public final class PAMClusterer extends AbstractReAssigningClusterer {
	/** Cluster factory. */
	private final ItemCluster.ItemClusterFactory clusterFactory;

	/**
	 * Constructs a PAMClusterer.
	 * 
	 * @param items
	 *            Items to cluster.
	 */
	public PAMClusterer(final Collection items) {
		this(items, new ItemCluster.ItemClusterFactory());
	}

	/**
	 * Constructs a PAMClusterer.
	 * 
	 * @param items
	 *            Items to cluster.
	 * @param factory
	 *            Cluster factory.
	 */
	public PAMClusterer(final Collection items,
			final ItemCluster.ItemClusterFactory factory) {
		super(items);
		setName(">>> PAMTask <<<");
		clusterFactory = factory;
		initClusters();
		assignItems();
	}

	/**
	 * @see mkm.clustering.clusterer.AbstractReAssigningClusterer#initClusters()
	 */
	public void initClusters() {
		clusters = new ArrayList(getNumClusters());
		for (int i = 0; i < getNumClusters(); i++) {
			clusters.add(clusterFactory.produce(getRandomItem()));
		}
	}

	/**
	 * @see mkm.clustering.clusterer.AbstractClusterer#step()
	 */
	protected void step() {
		// TODO cleanup
		// TODO history
		// FIXME clustering
		Cluster exchC = null;
		Item exchP = null;
		float delta = 0.0f;
		float overallCompactness = overallCompactness(clusters);

		// iterate over all clusters to exchange
		Iterator i = clusters.iterator();
		while (i.hasNext() && !isInterrupted()) {
			Cluster c = (Cluster) i.next();
			// iterate over all data items to exchange clusters with
			Iterator j = getInitialItems().iterator();
			while (j.hasNext() && !isInterrupted()) {
				Item p = (Item) j.next();
				Collection perm = createPermutation(c, p);
				float d = overallCompactness(perm) - overallCompactness;
				if (d > delta) {
					delta = d;
					exchC = c;
					exchP = p;
				}
			}
		}

		if (exchC != null && exchP != null) {
			clusters
					.set(clusters.indexOf(exchC), clusterFactory.produce(exchP));

			assignItems();
			calcCompactness(clusters);
		} else {
			done();
		}

		ArrayList clus = new ArrayList();
		clus.addAll(clusters);
		provideData(clus);
	}

	/**
	 * Creates a new permutation.
	 * 
	 * @param whichCluster
	 *            Cluster to exchange.
	 * @param newCluster
	 *            New cluster center to use for the indicated cluster.
	 * @return The permutated clusters.
	 */
	protected ArrayList createPermutation(final Cluster whichCluster,
			final Item newCluster) {
		ArrayList clus = new ArrayList();
		clus.addAll(clusters);
		int pos = clus.indexOf(whichCluster);
		clus.set(pos, clusterFactory.produce(newCluster));

		assignItems(clus);
		calcCompactness(clus);
		return clus;
	}

	/**
	 * Calculates the overall compactness of this clustering.
	 * 
	 * @param clus
	 *            Array of all clusters.
	 * @return float
	 */
	protected static float overallCompactness(final Collection clus) {
		float rc = 0.0f;
		Iterator i = clus.iterator();
		while (i.hasNext()) {
			rc += ((Cluster) i.next()).compactness();
		}
		return rc;
	}

	/**
	 * Calculates the compactness for all clusters in a Collection.
	 * 
	 * @param compClusters
	 *            Clusters to calculate compactness of.
	 */
	protected void calcCompactness(final Collection compClusters) {
		Iterator i = compClusters.iterator();
		while (i.hasNext()) {
			((ItemCluster) i.next()).calcCompactness();
		}
	}
}