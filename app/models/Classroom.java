package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import models.Classroom;
import models.Student;
import models.Teacher;


import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Classroom extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	public Long cid;
	
	public String cname;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	public List<Student> students;
			
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Test> tests;
	
	
    public static Model.Finder<Long,Classroom> find = new Model.Finder<Long,Classroom>(Long.class, Classroom.class);
	
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Classroom c: Classroom.find.orderBy("cname").findList()) {
            options.put(c.cid.toString(), c.cname);
        }
        return options;
    }	
    
    public void addTest(Test aTest){
    	tests.add(aTest);
    }
    
    public void addStudent(Student aStudent){
    	students.add(aStudent);
    }
    
    public static String getClassNamebyId(Long id){
    	Classroom aClassroom = new Model.Finder<>(long.class, Classroom.class)
				.byId(id);
		return aClassroom.cname;
    }

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
