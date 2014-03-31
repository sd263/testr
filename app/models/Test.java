package models;

import javax.persistence.*;

import java.util.*;

import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * Test entity managed by Ebean
 */
@Entity
public class Test extends Model{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	@Constraints.Required
	public String name;
	public String testDesc;
	
	public int numQuestions;

	@OneToMany(cascade = {CascadeType.ALL})
	public List<Question> questions;

	public void addQuestion(Question aQuestion){
		questions.add(aQuestion);
	}
	
	public static List<Test> getTestsForStudent(List<Classroom> croom,Student student){
		// todo
		return null;
		
	}

	public static Finder<Long,Test> find = new Finder<Long,Test>(Long.class, Test.class);
}
