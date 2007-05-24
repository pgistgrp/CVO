package org.pgist.tests.packages.cluster;

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
	public float distance(Item c) {
		if (c instanceof Item) {
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

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LineItem) {
			LineItem tempL = (LineItem)obj;
			if(tempL.dist == this.dist) return true;
		}
		return false;

	}
	
	
}
