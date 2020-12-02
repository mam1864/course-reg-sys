import java.io.*;
import java.util.ArrayList;


public class ImplAdmin extends User implements Admin {

    /**
     *
     */
    private static final long serialVersionUID = 3L;

    public ImplAdmin() {
    }

    @Override
    public void createNewCourse() {

    }

    @Override
    public void deleteCourse() {

    }

    @Override
    public void editCourse() {

    }

    @Override
    public void displayCourse() {

    }

    @Override
    public void regStudent() {

    }

    public void viewFullCourses(ArrayList<Course> courses) {
        for (int i = 0; i<courses.size(); i++) {
            if (courses.get(i).getMax()==courses.get(i).getCurrent()) {
                System.out.println((i+1) + ") " + courses.get(i).toString() + "\n");
            }
        }
    }

    @Override
    public void recordFullCourses(ArrayList<Course> courses) throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
        for(int i = 0; i<courses.size(); i++) {
            if (courses.get(i).getCurrent()>=courses.get(i).getMax()) {
                lines.add(courses.get(i).toString());
            }
        }

        BufferedWriter output = new BufferedWriter(new FileWriter("FullCourses.txt"));

        for(int j = 0; j<lines.size(); j++) {
            output.write(lines.get(j));
            output.newLine();
        }

        output.close();

    }

    @Override
    public void viewRegStudents(Course course) {
        for (int i = 0; i<course.getStudents().size(); i++) {
            System.out.println(course.getStudents().get(i).getFirstName() + " " +
            course.getStudents().get(i).getLastName() + " ID: " +
            course.getStudents().get(i).getID());
        }
    }

    @Override
    public void studentCourses() {

    }

    @Override
    public void sort(ArrayList<Course> courses) {
        int max = 0;
        for(int i = 0; i<courses.size(); i++) { //finds most students in any given class
            if (courses.get(i).getCurrent()>max) {
                max = courses.get(i).getCurrent();         
            }
        }
        while (max>=0) {
            for(int j = 0; j<courses.size(); j++) {
                if (courses.get(j).getCurrent()==max) {
                    System.out.println(courses.get(j).toString());
                }
            }
            max--;
        }
    }

	public void viewAllCourses(ArrayList<Course> courses) {
        for(int i=0; i<courses.size(); i++) {
            System.out.println((i+1) + ") " + courses.get(i).toString());
            for(int j=0; j<courses.get(i).getStudents().size(); j++) {
                System.out.println("\t" + (j+1) + ") " + courses.get(i).getStudents().get(j).getFirstName() + " " +
                courses.get(i).getStudents().get(j).getLastName() + " ID: " + 
                courses.get(i).getStudents().get(j).getID());
            }
            System.out.println();
        }
	}

}
