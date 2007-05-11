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
 * Revision 1.3  2003/10/02 18:49:54  mkmaier
 * first superficially working version after the Great Refactoring
 *
 * Revision 1.2  2003/10/02 15:14:05  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 * Revision 1.1  2003/10/01 12:02:14  mkmaier
 * IMP: moved to ..data
 *
 * Revision 1.3  2003/09/24 09:18:42  mkmaier
 * NEW: extended for caches
 *
 * Revision 1.2  2003/08/19 18:47:18  mkmaier
 * IMP: updated filecomments
 *
 */
package org.pgist.packages.cluster;

/**
 * This interface represents any class which can be clustered. This could be
 * either an Item or a Cluster.
 * 
 * @version 1.0
 * @author Markus Maier
 */
public interface Clusterable extends Measureable {
	/**
	 * Returns the distance to a Clusterable.
	 * 
	 * @param c
	 *            Clusterable to compute distance to.
	 * @return float
	 */
	float distance(Clusterable c);
}