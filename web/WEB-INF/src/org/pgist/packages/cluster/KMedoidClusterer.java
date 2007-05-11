/**
 * Copyright 18.11.2002 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.9  2003/10/16 14:30:50  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.8  2003/10/09 15:04:46  mkmaier
 * IMP: big cleanup of cluster subclasses and factories
 * DEL: KMDCluster, KMDClusterFactory
 * NEW: initClusters()
 *
 * Revision 1.7  2003/10/02 18:49:54  mkmaier
 * first superficially working version after the Great Refactoring
 *
 * Revision 1.6  2003/10/02 15:14:06  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 * Revision 1.5  2003/09/24 09:11:59  mkmaier
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


/**
 * A partitioning clusterer using the k-medoid algorithm to progressively re-
 * center cluster centers in each step. The re-centering is somewhat fake.
 * 
 * @version 0.9
 * @author Markus Maier
 */
public final class KMedoidClusterer extends AbstractReAssigningClusterer {
	/** KMDClusterFactory. */
	private ItemCluster.ItemClusterFactory clusterFactory;

	/**
	 * Constructs a KMedoidClusterer.
	 * 
	 * @param items
	 *            Items to cluster,
	 */
	public KMedoidClusterer(final Collection items) {
		this(items, new ItemCluster.ItemClusterFactory());
	}

	/**
	 * Constructs a KMedoidClusterer.
	 * 
	 * @param items
	 *            Items to cluster.
	 * @param factory
	 *            Cluster factory.
	 */
	public KMedoidClusterer(final Collection items,
			final ItemCluster.ItemClusterFactory factory) {
		super(items);
		clusterFactory = factory;
		setName(">>> KMedoidTask <<<");
		initClusters();
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
		if (getToGo() > 0) {
			boolean done = assignItems();
			reCenter(clusters);
			if (done) {
				done();
			}
		}

		ArrayList clus = new ArrayList();
		clus.addAll(clusters);
		provideData(clus);
	}
}