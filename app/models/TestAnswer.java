package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class TestAnswer extends Model {

	/**
	 * A students response to a Test.
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
	
	
	/**
	 * Creates a new TestAnswer
	 * 
	 * @param aStudent Student taking test
	 * @param aTest Test being taken
	 * @since Prototype 2
	 */
	public TestAnswer(Student aStudent, Test aTest) {
		student = aStudent;
		test = aTest;
	}
	
	/**
	 * Adds a QuestionResponse to TestAnswer
	 * 
	 * @param qr
	 * @since Prototype 2
	 */
	public void addAnswer(QuestionResponse qr){
		answers.add(qr);
	}

	/**
	 * Returns the current Question to Answer
	 * 
	 * @param current the position in the Test
	 * @since Prototype 2
	 */
	public  Question getQuestion(int current){
		return test.questions.get(current);
	}
	
	/**
	 * Marks correct answer
	 * 
	 * @since Prototype 2
	 */
	public void markCorrect(){
		score++;
	}
	
	/**
	 * Called at end of TestAnswer. Calcuates percentage score
	 * 
	 * @since Prototype 2
	 */
	public void calculatePercentage(){
		percentage =(int) (score * 100 / test.numQuestions + 0.5);
	}	

	/**
	 * Called in TestReview. Calculates the class average score for a Test.
	 * 
	 * @param tests Lists of all TestAnswers for Test
	 * @since Prototype 2
	 */
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
	
	/**
	 * Called in TestReview. Calculates the class average percentage for a Test.
	 * 
	 * @param tests Lists of all TestAnswers for Test
	 * @since Prototype 2
	 */
	public static String getClassAveragePercentage(List<TestAnswer> tests){
		int average = 0;
		for(int i = 0 ; i < tests.size() ; i++){
			average += tests.get(i).percentage;
		}	
		average = average / tests.size();
		return String.valueOf(average);
	}
	
	/**
	 * Returns the text for name of Question in TestAnswer
	 * 
	 * @param questionNum The question that is being looked for.
	 * @param testAnswer the TestAnswer to search for.
	 * 
	 * @since Prototype 2
	 */
	public static String getQuestionName(int questionNum , TestAnswer testAnswer){
		return testAnswer.test.questions.get(questionNum-1).questionText;
	}
	
	/**
	 * Returns the text for answer of the Student in TestAnswer
	 * 
	 * @param questionNum The question that is being looked for.
	 * @param testAnswer the TestAnswer to search for.
	 * 
	 * @since Prototype 2
	 */
	public static String getStudentAnswer(int i , TestAnswer testAnswer ){
		return String.valueOf(Question.getAnswer(testAnswer.test.questions.get(i-1),testAnswer.answers.get(i-1).getAnswer()));
	}
	
	/**
	 * Returns the text for the correct answer in TestAnswer
	 * 
	 * @param questionNum The question that is being looked for.
	 * @param testAnswer the TestAnswer to search for.
	 * 
	 * @since Prototype 2
	 */
	public static String getQuestionAnswer(int i , TestAnswer testAnswer){
		int n = testAnswer.test.questions.get(i-1).correctAnswer;
		return Question.getAnswer(testAnswer.test.questions.get(i-1), n);
	}

	/**
	 * Returns all the TestAnswers for a Test.
	 * 
	 * @param aTest the Test
	 * @param testAnswer the TestAnswer to search for.
	 * @return testAnswer list of TestAnswers
	 * @since Prototype 2
	 */
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

