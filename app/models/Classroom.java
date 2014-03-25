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
	public Long id;
	
	public String cname;
	
	@ManyToMany
	public List<Student> students;
		
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Test> tests;
	
	
    public static Model.Finder<Long,Classroom> find = new Model.Finder<Long,Classroom>(Long.class, Classroom.class);
	
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Classroom c: Classroom.find.orderBy("cname").findList()) {
            options.put(c.id.toString(), c.cname);
        }
        return options;
    }	
    
    public void addTest(Test aTest){
    	tests.add(aTest);
    }
    
    public void addTeacher(Teacher aTeacher){
    	teacher = aTeacher;
    }
    
    public void addStudent(Student aStudent){
    	students.add(aStudent);
    }
    
    public static teacher findTeacherByClassroom(Classroom aClassroom){
		List<Teacher> teachers = new Model.Finder<>(long.class,
				Teacher.class).all();
    }

}
