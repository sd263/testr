package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.Form;
import play.db.ebean.Model;

@Entity
public class TestAnswer extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public long id;
	
	@OneToOne
	public Student student;
			
	@ManyToOne
	public Test test;
	
//	@OneToMany
//	public List<Integer> questionAnswer; // saves what the student answers
	
	public int score;	// counts the total correctly answered questions
	
	public int percentage;
		

		
	public TestAnswer(Student aStudent, Test aTest) {
		student = aStudent;
		test = aTest;
	}

	public Student findStudentbyId(Long id) {
		Student student = new Model.Finder<>(long.class, Student.class)
				.byId(id);
		return student;
	}
	
	public Test findTestByID(Long id){
		Test test = new Model.Finder<>(long.class,Test.class).byId(id);
		return test;
	}

	public  Question getQuestion(int current){
			return test.questions.get(current);
	}
	
	public void markCorrect(){
		score++;
	}
	
	public void calculatePercentage(){
		percentage =(int) (score *100 / test.numQuestions + 0.5);
	}
	// http://stackoverflow.com/questions/10415531/overflow-safe-way-to-calculate-percentage-from-two-longs-in-java
	
	public void setStudent(Student aStudent){
		student = aStudent;
	}
	
	public void setTest(Test aTest){
		test = aTest;
	}

	
}

