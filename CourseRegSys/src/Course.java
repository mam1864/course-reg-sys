import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {

    private static final long serialVersionUID = 4L;
    private String name, id, instructor, location;
    private int section, max_students, current_students;
    private ArrayList<ImplStudent> students = new ArrayList<ImplStudent>();

    public Course() {
    }

    //complete constructor if list of students
    public Course(String name, String id, int max_students, ArrayList<ImplStudent> students, String instructor, int section, String location) {
        this.name = name;
        this.id = id;
        this.section = section;
        this.instructor = instructor;
        this.location = location;
        this.max_students = max_students;
        for(int i=0; i<students.size(); i++) {
            this.students.add(students.get(i));
        }
        current_students = students.size();

    }

    //constructor if no list of students
    public Course(String name, String id, int max_students, String instructor, int section, String location) {
        this.name = name;
        this.id = id;
        this.section = section;
        this.instructor = instructor;
        this.location = location;
        this.max_students = max_students;
        current_students = 0;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int courseEdit) {
        this.section = courseEdit;
    }
    
    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMax() {
        return max_students;
    }

    public void setMax(int max) {
        this.max_students = max;
    }

    public int getCurrent() {
        return current_students;
    }

    public ArrayList<ImplStudent> getStudents() {
        return students;
    }

    public void addStudent(ImplStudent stu) {
        students.add(stu);
        current_students++;
    }

    public void removeStudent(int remove) {
        students.remove(remove);
        current_students--;
    }
    
    public Boolean equals(Course compare) {
    	if (this.id.equals(compare.getID()) && this.getSection() == compare.getSection()) {
    		return true;
    	}
    	return false;
    }

    public String toString() {
        return name + " (" + id + " Section: " + section + ") Enrollment: " + current_students
            + "/" + max_students + " Instructor: " + instructor + " Location: " + location;
    }
}
