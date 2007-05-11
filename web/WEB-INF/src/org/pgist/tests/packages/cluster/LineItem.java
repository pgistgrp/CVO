package org.pgist.tests.packages.cluster;

import org.pgist.packages.cluster.Cluster;
import org.pgist.packages.cluster.Clusterable;
import org.pgist.packages.cluster.Item;

public class LineItem implements Item {
	
	float dist = 0;
	public LineItem(float dist) {
		super();
		this.dist = dist;
	}
	
	/* (non-Javadoc)
	 * @see org.pgist.packages.cluster.Clusterable#distance(org.pgist.packages.cluster.Clusterable)
	 */
	public float distance(Clusterable c) {
		if (c instanceof Cluster) {
			return c.distance(this);
		} else {
			return distance((LineItem) c);
		}
	}

	public float distance(LineItem item) {
		return Math.abs(this.dist - item.dist);		
	}
	
	/* (non-Javadoc)
	 * @see org.pgist.packages.cluster.Measureable#compactness()
	 */
	public float compactness() {
		return 0;
	}	
	
	public String toString() {
		return "LineItem: " + dist;
	}
}
