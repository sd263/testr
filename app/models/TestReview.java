package models;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

public class TestReview extends Model {

	@OneToOne
	Test test;

	@OneToMany
	List<TestAnswer> studentAnswers;

	public void setStudentAnswers(Long id) {
		List<TestAnswer> allAnswers = new Model.Finder<>(long.class,
				TestAnswer.class).all();
		for (int i = 0; i < allAnswers.size(); i++) {
			if (allAnswers.get(i).test.id != test.id) {
				allAnswers.remove(i);
				i--; // required because of how .remove() works
			}
		}
		studentAnswers = allAnswers;
	}
}
