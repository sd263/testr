package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	public Long id;

	@OneToOne
	public Test test;
	

	@ManyToMany(cascade = {CascadeType.ALL})
	public List<TestAnswer> studentAnswers;

	
	public static String getClassroomName(TestReview testReview){
		List<Classroom> classrooms = new Model.Finder<>(long.class, Classroom.class).all();
		for(Classroom classroom : classrooms){
			for(Test ctest : classroom.tests){
				if(ctest.equals(testReview.test)){
					return classroom.cname;
				}
			}
		}
		return "System error";
		
	}
	
	public void setTest(Test aTest){
		test = aTest;
	}

	public static TestReview findByTest(Test aTest) {
		List<TestReview> treviews = new Model.Finder<>(long.class, TestReview.class).all();
		for(TestReview tReview : treviews){
			if(tReview.test.equals(aTest))
				return tReview;
		}
		return treviews.get(100);
	}

	public void addTestAnswer(TestAnswer testAnswer) {
		studentAnswers.add(testAnswer);
	}

}
