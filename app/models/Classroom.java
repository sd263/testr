package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


import models.Classroom;
import models.Student;

import play.db.ebean.Model;

/**
 * Classroom 
 */
@Entity
public class Classroom extends Model {

	private static final long serialVersionUID = 1L;
	@Id
	public Long cid;
	
	public String cname;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	/**
	 * List of all the students in classroom
	 */
	public List<Student> students;
			
	@OneToMany(cascade = {CascadeType.ALL})
	/**
	 * List of all tests in classroom
	 */
	public List<Test> tests;
	
    public static Model.Finder<Long,Classroom> find = new Model.Finder<Long,Classroom>(Long.class, Classroom.class);
	
	/**
	 * Renders the Classrooms in a list
	 * 
	 * @since Prototype 2
	 */
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Classroom c: Classroom.find.orderBy("cname").findList()) {
            options.put(c.cid.toString(), c.cname);
        }
        return options;
    }	
    
	/**
	 * Adds a new Test to Classroom list
	 * 
	 * @param aTest Test to add to Classroom
	 * @since Prototype 2
	 */
    public void addTest(Test aTest){
    	tests.add(aTest);
    }
    
	/**
	 * Adds a new Student to Classroom list
	 * 
	 * @param aStudent Student to add to Classroom
	 * @since Prototype 2
	 */
    public void addStudent(Student aStudent){
    	students.add(aStudent);
    }
    
	/**
	 * Returns the Classroom name
	 * 
	 * @param Classroom Id
	 * @return Classroom name
	 * @since Prototype 2
	 */
    public static String getClassNamebyId(Long id){
    	Classroom aClassroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(id);
		return aClassroom.cname;
    }

	/**
	 * Returns a list with all the classrooms <b>without</b> the Student in.
	 * 
	 * @param Student the Student
	 * @return classrooms lists of classrooms
	 * @since Prototype 2
	 */
	public static List<Classroom> getClassWithoutStudent(Student student) {
		List<Classroom>  classrooms = new Model.Finder<>(long.class,
				Classroom.class).all();
		for(int i = 0; i < classrooms.size() ; i++){
			Classroom classroom = classrooms.get(i);
			if(classroom.students.contains(student)){
				classrooms.remove(classroom);
				i--;
			}
		}
		return classrooms;
	}
	
	/**
	 * Returns a list with all the classrooms <b>with</b> the Student in.
	 * 
	 * @param Student the Student
	 * @return classrooms lists of classrooms
	 * @since Prototype 2
	 */
	public static List<Classroom> getClassWithStudent(Student student) {
		List<Classroom>  classrooms = new Model.Finder<>(long.class,
				Classroom.class).all();
		for(int i = 0; i < classrooms.size() ; i++){
			Classroom classroom = classrooms.get(i);
			if(!classroom.students.contains(student)){
				classrooms.remove(classroom);
				i--;
			}
		}
		return classrooms;
	}
	
	
    
}
