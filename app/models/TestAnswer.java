package models;

import java.util.ArrayList;
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
	
	public Student student;
			
	@ManyToOne
	public Test test;
	
	
	public List<Integer> questionAnswer; // saves what the student answers
	
	public int score;	// counts the total correctly answered questions
		
	public TestAnswer(int startPos, long testId){
		test = findTestByID(testId);
		score = 0;
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
	
}
