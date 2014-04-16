package models;

import javax.persistence.Entity;

import play.db.ebean.Model;

@Entity
public class QuestionResponse extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

    public Integer answer;
	
	public QuestionResponse(int aAnswer) {
		answer = aAnswer;
	}
	
	public int getAnswer(){
		return answer;
	}

}
