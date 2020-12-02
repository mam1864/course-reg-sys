import java.io.Serializable;
import java.util.ArrayList;

public class ImplStudent extends User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2L;

    private String id;

    private ArrayList<Course> courses = new ArrayList<Course>();
    public ImplStudent() {
    }
    
    public ImplStudent(String firstname, String lastname, String id, String username, String password) {
    	this.setFirstName(firstname);
    	this.setLastName(lastname);
    	this.setId(id);
    	this.setUsername(username);
    	this.setPassword(password);
    }

    public String getID() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void viewNotFullCourses(ArrayList<Course> courses) {
        for (int i = 0; i<courses.size(); i++) {
            if (courses.get(i).getMax()>courses.get(i).getCurrent()) {
                System.out.println(courses.get(i).toString() + "\n");
            }
        }

    }

    public void register(Course course) {
    	courses.add(course);
    }

    public void withdraw(Course course) {
    	courses.remove(course);   	
    }
    
    public Boolean equals(ImplStudent compare) {
    	if(this.id.equals(compare.getID())) {
    		return true;
    	}
    	return false;
    }

    public void viewRegisteredCourses() {
        if(courses.isEmpty()) {
            System.out.println("No courses registered");
        }
        else {
            System.out.println("-----Registered Courses-----");
            for (int i = 0; i<courses.size(); i++) {
                System.out.println(i+1 + ") " + courses.get(i).toString());
            }
        }
    }
    
}
