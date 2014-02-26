package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
	
	@ManyToOne
	public Test test;	
	
	@ManyToMany
	public List<Question> questions;
	
	public int correctAnswers;
	
//	public ArrayList<Integer> studentAnswer;
	
	public TestAnswer(int current, long testId){
		this.current = current;
		test = findTestByID(testId);
		questions = Question.testQuestion(test);
//		studentAnswer = new ArrayList<Integer>();
	}
	
	public Test findTestByID(Long id){
		Test test = new Model.Finder<>(long.class,Test.class).byId(id);
		return test;
	}

	public  Question getQuestion(){
//		if(test.numQuestions <= current)
//			return null;
//		else 
			return questions.get(current);
	}
	
}
