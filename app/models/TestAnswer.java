package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
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
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<QuestionResponse> answers; // saves what the student answers
	
	public int score;	// counts the total correctly answered questions
	
	public int percentage;
		
		
	public TestAnswer(Student aStudent, Test aTest) {
		student = aStudent;
		test = aTest;
	}
	
	public void addAnswer(QuestionResponse qr){
		answers.add(qr);
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
	

	public static String getClassAverageScore(List<TestAnswer> tests){
		float average = (float) 0.0;
		for(int i = 0 ; i < tests.size() ; i++){
			average += tests.get(i).score;
		}
		average = average / tests.size();
		String result = String.valueOf(average);
		if(result.length() > 4)
		return result.substring(0, 4);	// used to stop returning large decimal numbers
		else return result;
	}
	
	public static String getClassAveragePercentage(List<TestAnswer> tests){
		int average = 0;
		for(int i = 0 ; i < tests.size() ; i++){
			average += tests.get(i).percentage;
		}	
		average = average / tests.size();
		return String.valueOf(average);
	}
	
	public static String getQuestionName(int i , TestAnswer testAnswer){
		return testAnswer.test.questions.get(i-1).questionText;
	}
	
	public static String getStudentAnswer(int i , TestAnswer testAnswer ){
		return String.valueOf(Question.getAnswer(testAnswer.test.questions.get(i-1),testAnswer.answers.get(i-1).getAnswer()));
	}
	
	
	public static String getQuestionAnswer(int i , TestAnswer testAnswer){
		int n = testAnswer.test.questions.get(i-1).correctAnswer;
		return Question.getAnswer(testAnswer.test.questions.get(i-1), n);
	}

	public static List<TestAnswer> getTAForTest(Test aTest) {
		List<TestAnswer>  testAnswer = new Model.Finder<>(long.class,
				TestAnswer.class).all();
		for(int i = 0 ; i < testAnswer.size(); i++){
			if(!testAnswer.get(i).test.equals(aTest)){
				testAnswer.remove(i);
				i--;
			}
		}
		return testAnswer;
	}
	
}

