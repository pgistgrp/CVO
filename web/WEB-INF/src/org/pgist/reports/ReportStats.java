package org.pgist.reports;

public class ReportStats {

	private Long id;
	
    /**
     * @return
     * 
     * @hibernate.id generator-class="native"
     */
	public Long getId() {
		return id;
	}

	
	public void setId(Long id) {
		this.id = id;
	}
	
}
