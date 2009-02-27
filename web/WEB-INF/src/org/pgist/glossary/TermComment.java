package org.pgist.glossary;


/**
 * Glossary Term Comment.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_term_comment" lazy="true"
 */
public class TermComment {
	
	
	private Long id;
	
	private String comment;
	
	
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
	
	
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


}//class TermComment
