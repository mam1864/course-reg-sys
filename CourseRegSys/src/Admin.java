import java.io.IOException;
import java.util.ArrayList;

public interface Admin {
    //Course Management
    public void createNewCourse();
    public void deleteCourse();
    public void editCourse();
    public void displayCourse();
    public void regStudent();
    //Reports
    public void recordFullCourses(ArrayList<Course> courses) throws IOException;
    public void viewRegStudents(Course course);
    public void studentCourses();
    public void sort(ArrayList<Course> courses);
    
}
