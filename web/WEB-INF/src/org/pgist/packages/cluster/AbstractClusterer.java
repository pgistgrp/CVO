/**
 * Copyright 18.11.2002 by Markus Maier, mkm@gmx.de
 * 
 * Released under the GNU General Public License,
 * see http://www.gnu.org/licenses/gpl.html
 *
 * $Id$
 * $Log$
 * Revision 1.10  2003/10/16 14:30:50  mkmaier
 * IMP: minor beautifying
 *
 * Revision 1.9  2003/10/09 15:15:45  mkmaier
 * FIX: ooops, that final was omitted for a reason
 *
 * Revision 1.8  2003/10/09 14:59:20  mkmaier
 * IMP: minor code beautifying
 *
 * Revision 1.7  2003/10/02 15:14:05  mkmaier
 * Extensive refactoring to clean up dependencies and code duplications. The design should now be much, much cleaner. First compile error free version, does not work, though.
 *
 * Revision 1.6  2003/09/29 07:58:27  mkmaier
 * IMP: moved distance measure stuff to AbstractHierarchicalClusterer
 *
 * Revision 1.5  2003/09/25 12:45:23  mkmaier
 * IMP: minor code beautifying
 *
 * Revision 1.4  2003/09/24 09:09:29  mkmaier
 * IMP: removed reset()
 * IMP: better comments
 * IMP: changed method names to reflect producer/cosumer pattern
 *
 * Revision 1.3  2003/08/19 18:47:18  mkmaier
 * IMP: updated filecomments
 *
 */
package org.pgist.packages.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/**
 * The abstract base class for all Clusterers. A Clusterer always runs in its
 * own Thread and can only run a single time, though pausing a resuming are
 * possible. To run a Clusterer a second time a new instance needs to be
 * created.
 * 
 * @version 1.0
 * @author Markus Maier
 */
public abstract class AbstractClusterer extends Thread {
	/**
	 * The initial items for this clusters. This does not change during the
	 * clustering.
	 */
	private List initialItems;
	/** The current clusters. */
	protected List clusters;
	/** All current result. */
	private Collection result;
	/** This flag indicates that new data is available for external consumers. */
	private boolean hasNew = false;
	/** The number of steps to go before pausing. */
	private int toGo;
	/** Number of steps so far */
	private int steps = 0;
	/** This flag is set to false when the Cluster Thread should stop running. */
	private boolean run = true;
	/** The number of clusters to produce of applicable. */
	private int numClusters = 10;

	/**
	 * Returns a list of all the clusteres that were created
	 */
	public List getClusters() {
		return clusters;		
	}
	
	/**
	 * Default constructor.
	 * 
	 * @param items
	 *            Initial items for the clusterer.
	 */
	public AbstractClusterer(final Collection items) {
		this.initialItems = new ArrayList(items);
		setName(">>> Clusterer <<<");
		setPriority(Thread.MIN_PRIORITY);
		result = null;
		provideData(items);
		toGo = 0;
		steps = 0;
	}

	/**
	 * Returns <code>true</code> if either the Clusterer has finished or was
	 * told to stop.
	 * 
	 * @return boolean
	 */
	public final boolean isDone() {
		return !run;
	}

	/**
	 * Sets the number of clusters to produce.
	 * 
	 * @param num
	 *            Final number of clusters.
	 */
	public void setNumClusters(final int num) {
		numClusters = num;
	}

	/**
	 * Returns the initial items.
	 * 
	 * @return Collection of initial items.
	 */
	public final Collection getInitialItems() {
		return initialItems;
	}

	/**
	 * Tells the clusterer Thread to exit gracefully.
	 */
	public final synchronized void end() {
		done();
		notifyAll();
		try {
			interrupt();
		} catch (SecurityException e) {
			// This is of no consequence but needs to be caught anyway.
		}
	}

	/**
	 * Tells the Clusterer to execute a certain number of steps.
	 * 
	 * @param stepsToGo
	 *            Number of steps to run.
	 */
	public final void go(final int stepsToGo) {
		toGo = stepsToGo;
		if (isAlive()) {
			// already running, simply unpause
			unPause();
		} else {
			// not yet running, need to start thread
			if (run) {
				start();
			}
		}
	}

	/**
	 * Tells the Clusterer to pause after the currently executing step.
	 */
	public final synchronized void pause() {
		try {
			wait();
		} catch (InterruptedException e) {
			// should not happen, so we do nothing
		}
	}

	/**
	 * Tells the Clusterer to resume.
	 */
	public final synchronized void unPause() {
		notifyAll();
	}

	/**
	 * Sets the number of steps to go to zero, causing the Clusterer to pause
	 * after the currently executing step.
	 */
	public final void endSteps() {
		toGo = 0;
	}

	/**
	 * Tells the Clusterer to call it a day. The Clusterer will stop running
	 * and never resume after the currently executing step.
	 */
	protected final void done() {
		run = false;
	}

	/**
	 * Starts clustering. The clustering task pauses after {@link #toGo toGo}
	 * steps, but can be resumed again.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public final void run() {
		while (run) {
			steps++;
			step();
			if (--toGo < 1) {
				pause();
			}
		}
	}

	/**
	 * Executes one "step" of the algorithm. This should be the smallest
	 * divison of the clustering algorithm that it makes sense to display
	 * graphically, e.g. one iteration.
	 */
	protected abstract void step();

	/**
	 * Returns the number of steps executed to far.
	 * 
	 * @return int
	 */
	public final int getSteps() {
		return steps;
	}

	/**
	 * Returns a random Item from the set of Items to be clustered.
	 * 
	 * @return Random Item.
	 * @throws IndexOutOfBoundsException
	 *             If no Items exist.
	 */
	protected final Item getRandomItem() throws IndexOutOfBoundsException {
		return (Item) initialItems.get((int) (Math.random() * initialItems
				.size()));
	}

	/**
	 * Sets the data for an external Consumer and wakes it up.
	 * 
	 * @param data
	 *            New data.
	 * @see #consumeData()
	 */
	protected final synchronized void provideData(final Collection data) {
		result = data;
		hasNew = true;
		notify();
	}

	/**
	 * Waits for new data if necessary and returns it.
	 * 
	 * @return Current data.
	 * @throws InterruptedException
	 *             When wait() is interrupted.
	 */
	public final synchronized Collection consumeData()
			throws InterruptedException {
		while (!hasNew) {
			wait();
		}
		notify();
		hasNew = false;
		return result;
	}

	/**
	 * Returns the number of steps still to ge before pausing.
	 * 
	 * @return int
	 */
	public int getToGo() {
		return toGo;
	}

	/**
	 * @return Returns the numClusters.
	 */
	public int getNumClusters() {
		return numClusters;
	}
}