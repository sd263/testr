package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Teacher extends Model  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public Long id;

	@Constraints.Required
	public String name;
	
	@Constraints.Required
	public String password;
	
	@OneToMany(cascade = {CascadeType.ALL})
	public List<Classroom> classrooms;
	
	public void addClassroom(Classroom aClassroom){
		classrooms.add(aClassroom);
	}

	public static Teacher findTeacherByClass(Classroom classroom) {
		List<Teacher> teachers = new Model.Finder<>(long.class,
				Teacher.class).all();
		for(Teacher teacher: teachers){
			if(teacher.classrooms.contains(classroom))
				return teacher;
		}
		return teachers.get(0);
	}
	


}
