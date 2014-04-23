package models;

import javax.persistence.Entity;

import play.db.ebean.Model;

@Entity
public class QuestionResponse extends Model {

	/**
	 * Used to store Students answer of a Test
	 */
	private static final long serialVersionUID = 1L;
	

    public Integer answer;
	
	/**
	 * Creates a new Question Response
	 * 
	 * @param aAnswer the Integer to be stored
	 * @since Prototype 2
	 */
	public QuestionResponse(int aAnswer) {
		answer = aAnswer;
	}
	
	/**
	 * Returns The Integer value
	 * 
	 * @return classrooms lists of classrooms
	 * @since Prototype 2
	 */
	public int getAnswer(){
		return answer;
	}

}
