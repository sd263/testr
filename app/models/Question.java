package models;

import play.db.ebean.Model;

public class Question extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int questionID;
	String questionText;

}
