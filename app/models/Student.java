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
	 * A student Account details
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	@Constraints.Required
	public String name;

	@Constraints.Required
	public String password;

	/**
	 * List of all tests completed by the Student
	 */
	@ManyToMany
	public List<Test> testComplete;
	
	 public static Model.Finder<Long,Student> find = new Model.Finder<Long,Student>(Long.class, Student.class);

	/**
	 * Returns a Student with their Id
	 * 
	 * @param id
	 *            The Student Id
	 * @return student the Student
	 * @since Prototype 2
	 */
	public static Student findStudentbyId(Long id) {
		Student student = new Model.Finder<>(long.class, Student.class)
				.byId(id);
		return student;
	}

	/**
	 * Adds a new Test to TestComplete
	 * 
	 * @param test
	 *            the Test
	 * @since Prototype 2
	 */
	public void addTestTaken(Test test) {
		testComplete.add(test);
	}

}
