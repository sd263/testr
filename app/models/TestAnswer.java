package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
@Entity
public class TestAnswer extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public long id;
	
	public int current;
	public Test test;	
	public static List<Question> questions;
	
	public TestAnswer(long testId){
		current = 0;
		test = findTestByID(testId);
		questions = Question.testQuestion(test);
	}
	
	public Test findTestByID(Long id){
		Test test = new Model.Finder<>(long.class,Test.class).byId(id);
		return test;
	}

	public  Question getQuestion(){
		return questions.get(current);
	}
	
}
