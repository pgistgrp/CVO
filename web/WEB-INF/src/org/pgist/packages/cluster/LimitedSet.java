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
 * Revision 1.1  2003/10/06 16:07:04  mkmaier
 * IMP: mainly comment cleanups
 *
 * Revision 1.1  2003/10/01 11:56:09  mkmaier
 * NEW: TreeSet with limit on number of items.
 *
 */
package org.pgist.packages.cluster;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * A TreeSet with a limit on how many items it can contain.
 * 
 * @version 1.0
 * @author Markus Maier
 */
public final class LimitedSet extends TreeSet {
	/** Maximum number of data items. */
	private final int limit;

	/**
	 * Constructor.
	 * 
	 * @param comparator
	 *            Comparator for stored data.
	 * @param sizeLimit
	 *            Limit for data size.
	 */
	public LimitedSet(final Comparator comparator, final int sizeLimit) {
		super(comparator);
		this.limit = sizeLimit;
	}

	/**
	 * Adds two Clusterables and their distance.
	 * 
	 * @param o
	 *            New Object.
	 * @return false if Object already exists, true otherwise.
	 * @see java.util.TreeSet#add(java.lang.Object)
	 */
	public boolean add(final Object o) {
		if (size() < limit) {
			return super.add(o);
		} else {
			Object last = last();
			remove(last);
			return super.add(o);
		}
	}
}