package models;

import javax.persistence.*;

import java.util.*;

import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * Test entity managed by Ebean
 */
@Entity
public class Test extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	@Constraints.Required
	public Long classId;
	public String name;
	public String testDesc;

	public int numQuestions;

	@OneToMany(cascade = { CascadeType.ALL })
	public List<Question> questions;

	public void addQuestion(Question aQuestion) {
		questions.add(aQuestion);
	}

	public static List<Test> getTestsForStudent(List<Classroom> croom, Student student) {
		List<Classroom> allClass = new Model.Finder<>(long.class, Classroom.class).all();
		List<Test> testList = new Model.Finder<>(long.class, Test.class).all();
		for(int i = 0 ; i < allClass.size() ; i++){
			if(!allClass.get(i).students.contains(student)){
				testList.removeAll(allClass.get(i).tests);
			} else {
				for(int n = 0 ; n < allClass.get(i).tests.size() ; n++){
					if(student.testComplete.contains(allClass.get(i).tests.get(n)))
						testList.remove(allClass.get(i).tests.get(n));
				}
			}
		}
		return testList;

	}

	public static Finder<Long, Test> find = new Finder<Long, Test>(Long.class,
			Test.class);
}
