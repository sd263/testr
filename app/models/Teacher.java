package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Teacher extends Model  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public Long id;

	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String password;
	
	@OneToMany
	public List<Classroom> classrooms;
	
	


}
