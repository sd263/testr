package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;


// The responses to any test from one student.
@Entity
public class Answer extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	@Id
	String text;
	
	public Answer(String text){
		this.text = text;
	}
	


}
