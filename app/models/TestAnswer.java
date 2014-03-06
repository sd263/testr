package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
	
	public List<Integer> questionAnswer; // saves what the student answers
	
	public int correctAnswers;	// counts the total correctly answered questions
	
//	public ArrayList<Integer> studentAnswer;
	
	public TestAnswer(int startPos, long testId){
		current = startPos;
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
