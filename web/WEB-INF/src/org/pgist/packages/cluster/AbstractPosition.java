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
 * Revision 1.3  2003/10/09 15:06:25  mkmaier
 * IMP: sanity checks in constructors
 *
 * Revision 1.2  2003/10/06 16:07:05  mkmaier
 * IMP: mainly comment cleanups
 *
 * Revision 1.1  2003/10/02 15:14:05  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 */
package org.pgist.packages.cluster;

/**
 * A position in n-dimensional space which can be clustered.
 * 
 * @author Markus Maier
 * @version 0.1
 */
public abstract class AbstractPosition implements Position {
	/** Coordinates in space for this Position. */
	protected float[] coordinates;

	/**
	 * Constructs a Position.
	 * 
	 * @param coords
	 *            Coordinates.
	 */
	public AbstractPosition(final float[] coords) {
		if (coords.length == 0) {
			throw new IllegalArgumentException();
		}
		coordinates = coords;
	}

	/**
	 * Constructs a Position.
	 * 
	 * @param pos
	 *            Position to copy.
	 */
	public AbstractPosition(final Position pos) {
		if (pos.getDimensions() == 0) {
			throw new IllegalArgumentException();
		}
		coordinates = new float[pos.getDimensions()];
		for (int i = 0; i < coordinates.length; i++) {
			coordinates[i] = pos.getCoordinate(i);
		}
	}

	/**
	 * Returns the distance to a Position.
	 * 
	 * @param c
	 *            Position to compute distance to.
	 * @return Distance to Position c.
	 * @see mkm.clustering.data.Clusterable#distance(mkm.clustering.data.Clusterable)
	 */
	public float distance(final Position c) {
		if (coordinates.length != c.getDimensions()) {
			throw new IllegalArgumentException();
		}
		float dist = 0f;
		for (int i = 0; i < coordinates.length; i++) {
			dist += Math.pow(coordinates[i] - c.getCoordinate(i), 2);
		}
		return (float) Math.sqrt(dist);
	}

	/**
	 * Returns the distance to a set of coordinates.
	 * 
	 * @param c
	 *            Coordinates to calculate distance to.
	 * @return Distance to coordinates.
	 */
	protected float distance(final float[] c) {
		if (coordinates.length != c.length) {
			throw new IllegalArgumentException();
		}
		float dist = 0f;
		for (int i = 0; i < coordinates.length; i++) {
			dist += Math.pow(coordinates[i] - c[i], 2);
		}
		return (float) Math.sqrt(dist);
	}

	/**
	 * Returns a numbered coordinate.
	 * 
	 * @param i
	 *            Index of coordinate to return.
	 * @return Value of given coordinate.
	 */
	public float getCoordinate(final int i) {
		return coordinates[i];
	}

	/**
	 * Returns number of dimensions this Position has.
	 * 
	 * @return Number of dimensions for this Position.
	 */
	public int getDimensions() {
		return coordinates.length;
	}
}