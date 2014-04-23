package controllers;

import static play.data.Form.form;

import java.util.List;

import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;
import models.*;
import views.html.*;

/**
 * Application executes methods called at view level.
 */
public class Application extends Controller {

	/**
	 * Loads the login screen. Loads of domain name being entered.
	 * 
	 * @since prototype 2
	 * @return login screen
	 */
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

	/**
	 * Verfies if account exists. Logs in as either student, teacher, or returns
	 * incorrect login data error message.
	 * 
	 * @since prototype 2
	 * @return Student home screen
	 * @return Teacher home screen
	 * @throws Bad
	 *             Login error
	 */
	public static Result login() {
		Form<Student> studentLogin = form(Student.class).bindFromRequest();
		Form<Teacher> teacherLogin = form(Teacher.class).bindFromRequest();
		List<Teacher> teachers = new Model.Finder<>(long.class, Teacher.class)
				.all();
		List<Student> students = new Model.Finder<>(long.class, Student.class)
				.all();
		for (Teacher teacher : teachers) {
			if (teacher.name.equals(teacherLogin.get().name)
					&& teacher.password.equals(teacherLogin.get().password)) {
				return teacherHome(teacher.id);
			}
		}
		for (Student student : students) {
			if (student.name.equals(studentLogin.get().name)
					&& student.password.equals(studentLogin.get().password)) {
				return studentHome(student.id);
			}
		}
		flash("wrongLogin", "Your username or password is incorrect!");
		return loginScreen();
	}

	/**
	 * Loads the home screen of a Student
	 * 
	 * @since prototype 2
	 * @param id
	 *            The Student id
	 * @return Student home screen
	 */
	public static Result studentHome(Long id) {
		Student student = new Model.Finder<>(long.class, Student.class)
				.byId(id);
		List<Classroom> inCroom = Classroom.getClassWithStudent(student);
		List<Test> tests = Test.getTestsForStudent(inCroom, student);
		List<Classroom> outCroom = Classroom.getClassWithoutStudent(student);
		return ok(studentHome.render(inCroom, outCroom, tests, student));
	}

	/**
	 * Loads the home screen of a Teacher
	 * 
	 * @since prototype 2
	 * @param id
	 *            The Teacher id
	 * @return Teacher home screen
	 */
	public static Result teacherHome(Long id) {
		Teacher teacher = new Model.Finder<>(long.class, Teacher.class)
				.byId(id);
		List<Test> tests = Test.getTestForTeacher(teacher);
		Form<Test> testForm = form(Test.class);
		return ok(teacherHome.render(tests, testForm, teacher));
	}

	/**
	 * Creates a classroom. Adds to teachers list
	 * 
	 * @since prototype 2
	 * @param id
	 *            The Teacher id
	 * @return Teacher home screen
	 */
	public static Result createClassroom(long id) {
		Classroom classroom = Form.form(Classroom.class).bindFromRequest()
				.get();
		Teacher teacher = new Model.Finder<>(long.class, Teacher.class)
				.byId(id);
		teacher.addClassroom(classroom);
		teacher.save();
		return teacherHome(id);
	}

	/**
	 * Creates a Test to have questions be added to it.
	 * 
	 * @since Prototype 1
	 * @param teacherId
	 *            the id of the Teacher who is creating test
	 * @return Teacher home screen
	 * @throws No
	 *             test name error
	 */
	public static Result createTest(long teacherId) {
		Test test = Form.form(Test.class).bindFromRequest().get();
		test.openTest();
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(test.classId);
		classroom.addTest(test);
		if (test.name == "") {
			flash("notcreated", "your test has not been made");
			return teacherHome(teacherId);
		}
		classroom.save();
		return ok(createTest.render(test, classroom));
	}

	/**
	 * Joins a student to a classroom.
	 * 
	 * @since Prototype 2
	 * @param id
	 *            The Student id
	 * @param classId
	 *            The Classroom Id
	 * @return Student home screen
	 */
	public static Result joinClassroom(long id, long classId) {
		Student student = new Model.Finder<>(long.class, Student.class)
				.byId(id);
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classId);
		classroom.addStudent(student);
		classroom.save();
		return studentHome(id);
	}

	/**
	 * Creates a new Teacher.
	 * 
	 * @since Prototype 2
	 * @return Login screen
	 */
	public static Result createTeacher() {
		Form<Teacher> teacherForm = form(Teacher.class).bindFromRequest();
		if (!teacherForm.hasErrors()) {
			flash("accountCreated", "Teacher account created!");
			teacherForm.get().save();
		}
		return loginScreen();
	}

	/**
	 * Creates a new Student
	 * 
	 * @since Prototype 2
	 * @return Login screen
	 */
	public static Result createStudent() {
		Form<Student> studentForm = form(Student.class).bindFromRequest();
		if (!studentForm.hasErrors()) {
			flash("accountCreated", "Student account created!");
			studentForm.get().save();
		}
		return loginScreen();
	}

	/**
	 * Adds a new question to non published test.
	 * 
	 * @since Prototype 1
	 * @param classId
	 *            The Classroom Id
	 * @return Question creation screen
	 */
	public static Result addQuestion(long classId) { // creates
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classId);
		Question question = Form.form(Question.class).bindFromRequest().get();
		Test test = classroom.tests.get(classroom.tests.size() - 1);
		test.numQuestions++;
		test.addQuestion(question);
		test.save();
		return ok(createTest.render(test, classroom));
	}

	/**
	 * Publishes a test when the Teacher has created all questions.
	 * 
	 * @since Prototype 1
	 * @param id
	 *            The Test id
	 * @param classId
	 *            The Classroom Id
	 * @return Teacher home screen
	 * @throws no
	 *             questions error
	 */
	public static Result publishTest(long id, long classId) {
		Test test = new Model.Finder<>(long.class, Test.class).byId(id);
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classId);
		if (test.numQuestions <= 0) {
			test.delete();
			flash("notcreated", "your test has not been made");
		} else {
			flash("published", test.name);
		}
		Teacher teacher = Teacher.findTeacherByClass(classroom);
		return teacherHome(teacher.id);
	}

	/**
	 * Deletes a unwanted Test before being published.
	 * 
	 * @since Prototype 2
	 * @param id
	 *            The Test id
	 * @param classId
	 *            The Classroom Id
	 * @return Teacher home screen
	 */
	public static Result scrapTest(long id, long classroomId) {
		Test test = new Model.Finder<>(long.class, Test.class).byId(id);
		Classroom classroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(classroomId);
		test.delete();
		flash("notcreated", "your test has not been made");
		Teacher teacher = Teacher.findTeacherByClass(classroom);
		return teacherHome(teacher.id);
	}

	/**
	 * Student begins taking a Test.
	 * 
	 * @since Prototype 1
	 * @param studentId
	 *            The Student id
	 * @param testId
	 *            The Test id
	 * @return Take test screen
	 */
	public static Result beginTest(long studentId, long testId) {
		Test test = new Model.Finder<>(long.class, Test.class).byId(testId);
		Student student = Student.findStudentbyId(studentId);
		student.addTestTaken(test);
		student.save();
		TestAnswer testAnswer = new TestAnswer(student, test);
		testAnswer.save();
		return ok(takeTest.render(0, testAnswer));
	}

	/**
	 * Marks a Students answer and checks if any more questions in test.
	 * 
	 * @since Prototype 1
	 * @param current
	 *            the current postion in the test
	 * @param answer
	 *            the Student answer for the current question
	 * @param id
	 *            The Student id
	 * @return Take test screen
	 * @return Test result screen
	 */
	public static Result markQuestion(int current, int answer, long id) {

		TestAnswer testAnswer = new Model.Finder<>(long.class, TestAnswer.class)
				.byId(id);
		QuestionResponse qr = new QuestionResponse(answer);
		testAnswer.addAnswer(qr);

		if (testAnswer.test.questions.get(current).correctAnswer == answer) {
			flash("correct", "");
			testAnswer.markCorrect();
		} else { // incorrect answer
			flash("wrong", Question.getAnswer(
					testAnswer.test.questions.get(current),
					testAnswer.test.questions.get(current).correctAnswer - 1));
		}
		if (testAnswer.test.numQuestions <= current + 1) { // no more questions
			testAnswer.calculatePercentage();
			testAnswer.save();
			return ok(testResult.render(testAnswer, testAnswer.student.id));
		}
		testAnswer.save();
		return ok(takeTest.render(++current, testAnswer));
	}

	/**
	 * Allows a Teacher to review all the answers for a Test.
	 * 
	 * @since Prototype 2
	 * @param id
	 *            The Test Id
	 * @param teacherId
	 *            The Teacher Id
	 * @return Review test screen
	 */
	public static Result reviewTest(long id, long teacherId) {
		Test test = new Model.Finder<>(long.class, Test.class).byId(id);
		List<TestAnswer> answers = TestAnswer.getTAForTest(test);
		return ok(reviewTest.render(test, answers, teacherId));
	}

	
	/**
	 * Allows a Teacher to review a Student's response to a Test
	 * 
	 * @since Prototype 2
	 * @param id
	 *            The Test Id
	 * @param teacherId
	 *            The Teacher Id
	 * @return Review Student test screen
	 */
	public static Result reviewStudentTest(long id, long teacherId) {
		TestAnswer test = new Model.Finder<>(long.class, TestAnswer.class)
				.byId(id);
		return ok(reviewStudentTest.render(test, test.student, teacherId));
	}

	/**
	 * Prevents Test from showing any more in Teachers Test list
	 * 
	 * @since Prototype 2
	 * @param id
	 *            The Test Id
	 * @param teacherId
	 *            The Teacher Id
	 * @return Teacher home screen
	 */
	public static Result retireTest(long id, long teacherId) {
		Test test = new Model.Finder<>(long.class, Test.class).byId(id);
		flash("notcreated", "This test has been retired");
		test.retireTest();
		test.save();
		return teacherHome(teacherId);
	}

	// JSON USED FOR DEBUGGING
	// Would be removed in production build.

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
		return ok(Json.toJson(questions));
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
