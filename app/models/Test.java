package models;

import javax.persistence.*;

import java.util.*;

import com.avaje.ebean.Page;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

/**
 * Test entity managed by Ebean
 */
@Entity
public class Test extends Model{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	@Constraints.Required
	public String name;
	public String testDesc;
	public int numQuestions;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Question> questions;
	
	
	
//	
//	@OneToMany
//	public List<Question> questions;
//	
	public void addQuestion(Question aQuestion){
		questions.add(aQuestion);
	}
//	


	public static Finder<Long,Test> find = new Finder<Long,Test>(Long.class, Test.class);
	


}
