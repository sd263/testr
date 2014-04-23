package models;

import javax.persistence.*;

import java.util.*;

import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * 
 */
@Entity
public class Test extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	@Constraints.Required
	public Long classId;
	public boolean retired;
	public String name;
	public String testDesc;

	public int numQuestions;

	@OneToMany(cascade = { CascadeType.ALL })
	public List<Question> questions;

	/**
	 * Adds new Question to the test
	 * 
	 * @param aQuestion the Question to add
	 * @since Prototype 1
	 */
	public void addQuestion(Question aQuestion) {
		questions.add(aQuestion);
	}
	
	/**
	 * Sets the test to be ready
	 * 
	 * @since Prototype 2
	 */
	public void openTest(){
		retired = false;
	}
	
	/**
	 * Sets the test to be retired 
	 * 
	 * @since Prototype 2
	 */
	public void retireTest(){
		retired = true;
	}

	/**
	 * Returns all the Tests available to take for a Student
	 * 
	 * @param croom List of Classrooms Student is in.
	 * @param student the Student
	 * @return testLIst lists of Tests
	 * @since Prototype 2
	 */
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
	
	/**
	 * Returns all the Tests available to review for Teacher
	 * 
	 * @param teacher
	 * @return testLIst lists of Tests
	 * @since Prototype 2
	 */
	public static List<Test> getTestForTeacher(Teacher teacher) {
		List<Test> testList = new Model.Finder<>(long.class, Test.class).all();
		for(int i = 0 ; i < testList.size() ; i++){
			Long cId = testList.get(i).classId;
			Classroom aClass = new Model.Finder<>(long.class, Classroom.class)
					.byId(cId);
			if(!teacher.classrooms.contains(aClass))
			 testList.remove(testList.get(i));
			else if(testList.get(i).retired == true){
				testList.remove(testList.get(i));
				i--;
			}
			
		}
		return testList;
	}
	
	/**
	 * Returns the name of classroom based on a Test
	 * 
	 * @param aTest
	 * @return Classroom Name
	 * @since Prototype 2
	 */
	public static String getClassroomName(Test aTest){
		Classroom aClass = new Model.Finder<>(long.class, Classroom.class)
				.byId(aTest.classId);
		return aClass.cname;
	}	

}
