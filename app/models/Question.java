package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;


@Entity
public class Question extends Model {
	private static final long serialVersionUID = 1L;

	@Constraints.Required @Id
	public String questionText;
	
	public String answer1;
	public String answer2;
	public String answer3;
	public String answer4;

	@Constraints.Required
	@Constraints.Max(value = 4)
	@Constraints.Min(value = 1)
	public int correctAnswer;

	public static String getAnswer(Question question, int i) {
		if (i == 0)
			return question.answer1;
		else if (i == 1)
			return question.answer2;
		if (i == 2)
			return question.answer3;
		else
			return question.answer4;
	}
}
