package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {

	// Home screen and login
	public static Result loginScreen() {
		List<Classroom> classes = new Model.Finder<>(long.class,
				Classroom.class).all();
		Form<Student> studentForm = form(Student.class);
		Form<Teacher> teacherForm = form(Teacher.class);
		List<Student> students = new Model.Finder<>(long.class, Student.class)
				.all();
		List<Teacher> teachers = new Model.Finder<>(long.class, Teacher.class)
				.all();
		return ok(login.render(classes, studentForm, teacherForm, students,
				teachers));
	}

	public static Result login(String name, String password) {
		List<Teacher> teachers = new Model.Finder<>(long.class, Teacher.class)
				.all();
		List<Student> students = new Model.Finder<>(long.class, Student.class)
				.all();
		for (Teacher teacher : teachers) {
			if (teacher.name.equals(name) || teacher.password.equals(password)) {
				return teacherHome(teacher.id);
			}
		}
		for (Student student : students) {
			if (student.name.equals(name) || student.password.equals(password)) {
				return studentHome(student.id);
			}
		}
		return teacherHome((long) 1);
	}

	public static Result studentHome(Long id) {
		List<Test> tests = new Model.Finder<>(long.class, Test.class).all();
		List<Classroom> classrooms = new Model.Finder<>(long.class,
				Classroom.class).all();
		Student student = new Model.Finder<>(long.class, Student.class)
				.byId(id);
		for (Classroom classroom : classrooms) {
			if (!classroom.students.contains(student)) {
				tests.removeAll(classroom.tests);
			} 
//			else {
//				classrooms.remove(classroom);
//			}
		}

		return ok(studentHome.render(tests, classrooms, id));
	}

	public static Result teacherHome(Long id) {
		Teacher teacher = new Model.Finder<>(long.class, Teacher.class)
				.byId(id);
		List<TestReview> tests = new Model.Finder<>(long.class,
				TestReview.class).all();
		List<Classroom> classes = new Model.Finder<>(long.class,
				Classroom.class).all();
		Form<Test> testForm = form(Test.class);
		Form<Classroom> classForm = form(Classroom.class);
		return ok(teacherHome.render(tests, classes, testForm, teacher));
	}

	// creating students and classrooms

	public static Result createClassroom(long id) {
		Classroom classroom = Form.form(Classroom.class).bindFromRequest()
				.get();
		Teacher teacher = new Model.Finder<>(long.class, Teacher.class)
				.byId(id);
		// breaks the program
		classroom.addTeacher(teacher);
		classroom.save();
		return teacherHome(id);
	}

	public static Result joinClassroom(long id, long classid) {
		Student student = new Model.Finder<>(long.class, Student.class)
				.byId(id);
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classid);	
		classroom.addStudent(student);
		classroom.save();
		return studentHome(id);
	}

	public static Result createTeacher() {
		Form<Teacher> teacherForm = form(Teacher.class).bindFromRequest();
		if (!teacherForm.hasErrors()) {
			teacherForm.get().save();
		}
		return loginScreen();
	}

	public static Result createStudent() {
		Form<Student> studentForm = form(Student.class).bindFromRequest();
		if (!studentForm.hasErrors()) {
			studentForm.get().save();
		}
		return loginScreen();
	}

	public static Result createTest(long classroomId) {
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classroomId);
		Test test = Form.form(Test.class).bindFromRequest().get();
		 classroom.addTest(test); // breaks the program
		classroom.save();
		return ok(createTest.render(test, classroom));
	}
	
	public static Result addQuestion(long classroomId) { // creates
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classroomId);
		Question question = Form.form(Question.class).bindFromRequest().get();
		Test test = classroom.tests.get(classroom.tests.size()-1);
		test.numQuestions++;
		test.addQuestion(question);
		test.save();
		return ok(createTest.render(test, classroom));
	}

	public static Result publishTest(long id, long classroomId) {
		Test test = new Model.Finder<>(long.class, Test.class).byId(id);
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classroomId);
		if (test.numQuestions <= 0) {
			test.delete();
			flash("notcreated", "your test has not been made");
		} else {
			flash("published", test.name);
			TestReview testReview = new TestReview();
			testReview.setTest(test);
			testReview.save();
		}
		return teacherHome(classroom.teacher.id); // test.classroom.teacher.id
	}



	public static Result beginTest(long testId, long studentId) {
		Test test = new Model.Finder<>(long.class, Test.class)
				.byId(testId);
		TestReview testReview = TestReview.findByTest(test);
		Student student = new Model.Finder<>(long.class, Student.class)
				.byId(studentId);
		TestAnswer testAnswer = new TestAnswer();
		testAnswer.setStudent(student);
		testAnswer.setTest(test);
		testReview.studentAnswers.add(testAnswer);
		testReview.save();
		return ok(takeTest.render(0, testAnswer));
	}

	public static Result markQuestion(int current, int answer, long id) {

		TestAnswer testAnswer = new Model.Finder<>(long.class, TestAnswer.class)
				.byId(id);

		// testAnswer.addAnswer(answer);

		if (testAnswer.test.questions.get(current).correctAnswer == answer) {
			flash("correct", "");
			testAnswer.markCorrect();
		} else {
			flash("wrong", Question.getAnswer(
					testAnswer.test.questions.get(current),
					testAnswer.test.questions.get(current).correctAnswer - 1));
		}
		testAnswer.save();
		if (testAnswer.test.numQuestions <= current + 1) { // no more questions
			testAnswer.save();
			return ok(testResult.render(testAnswer, testAnswer.student.id));
		}

		return ok(takeTest.render(++current, testAnswer));
	}

	public static Result reviewTest(long id) {
		TestReview testReview = new Model.Finder<>(long.class, TestReview.class)
				.byId(id);
		return ok(reviewTest.render(testReview));
	}

	// JSON USED FOR DEBUGGING

	public static Result getTests() {
		List<Test> tests = new Model.Finder<>(long.class, Test.class).all();
		return ok(Json.toJson(tests));
	}

	public static Result getTestAnswers() {
		List<TestAnswer> data = new Model.Finder<>(long.class, TestAnswer.class)
				.all();
		return ok(Json.toJson(data));
	}

	public static Result getQuestions() {
		List<Question> questions = new Model.Finder<>(long.class,
				Question.class).all();
		// Test test = new Model.Finder<>(long.class,Test.class).byId((long) 1);
		// List<Question> quest = Question.testQuestion(test);
		return ok(Json.toJson(questions));
	}

	public static Result getTestReview() {
		List<TestReview> testreview = new Model.Finder<>(long.class,
				TestReview.class).all();
		return ok(Json.toJson(testreview));
	}

	public static Result getStudents() {
		List<Student> students = new Model.Finder<>(long.class, Student.class)
				.all();
		return ok(Json.toJson(students));
	}

	public static Result getClassrooms() {
		List<Classroom> data = new Model.Finder<>(long.class, Classroom.class)
				.all();
		return ok(Json.toJson(data));
	}

	public static Result getTeachers() {
		List<Teacher> data = new Model.Finder<>(long.class, Teacher.class)
				.all();
		return ok(Json.toJson(data));
	}

}
