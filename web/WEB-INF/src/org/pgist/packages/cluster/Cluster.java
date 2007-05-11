/**
 * Copyright 13.12.2002 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.4  2003/10/16 14:31:29  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.3  2003/10/08 11:03:47  mkmaier
 * NEW: added getClusters(), getAllItems()
 *
 * Revision 1.2  2003/10/02 18:49:54  mkmaier
 * first superficially working version after the Great Refactoring
 *
 * Revision 1.1  2003/10/01 12:02:14  mkmaier
 * IMP: moved to ..data
 *
 * Revision 1.3  2003/09/24 09:12:44  mkmaier
 * IMP: better comments
 *
 * Revision 1.2  2003/08/19 18:40:54  mkmaier
 * IMP: updated filecomments
 *
 */
package org.pgist.packages.cluster;

import java.util.Collection;

/**
 * A cluster containing data items and/or other clusters.
 * 
 * @version 1.0
 * @author Markus Maier
 */
public interface Cluster extends Clusterable {
	/**
	 * Returns the Items directly contained in this Cluster. Must return an
	 * empty Collection of there are none!
	 * 
	 * @return The Items directly contained in this Cluster.
	 */
	Collection getItems();

	/**
	 * Returns all Items contained in this Cluster and its subclusters.
	 * 
	 * @return All Items contained in this Cluster and its subclusters.
	 */
	Collection getAllItems();

	/**
	 * Returns the Clusters directly contained in this Cluster. Must return an
	 * empty Collection of there are none!
	 * 
	 * @return The Clusters directly contained in this Cluster.
	 */
	Collection getClusters();
}