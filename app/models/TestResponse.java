package models;

import javax.persistence.ManyToOne;

import play.db.ebean.Model;


// The responses to any test from one student.

public class TestResponse extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	long reponseID;
	long testID;
	
	@ManyToOne
	public Test test;
	
	
	TestResponse(long atestID){
		testID = atestID;
	}

}
