package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;

/**
 * A Question that is part of a Test.
 */
@Entity
public class Question extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	public long Id;
	@Constraints.Required 
	/**
	 * The text of the question
	 */
	public String questionText;
	
	public String answer1;
	public String answer2;
	public String answer3;
	public String answer4;

	@Constraints.Max(value = 4)
	@Constraints.Min(value = 1)
	/**
	 * Assigns the correct answer
	 */
	public int correctAnswer;

	/**
	 * Returns a answer of a Question
	 * 
	 * @param question the Question
	 * @param the Answer number
	 * @return the Question Answer
	 * @since Prototype 1
	 */
	public static String getAnswer(Question question, int i) {
		if (i == 1)
			return question.answer1;
		else if (i == 2)
			return question.answer2;
		if (i == 3)
			return question.answer3;
		else
			return question.answer4;
	}
}
