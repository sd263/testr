package models;

import javax.persistence.ManyToOne;

import play.db.ebean.Model;


// The responses to any test from one student.

public class Answer extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	@ManyToOne
	 long questionID;
	
	boolean correct;
	


}
