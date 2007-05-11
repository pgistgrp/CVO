/**
 * Copyright 10.09.2003 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.6  2003/10/16 14:30:50  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.5  2003/10/09 15:02:35  mkmaier
 * IMP: minor code beautifying
 *
 * Revision 1.4  2003/10/02 18:49:54  mkmaier
 * first superficially working version after the Great Refactoring
 *
 * Revision 1.3  2003/10/02 15:14:06  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 * Revision 1.2  2003/09/25 12:45:23  mkmaier
 * IMP: minor code beautifying
 *
 * Revision 1.1  2003/09/24 09:20:49  mkmaier
 * NEW: basic cache for distances
 *
 */
package org.pgist.packages.cluster;

import java.util.Collection;
import java.util.List;


/**
 * Base implementation of a cache for distance values.
 * 
 * @version 1.0
 * @author Markus Maier
 */
public class DistanceCache {
	/** Distance matrix, stored in an array for efficiency. */
	private final float[] distance;
	/** Number of items. */
	private final int size;

	/**
	 * Hide default constructor.
	 */
	protected DistanceCache() {
		distance = null;
		size = -1;
	}

	/**
	 * Single public constructor.
	 * 
	 * @param items
	 *            The items that should be cached.
	 */
	public DistanceCache(final Collection items) {
		size = items.size();
		distance = new float[(size * (size - 1)) / 2];
	}

	/**
	 * Initializes the cache. Override this for your own subclass.
	 * 
	 * @param items
	 *            Items to cache.
	 */
	public void initDistances(final List items) {
		for (int i = 0; i < size - 1; i++) {
			Cacheable c1 = (Cacheable) items.get(i);
			c1.setIndex(i);
			for (int j = i + 1; j < size; j++) {
				Cacheable c2 = (Cacheable) items.get(j);
				distance[indexOf(i, j)] = c1.distance(c2);
			}
		}
		Cacheable last = (Cacheable) items.get(size - 1);
		last.setIndex(size - 1);
	}

	/**
	 * Returns the distance between two items.
	 * 
	 * @param x
	 *            Index of first item.
	 * @param y
	 *            Index of second item.
	 * @return Distance between the two items.
	 */
	public final float distance(final int x, final int y) {
		return distance[indexOf(x, y)];
	}

	/**
	 * Computes index into cache array.
	 * 
	 * @param x
	 *            First coordinate.
	 * @param y
	 *            Second coordinate.
	 * @return Index into cache array.
	 */
	protected final int indexOf(final int x, final int y) {
		if (x < 0 || x >= size || y < 0 || y >= size || x == y) {
			throw new IllegalArgumentException("Cannot compute index of " + x
					+ " and " + y + ".");
		}
		if (x > y) {
			return ((x - 1) * x) / 2 + y;
		} else {
			return ((y - 1) * y) / 2 + x;
		}
	}

	/**
	 * Returns the distance between two items.
	 * 
	 * @param c1
	 *            First item.
	 * @param c2
	 *            Second item.
	 * @return Distance between the two items.
	 */
	public float distance(final Clusterable c1, final Clusterable c2) {
		if (c1 instanceof Cacheable && c2 instanceof Cacheable) {
			return distance((Cacheable) c1, (Cacheable) c2);
		} else {
			return c1.distance(c2);
		}
	}

	/**
	 * Returns the distance between two items.
	 * 
	 * @param c1
	 *            First item.
	 * @param c2
	 *            Second item.
	 * @return Distance between the two items.
	 */
	public float distance(final Cacheable c1, final Cacheable c2) {
		try {
			return distance(c1.getIndex(), c2.getIndex());
		} catch (IllegalArgumentException e) {
			return c1.distance(c2);
		}
	}

	/**
	 * @return Returns the size.
	 */
	public final int getSize() {
		return size;
	}
}