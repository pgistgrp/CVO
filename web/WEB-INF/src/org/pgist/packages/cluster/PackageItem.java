package org.pgist.packages.cluster;

public class PackageItem implements Item {

	/* (non-Javadoc)
	 * @see org.pgist.packages.cluster.Clusterable#distance(org.pgist.packages.cluster.Clusterable)
	 */
	public float distance(Clusterable c) {
		//Get this from the daisy algorithm
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.pgist.packages.cluster.Measureable#compactness()
	 */
	public float compactness() {
		//We think this is calculated elsewhere
		return 0;
	}
}
