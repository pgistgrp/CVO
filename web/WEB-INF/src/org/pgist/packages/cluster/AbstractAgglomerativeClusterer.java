/**
 * Copyright 17.02.2003 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 * 
 * $Id$
 * $Log$
 * Revision 1.5  2003/10/16 14:30:50  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.4 2003/10/09 14:57:40 mkmaier
 * IMP: minor code beautifying
 * 
 * Revision 1.3 2003/10/09 08:25:43 mkmaier
 * IMP: less specialized, can handle any number of clusters and items
 * 
 * Revision 1.2 2003/10/02 18:49:54 mkmaier
 * first superficially working version after the Great Refactoring
 * 
 * Revision 1.1 2003/10/02 15:14:06 mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 * 
 * Revision 1.5 2003/09/29 07:15:16 mkmaier
 * IMP: distance measuer stuff moved to this class
 * 
 * Revision 1.3 2003/08/19 18:47:18 mkmaier
 * IMP: updated filecomments
 *  
 */
package org.pgist.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Slightly extended base class for Agglomerative clusterers.
 * 
 * @version 1.1
 * @author Markus Maier
 */
public abstract class AbstractAgglomerativeClusterer extends AbstractClusterer {
	/** Constant to indicate Single Linkage distance measure. */
	public static final int SINGLE_LINKAGE = 0;
	/** Constant to indicate Complete Linkage distance measure. */
	public static final int COMPLETE_LINKAGE = 1;
	/** Constant to indicate Average Linkage distance measure. */
	public static final int AVERAGE_LINKAGE = 2;

	/** The currently selected distance measure. */
	private int distAlgo = COMPLETE_LINKAGE;

	/**
	 * Constructor for AbstractHierarchichalClusterer.
	 * 
	 * @param items
	 *            Initial elements for the clusterer.
	 */
	public AbstractAgglomerativeClusterer(final Collection items) {
		super(items);
		clusters = new ArrayList();
		clusters.addAll(items);
	}

	/**
	 * Sets the distance measure used.
	 * 
	 * @param m
	 *            Distance measure.
	 */
	public final void setDistMeasure(final int m) {
		distAlgo = m;
	}

	/**
	 * Sets the distance measure used.
	 * 
	 * @return Distance measure code.
	 */
	public final int getDistMeasure() {
		return distAlgo;
	}
}