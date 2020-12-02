import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseRegSys {
    
    public static void main(String[] args) throws IOException {
        
        String line = "";
        ArrayList<Course> courses = new ArrayList<Course>();
        ArrayList<ImplStudent> students = new ArrayList<ImplStudent>();

        ImplAdmin admin = new ImplAdmin();
        admin.setUsername("Admin"); //for simplicity, we have one admin, so we establish it here
        admin.setPassword("Admin001");
        admin.setFirstName("Admin");
       
        

        //initial reading of the .csv.
        try {
            BufferedReader br = new BufferedReader(new FileReader("MyUniversityCourses.csv"));
            
            br.readLine(); //skips the first line (which is just labels)
            line = br.readLine();

            while (line != null) {
                String[] course_details = line.split(",");
                
                Course course = new Course(course_details[0], course_details[1], Integer.parseInt(course_details[2]),
                    course_details[5], Integer.parseInt(course_details[6]), course_details[7]);
                courses.add(course);

                line = br.readLine();
                }

            br.close();

        } catch (IOException e) {
            System.out.println("MyUniversityCourses.csv could not be found.");
        }
        
        //Deserialization of students and courses arraylists
        try {
            FileInputStream fileIn_stu = new FileInputStream("students.ser");
            FileInputStream fileIn_cou = new FileInputStream("courses.ser");
            ObjectInputStream in_stu = new ObjectInputStream(fileIn_stu);
            ObjectInputStream in_cou = new ObjectInputStream(fileIn_cou);
    
            students = (ArrayList) in_stu.readObject();
            courses = (ArrayList) in_cou.readObject();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        
        for(int i = 0; i<students.size(); i++) {
        	System.out.println(students.get(i).getFirstName());
        }
//       //____FILLS ONE OF THE CLASSES WITH STUDENTS - for testing writing full courses to a file
//        for(int i = 0; i<8; i++) {
//        	courses.get(0).addStudent(new ImplStudent());
//        }


        Scanner in = new Scanner(System.in);

        //determining user type
        System.out.println("Are you an Admin or a Student? (Type admin or student)\n");
        String usercheck = in.nextLine();

        while ((usercheck.equalsIgnoreCase("admin") || usercheck.equalsIgnoreCase("student"))==false) { //valid input check
            System.out.println("Invalid entry, try again.");
            usercheck = in.nextLine();
        }
        
        boolean validuserpass = false; //control for authentication loops
        String cont = "y"; //control for the main while loop
        int attempts = 5; //control for login attempts

        
        
        if (usercheck.equalsIgnoreCase("admin")) { //for admin
            ImplAdmin user = new ImplAdmin();

            while(!validuserpass && attempts>0) { //admin login
                System.out.println("Username: ");
                String user_attempt = in.nextLine();
                System.out.println("Password: ");
                String pass_attempt = in.nextLine();
                    if(user_attempt.equals(admin.getUsername()) && pass_attempt.equals(admin.getPassword())) {
                        validuserpass = true;
                        user = admin;
                    } else {
                        attempts--;
                        System.out.println("Invalid username and password. You have " + attempts + " attempts left.");
                    }
            }
            
            
            
            if(validuserpass) {
                while(!cont.equalsIgnoreCase("quit")) {
                    System.out.println("Admin: " + user.getFirstName());
                    System.out.println("What would you like to do?");
                    System.out.println("Reports: ");
                    System.out.println("1) View all courses\n" +
                        "2) View all courses that are full\n" +
                        "3) Write full courses to file\n" +
                        "4) View students registered to specific course\n" +
                        "5) View student's courses\n" +
                        "6) Sort courses by enrollment\n\n" +
                        "Course Management:\n" +
                        "7) Create a new course\n" +
                        "8) Delete a course\n" +
                        "9) Edit a course\n" +
                        "10) Display information on a given course (by ID)\n" +
                        "11) Register a student\n" + //as in to the database, not to a class
                        "0) Quit");
                    String actionkey = in.nextLine();

                    if (actionkey.equals("1")) {
                        user.viewAllCourses(courses);
                    }
                    else if (actionkey.equals("2")) {
                        user.viewFullCourses(courses);
                    }
                    else if (actionkey.equals("3")) {
                        user.recordFullCourses(courses);
                    }
                    else if (actionkey.equals("4")) {
                        System.out.println("Which course do you want to see?\n" +
                        "Enter corresponding number when viewing all courses");
                        int courseSelect = selectCourseForAction(courses);
                        for(int j=0; j<courses.get(courseSelect).getStudents().size(); j++) { //iterate through and print students in a course
                            System.out.println("\t" + (j+1) + ") " + courses.get(courseSelect).getStudents().get(j).getFirstName() + " " +
                            courses.get(courseSelect).getStudents().get(j).getLastName() + " ID: " + 
                            courses.get(courseSelect).getStudents().get(j).getID());
                        }

                    }
                    else if (actionkey.equals("5")) {
                        System.out.println("Enter the student you wish to see. (type the ID)");
                        String studentSelect = in.nextLine();
                        for(int i = 0; i<students.size(); i++) {
                            if (students.get(i).getID().equals(studentSelect)) {
                                students.get(i).viewRegisteredCourses();
                            }
                        }
                    }
                    else if (actionkey.equals("6")) {
                        user.sort(courses);
                    }
                    else if (actionkey.equals("7")) {
                        courses.add(createNewCourse());                        
                        
                    }
                    else if (actionkey.equals("8")) {
                        System.out.println("Which course do you want to delete?\n" +
                        "Enter corresponding number from when viewing all courses");
                        int courseSelect = selectCourseForAction(courses);
                        System.out.println("Removing " + courses.get(courseSelect).getName());
                        for(int i = 0; i<students.size();i++) { //update students' courses
                        	for(int j = 0; j<students.get(i).getCourses().size(); j++) {
                        		if(students.get(i).getCourses().get(j).equals(courses.get(courseSelect))) {
                        			students.get(i).getCourses().remove(j);
                        		}
                        	}
                        }
                        courses.remove(courseSelect); //update courses

                        }
                    else if (actionkey.equals("9")) {
                        System.out.println("Which course do you want to edit?\n" +
                        "Enter corresponding number from when viewing all courses.");
                        int courseSelect = selectCourseForAction(courses);
                        System.out.println("What do you want to change?\n" +
                        "1) Section number\n2) Max students\n3) Location\n4) Instructor");
                        int changeSelect = selectCourseParameterForAction();
                        editCourseParameter(courseSelect, changeSelect, courses);
                        System.out.println("Course details updated:\n" + courses.get(courseSelect).toString());
                        for(int i = 0; i<students.size();i++) { //update students' courses
                        	for(int j = 0; j<students.get(i).getCourses().size(); j++) {
                        		if(students.get(i).getCourses().get(j).equals(courses.get(courseSelect))) {
                        			students.get(i).getCourses().set(j, courses.get(courseSelect));
                        		}
                        	}
                        }
                    }
                    else if (actionkey.equals("10")) {
                        System.out.println("Enter course ID");
                        String courseSelect = in.nextLine();
                        int cselect2 = -1;
                        for (int i = 0; i<courses.size(); i++) {
                            if (courseSelect.equalsIgnoreCase(courses.get(i).getID())) {
                                cselect2 = i;
                            }
                        }
                        if(cselect2>-1) {
                            System.out.println(courses.get(cselect2).toString());
                        }
                    }
                    else if (actionkey.equals("11")) {
                        System.out.println("Enter student first name");
                        String firstname = in.nextLine();
                        System.out.println("Enter student last name");
                        String lastname = in.nextLine();
                        System.out.println("Enter student ID");
                        String id = in.nextLine();
                        System.out.println("Enter student username");
                        String username = in.nextLine();
                        System.out.println("Enter student password");
                        String password = in.nextLine();
                        ImplStudent newStu = new ImplStudent(firstname, lastname, id, username, password); 
                        students.add(newStu);
                    }

                    else if (actionkey.equals("0")) {
                        System.out.println("Good bye!");
                        cont = "quit";
                    }
                    
                }
            }
        }
        
        if (usercheck.equalsIgnoreCase("student")) { //for students
            ImplStudent user = new ImplStudent();
            while(!validuserpass && attempts>0) {  //student login
                System.out.println("Username: ");
                String user_attempt = in.nextLine();
                System.out.println("Password: ");
                String pass_attempt = in.nextLine();
                    for(int i = 0;i<students.size();i++) {
                        if(students.get(i).getUsername().equals(user_attempt) && students.get(i).getPassword().equals(pass_attempt)) {
                            validuserpass = true;
                            user = students.get(i); //user now refers to the student whose credentials were entered                  
                        }
                    }
                if (!validuserpass) {
                    attempts--;
                    System.out.println("Invalid username and password. You have " + attempts + " attempts left.");  
                }
            }
            
            if (validuserpass) {
                while(!cont.equalsIgnoreCase("quit")) {
                    System.out.println("Student: " + user.getFirstName());
                    System.out.println("What would you like to do?");
                    System.out.println("1) View all courses\n" +
                        "2) View all courses that are not full\n" +
                        "3) Register in a course\n" +
                        "4) Withdraw from a course\n" +
                        "5) View my registered courses\n" +
                        "6) Change username\n" +
                        "7) Change password\n" +
                        "8) Exit");
                    String actionkey = in.nextLine();
                    if (actionkey.equals("1")) {
                        user.viewAllCourses(courses);
                    }
                    else if (actionkey.equals("2")) {
                        user.viewNotFullCourses(courses);
                    }
                    else if (actionkey.equals("3")) { 
                        System.out.println("Which course do you want to register in?\n" +
                            "Enter the corresponding number when viewing ALL courses");
                        int courseSelect = selectCourseForAction(courses);
                        if (courses.get(courseSelect).getStudents().isEmpty()) {
                        	user.register(courses.get(courseSelect));
                        	courses.get(courseSelect).addStudent(user);
                        	System.out.println("Adding " + courses.get(courseSelect).getName() + " to your schedule.");
                        }
                        else {
                        	for (int i = 0; i<courses.get(courseSelect).getStudents().size(); i++) {
	                        	if (courses.get(courseSelect).getStudents().get(i).equals(user)) { //finds course you picked and makes sure user isn't already enrolled
	                        		System.out.println("Invalid course entry. Already registered in this course.");
	                        	} else {
	                        		user.register(courses.get(courseSelect));
	                        		courses.get(courseSelect).addStudent(user);
	                        	}
	                    	}
                        }
                    }
                    else if (actionkey.equals("4")) {
                        System.out.println("Which course would you like to remove? (Enter number corresponding to course)");
                        user.viewRegisteredCourses();
                        int courseRemove = in.nextInt() - 1;
                        while (courseRemove<1 && courseRemove>user.getCourses().size()) {
                        	System.out.println("Invalid entry, enter a number from 1 to " + user.getCourses().size());
                        	courseRemove = in.nextInt() - 1;
                        }
                        in.nextLine();
                        for (int i = 0; i<courses.size(); i++) {
                        	if (courses.get(i).equals(user.getCourses().get(courseRemove))) {
                        		user.getCourses().remove(courseRemove);
                        		for (int j = 0; j<courses.get(i).getStudents().size(); j++) {
                        			if (courses.get(i).getStudents().get(j).equals(user)) {
                        				courses.get(i).removeStudent(j);
                        			}
                        		}
                        	}
                    	}
                        user.withdraw(courses.get(courseRemove));
                    }
                    else if (actionkey.equals("5")) {
                        user.viewRegisteredCourses();
                    }
                    else if (actionkey.equals("6")) {
                        System.out.println("Enter new username");
                        String newU = in.nextLine();
                        user.setUsername(newU);
                        System.out.println("Your new username is: " + user.getUsername());
                    }

                    else if (actionkey.equals("7")) {
                        System.out.println("Enter new password");
                        String newP = "";
                        String newPConfirm = "/";
                        while (!newP.equals(newPConfirm)) {
                            newP = in.nextLine();
                            System.out.println("Confirm your password");
                            newPConfirm = in.nextLine();
                            if(!newP.equals(newPConfirm)) {
                                System.out.println("Passwords did not match.\nEnter a new password.");
                            }
                        }
                    }

                    else if (actionkey.equals("8")) {
                        System.out.println("Good bye!");
                        cont = "quit";
                    }
                }
            }


        

        }
        //Serialization
        try {
            FileOutputStream fileOut_stu = new FileOutputStream("students.ser");
            FileOutputStream fileOut_cou = new FileOutputStream("courses.ser");
            ObjectOutputStream out_stu = new ObjectOutputStream(fileOut_stu);
            ObjectOutputStream out_cou = new ObjectOutputStream(fileOut_cou);
            out_stu.writeObject(students);
            out_cou.writeObject(courses);
            fileOut_stu.close();
            fileOut_cou.close();

        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        in.close();

    }
    
    //Some methods that helped enhance readability
    private static Course createNewCourse() {
    	Scanner in = new Scanner(System.in);
        System.out.println("Creating new course: ");
        System.out.println("Enter course name: ");
        String courseName = in.nextLine();
        System.out.println("Enter course ID: ");
        String courseID = in.nextLine();
        System.out.println("Enter course Section: ");
        int courseSection = in.nextInt();
        in.nextLine();
        System.out.println("Enter course max enrollment: ");
        int courseMax = in.nextInt();
        in.nextLine();
        System.out.println("Enter course instructor: ");
        String courseInstructor = in.nextLine();
        System.out.println("Enter course location: ");
        String courseLocation = in.nextLine();
        Course out = new Course(courseName, courseID, courseMax, courseInstructor, courseSection, courseLocation);
        return out;
    }
    
    private static int selectCourseForAction(ArrayList<Course> courses) {
    	Scanner in = new Scanner(System.in);
        int courseSelect = in.nextInt() - 1;
        while(courseSelect>=courses.size() || courseSelect<0) { //checks for valid entry
            System.out.println("Invalid entry. Enter a value between 1 and " + courses.size());
            courseSelect = in.nextInt() - 1;
            in.nextLine();
        }
        return courseSelect;
    }
    
    private static int selectCourseParameterForAction() {
    	Scanner in = new Scanner(System.in);
    	int changeSelect = in.nextInt();
        in.nextLine();
        while(changeSelect>=5 || changeSelect<1) { //checks for valid entry
            System.out.println("Invalid entry. Enter a value between 1 and 4");
            changeSelect = in.nextInt();
            in.nextLine();
        }
        return changeSelect;
    }
    
    private static void editCourseParameter(int courseSelect, int changeSelect, ArrayList<Course> courses) {
    	Scanner in = new Scanner(System.in);
        if(changeSelect == 1) {
            System.out.println("Enter section number.");
            int courseEdit = in.nextInt();
            in.nextLine();
            courses.get(courseSelect).setSection(courseEdit);
        }
        else if (changeSelect == 2) {
            System.out.println("Enter new maximum students.");
            int courseEdit = in.nextInt();
            in.nextLine();
            courses.get(courseSelect).setMax(courseEdit);
        }
        else if (changeSelect == 3) {
            System.out.println("Enter new location.");
            String courseEdit = in.nextLine();
            courses.get(courseSelect).setLocation(courseEdit);
        }

        else if (changeSelect == 4) {
            System.out.print("Enter new instructor: ");
            String courseEdit = in.nextLine();
            courses.get(courseSelect).setInstructor(courseEdit);
        }
    }
    
    
    
}
