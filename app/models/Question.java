package models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Question extends Model{

	/**
	 * 
	 */
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
	
	public void assignTest(Test atest){
		test = atest;
	}
	
	}
