package org.pgist.tests.packages.cluster;

import org.pgist.packages.cluster.Cluster;
import org.pgist.packages.cluster.Clusterable;
import org.pgist.packages.cluster.Item;

public class PointItem implements Item {
	
	float x;
	float y;
	public PointItem(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/* (non-Javadoc)
	 * @see org.pgist.packages.cluster.Clusterable#distance(org.pgist.packages.cluster.Clusterable)
	 */
	public float distance(Clusterable c) {
		if (c instanceof Cluster) {
			return c.distance(this);
		} else {
			return distance((PointItem) c);
		}
	}

	public float distance(PointItem item) {
		float distx = this.x - item.x;
		float disty = this.y - item.y;
		return (float)Math.sqrt(distx* distx + disty*disty);		
	}
	
	/* (non-Javadoc)
	 * @see org.pgist.packages.cluster.Measureable#compactness()
	 */
	public float compactness() {
		return 0;
	}	
	
	public String toString() {
		return "PointItem: [" + x + "," + y + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PointItem) {
			PointItem tempL = (PointItem)obj;
			if(tempL.x == this.x && tempL.y == this.y) return true;
		}
		return false;
	}
}
