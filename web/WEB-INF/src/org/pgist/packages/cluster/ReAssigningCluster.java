/**
 * Copyright 2003 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.4  2003/10/16 14:31:29  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.3  2003/10/09 15:08:47  mkmaier
 * IMP: minor code beautifying
 *
 * Revision 1.2  2003/10/06 16:07:04  mkmaier
 * IMP: mainly comment cleanups
 *
 * Revision 1.1  2003/10/02 15:14:05  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 */
package org.pgist.packages.cluster;

import java.util.Collection;

/**
 * @author Markus Maier
 * @version 0.1
 */
public interface ReAssigningCluster extends Cluster {
	/**
	 * "Opens" this cluster so new items can be added to it again.
	 */
	void open();
	/**
	 * "Closes" this cluster, i.e. makes new items the displayed items. Returns
	 * <code>true</code> if no change in items.
	 * 
	 * @return boolean <code>true</code> if no change in items.
	 */
	boolean close();
	/**
	 * Adds a new point to this clusterer.
	 * 
	 * @param i
	 *            Item to add to this cluster.
	 */
	void add(Item i);
	/**
	 * Re-centers this cluster.
	 */
	void reCenter();
	/**
	 * Returns the newly assigned Items for this Cluster.
	 * 
	 * @return The newly assigned Items for this Cluster.
	 */
	Collection getNew();
}