package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import play.db.ebean.Model;

@Entity
public class Student extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	public Long id;

	public String name;
	
	@ManyToOne
	public Classroom classroom;

	
}
