package models;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
@Entity
public class Question extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	public long id;

	@ManyToOne
	public Test test;

	@Constraints.Required
	public String questionText;
	public String answer1;
	public String answer2;
	public String answer3;
	public String answer4;
	
	@Constraints.Required
	@Constraints.Max(value = 4)
	@Constraints.Min(value = 1)
	public int correctAnswer;

    public static String getAnswer(Question question, int i){ // seems legit
    	if(i == 0) return question.answer1;
    	else if(i == 1) return question.answer2;
    	if(i == 2) return question.answer3;
    	else  return question.answer4;
    }

	public void assignTest(Test atest) {
		test = atest;
	}
	
	public static List<Question> testQuestion(Test atest){
		List<Question> allQuestion = new Model.Finder<>(long.class,Question.class).all();
		for(int i = 0; i < allQuestion.size();i++){
			if(allQuestion.get(i).test.id != atest.id){
				allQuestion.remove(i);
				i--;			// required because of how .remove() works
			}
		}
		return allQuestion;
		
	}
}
