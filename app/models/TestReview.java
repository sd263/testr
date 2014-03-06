package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class TestReview extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	Long id;

	@OneToOne
	public Test test;

	@OneToMany(cascade = {CascadeType.ALL})
	public List<TestAnswer> studentAnswers;
	
	public TestReview(Test atest){
		test = atest;
	}
}
