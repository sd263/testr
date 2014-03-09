package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Student extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	public Long id;

	@Constraints.Required
	public String name;
	
	@ManyToOne @Constraints.Required
	public Classroom classroom;

	@ManyToMany
	public List<Test> pastTests;
	
	
	public static Student findStudentbyId(Long id){
		Student student = new Model.Finder<>(long.class, Student.class).byId(id);
		return student;
		
	}
	
}
