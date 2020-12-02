import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username, password;
	private String firstname;
	private String lastname;

    public User() {
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String fname) {
        firstname = fname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lname) {
        lastname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String uname) {
        username = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pword) {
        password = pword;
    }

    public void viewAllCourses(ArrayList<Course> courses) {
        for(int i=0; i<courses.size(); i++) {
            System.out.println((i+1) + ") " + courses.get(i).toString() + "\n");
            }
    }

}
