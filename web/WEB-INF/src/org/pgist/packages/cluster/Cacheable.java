/**
 * Copyright 2003 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.2  2003/10/16 14:31:29  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.1  2003/10/02 18:49:54  mkmaier
 * first superficially working version after the Great Refactoring
 *
 * Revision 1.1  2003/10/02 15:14:05  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 */
package org.pgist.packages.cluster;


/**
 * @author Markus Maier
 * @version 0.1
 */
public interface Cacheable extends Clusterable {
	/**
	 * Sets the index for this Clusterable. This is used to quickly find data
	 * in the cache, if used.
	 * 
	 * @param idx
	 *            New index.
	 */
	void setIndex(int idx);

	/**
	 * Returns the index for this Clusterable.
	 * 
	 * @return The index for this Clusterable.
	 */
	int getIndex();

	/**
	 * Sets the cache to use.
	 * 
	 * @param cache
	 *            DistanceCache to use.
	 */
	void setCache(DistanceCache cache);
}