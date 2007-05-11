/**
 * Copyright 25.09.2003 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.4  2003/10/16 14:31:29  mkmaier
 * IMP: minor beautifying
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
 * Revision 1.1  2003/10/01 12:02:31  mkmaier
 * IMP: moved to ..data
 *
 * Revision 1.1  2003/09/25 12:46:01  mkmaier
 * NEW: base implementation for Clusterables
 *
 */
package org.pgist.packages.cluster;



/**
 * Base implementation for Clusterables.
 * 
 * @version 1.0
 * @author Markus Maier
 */
public abstract class AbstractCachable implements Cacheable {
	/** Index for the cache. */
	private int index = -1;
	/** DistanceCache. */
	private DistanceCache distanceCache;

	/**
	 * Sets the index used for this Cluster in the cache.
	 * 
	 * @param idx
	 *            Index used in cache.
	 * @see mkm.clustering.data.Cacheable#setIndex(int)
	 */
	public void setIndex(final int idx) {
		index = idx;
	}

	/**
	 * Returns this cluster's index in the cache.
	 * 
	 * @return This cluster's index in the cache.
	 * @see mkm.clustering.data.Cacheable#getIndex()
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param cache
	 * @see mkm.clustering.data.Cacheable#setCache(DistanceCache)
	 */
	public void setCache(final DistanceCache cache) {
		distanceCache = cache;
	}

	/**
	 * Returns the DistanceCache for this Object.
	 * 
	 * @return The DistanceCache for this Object.
	 */
	public DistanceCache getCache() {
		return distanceCache;
	}
}