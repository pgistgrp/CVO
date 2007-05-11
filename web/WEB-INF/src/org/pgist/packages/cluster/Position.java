/**
 * Copyright 2003 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.2  2003/10/16 14:31:30  mkmaier
 * IMP: minor beautifying
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
public interface Position {
	/**
	 * Returns the distance to a Position.
	 * 
	 * @param c
	 *            Position to compute distance to.
	 * @return Distance to Position c.
	 * @see mkm.clustering.data.Clusterable#distance(mkm.clustering.data.Clusterable)
	 */
	float distance(Position c);

	/**
	 * Returns a numbered coordinate.
	 * 
	 * @param i
	 *            Index of coordinate to return.
	 * @return Value of given coordinate.
	 */
	float getCoordinate(int i);

	/**
	 * Returns number of dimensions this Position has.
	 * 
	 * @return Number of dimensions for this Position.
	 */
	int getDimensions();
}