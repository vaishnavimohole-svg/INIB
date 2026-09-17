import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private int id;
    private String name;
    private double math;
    private double science;
    private double computer;

    public Student(int id, String name, double math, double science, double computer) {
        this.id = id;
        this.name = name;
        this.math = math;
        this.science = science;
        this.computer = computer;
    }

    public int getId() {
        return id;
    }

    public double calculateAverage() {
        return (math + science + computer) / 3;
    }

    public String calculateGrade() {
        double average = calculateAverage();

        if (average >= 90)
            return "A+";
        else if (average >= 80)
            return "A";
        else if (average >= 70)
            return "B";
        else if (average >= 60)
            return "C";
        else if (average >= 50)
            return "D";
        else
            return "F";
    }

    public void update(String name, double math, double science, double computer) {
        this.name = name;
        this.math = math;
        this.science = science;
        this.computer = computer;
    }

    public void display() {
        System.out.println("-----------------------------------------------");
        System.out.println("Student ID   : " + id);
        System.out.println("Student Name : " + name);
        System.out.println("Math         : " + math);
        System.out.println("Science      : " + science);
        System.out.println("Computer     : " + computer);
        System.out.printf("Average      : %.2f%n", calculateAverage());
        System.out.println("Grade        : " + calculateGrade());
        System.out.println("-----------------------------------------------");
    }
}

public class StudentGradebook {

    static ArrayList<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        System.out.println("===============================================");
        System.out.println("          STUDENT GRADEBOOK SYSTEM");
        System.out.println("===============================================");

        do {
            displayMenu();

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    displayAllStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    editStudent();
                    break;

                case 5:
                    removeStudent();
                    break;

                case 6:
                    generateReport();
                    break;

                case 7:
                    System.out.println("\nThank you for using Student Gradebook!");
                    System.out.println("Program ended successfully.");
                    break;

                default:
                    System.out.println("\nInvalid choice. Please enter 1 to 7.");
            }

        } while (choice != 7);

        scanner.close();
    }

    // Display menu
    static void displayMenu() {

        System.out.println("\n--------------- MAIN MENU ----------------");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Edit Student");
        System.out.println("5. Remove Student");
        System.out.println("6. Generate Grade Report");
        System.out.println("7. Exit");
        System.out.println("------------------------------------------");
    }

    // Add student
    static void addStudent() {

        System.out.println("\n========== ADD STUDENT ==========");

        int id = readInt("Enter Student ID: ");

        if (findStudent(id) != null) {
            System.out.println("A student with this ID already exists.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        double math = readMarks("Enter Math marks (0-100): ");
        double science = readMarks("Enter Science marks (0-100): ");
        double computer = readMarks("Enter Computer marks (0-100): ");

        Student student = new Student(id, name, math, science, computer);

        students.add(student);

        System.out.println("\nStudent added successfully!");
    }

    // View all students
    static void displayAllStudents() {

        System.out.println("\n========== ALL STUDENTS ==========");

        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        for (Student student : students) {
            student.display();
        }
    }

    // Search student
    static void searchStudent() {

        System.out.println("\n========== SEARCH STUDENT ==========");

        int id = readInt("Enter Student ID: ");

        Student student = findStudent(id);

        if (student != null) {
            System.out.println("\nStudent found!");
            student.display();
        } else {
            System.out.println("Student not found.");
        }
    }

    // Edit student
    static void editStudent() {

        System.out.println("\n========== EDIT STUDENT ==========");

        int id = readInt("Enter Student ID to edit: ");

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter new Student Name: ");
        String name = scanner.nextLine();

        double math = readMarks("Enter new Math marks (0-100): ");
        double science = readMarks("Enter new Science marks (0-100): ");
        double computer = readMarks("Enter new Computer marks (0-100): ");

        student.update(name, math, science, computer);

        System.out.println("\nStudent record updated successfully!");
    }

    // Remove student
    static void removeStudent() {

        System.out.println("\n========== REMOVE STUDENT ==========");

        int id = readInt("Enter Student ID to remove: ");

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        students.remove(student);

        System.out.println("Student record removed successfully!");
    }

    // Generate grade report
    static void generateReport() {

        System.out.println("\n============== GRADE REPORT ==============");

        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        System.out.printf("%-10s %-20s %-12s %-10s%n",
                "ID", "NAME", "AVERAGE", "GRADE");

        System.out.println("-----------------------------------------------");

        for (Student student : students) {

            System.out.printf("%-10d %-20s %-12.2f %-10s%n",
                    student.getId(),
                    getStudentName(student),
                    student.calculateAverage(),
                    student.calculateGrade());
        }

        System.out.println("-----------------------------------------------");

        double classAverage = calculateClassAverage();

        System.out.printf("Class Average: %.2f%n", classAverage);

        System.out.println("===============================================");
    }

    // Find student by ID
    static Student findStudent(int id) {

        for (Student student : students) {

            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    // Calculate class average
    static double calculateClassAverage() {

        double total = 0;

        for (Student student : students) {
            total += student.calculateAverage();
        }

        return total / students.size();
    }

    // Get student name using display helper
    static String getStudentName(Student student) {

        try {
            java.lang.reflect.Field field =
                    Student.class.getDeclaredField("name");

            field.setAccessible(true);

            return (String) field.get(student);

        } catch (Exception e) {
            return "Unknown";
        }
    }

    // Read integer safely
    static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                int value = Integer.parseInt(scanner.nextLine());

                return value;

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Read marks with validation
    static double readMarks(String message) {

        while (true) {

            try {

                System.out.print(message);

                double marks = Double.parseDouble(scanner.nextLine());

                if (marks >= 0 && marks <= 100) {
                    return marks;
                }

                System.out.println("Marks must be between 0 and 100.");

            } catch (NumberFormatException e) {

                System.out.println("Please enter a valid number.");
            }
        }
    }
}