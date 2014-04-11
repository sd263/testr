package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Student extends Model {

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
	
	@ManyToMany
	public List<Test> testComplete;
	

	public static Student findStudentbyId(Long id) {
		Student student = new Model.Finder<>(long.class, Student.class)
				.byId(id);
		return student;
	}


	public void addTestTaken(Test test) {
		testComplete.add(test);	
	}


	
	/*
	 * student join classroom code
	 * 
	 * @select( studentForm("classroom.id"), options(Classroom.options), '_label
	 * -> "Classroom", '_default -> "-- Choose a company --", '_showConstraints
	 * -> false ) /*
	 */

}
