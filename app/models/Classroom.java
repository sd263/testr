package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	@Constraints.Required
	public String cname;
	
    public static Model.Finder<Long,Classroom> find = new Model.Finder<Long,Classroom>(Long.class, Classroom.class);
	
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Classroom c: Classroom.find.orderBy("cname").findList()) {
            options.put(c.id.toString(), c.cname);
        }
        return options;
    }
	
	

}
