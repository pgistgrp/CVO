/**
 * Copyright 19.02.2003 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.3  2003/10/16 14:31:29  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.2  2003/10/02 15:14:05  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 * Revision 1.1  2003/10/01 12:01:39  mkmaier
 * IMP: moved to ..data
 * IMP: Element cleaned up and renamed to Item
 *
 * Revision 1.3  2003/09/25 12:45:23  mkmaier
 * IMP: minor code beautifying
 *
 * Revision 1.2  2003/08/19 18:47:18  mkmaier
 * IMP: updated filecomments
 *
 */
package org.pgist.packages.cluster;

/**
 * This interface should be implemented by all classes which need to be
 * measured for compactness.
 * 
 * @version 1.0
 * @author Markus Maier
 */
public interface Measureable {
	/**
	 * Returns the compactness of this component, i.e. the inverse square
	 * distance of all items to the cluster center.
	 * 
	 * @return float
	 */
	float compactness();
}