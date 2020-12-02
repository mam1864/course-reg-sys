import java.util.ArrayList;

public interface Student {
    //Course Management
    public void viewNotFullCourses(ArrayList<Course> courses);
    public void register(Course course);
    public void withdraw(int courseRemove);
    public void viewRegisteredCourses();
    
    
}
