import java.util.List;
import java.util.Scanner;
import model.Student;
import controller.StudentController;

public class StudentView {
    private StudentController controller;
    private Scanner scanner;

    public StudentView(StudentController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Find Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.println("======================================");
            System.out.print("Enter your choice: ");

            int choice = getIntInput("Enter Student ID to update: ");

            switch (choice) {
                case 1:
                    addStudentView();
                    break;
                case 2:
                    viewAllStudentsView();
                    break;
                case 3:
                    findStudentView();
                    break;
                case 4:
                    updateStudentView();
                    break;
                case 5:
                    deleteStudentView();
                    break;
                case 6:
                    System .out.println("Exiting application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addStudentView() {
        System.out.println("\n--- ADD NEW STUDENT ---");
        String name = getStringInput("Enter Student Name: ");
        String department = getStringInput("Enter Department: ");
        double marks = getDoubleInput("Enter Marks: ");

        Student student = new Student(name, department, marks);
        controller.addStudent(student);
    }

    private void viewAllStudentsView() {
        System.out.println("\n--- VIEW ALL STUDENTS ---");
        List<Student> students = controller.getAllStudents();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void findStudentView() {
        System.out.println("\n--- FIND STUDENT BY ID ---");
        int studentID = getIntInput("Enter Student ID: ");
        Student student = controller.getStudentById(studentID);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private void updateStudentView() {
        System.out.println("\n--- UPDATE STUDENT ---");
        int studentID = getIntInput("Enter Student ID to update: ");
        String name = getStringInput("Enter new Student Name: ");
        String department = getStringInput("Enter new Department: ");
        double marks = getDoubleInput("Enter new Marks: ");

        Student student = new Student(studentID, name, department, marks);
        controller.updateStudent(student);
    }

    private void deleteStudentView() {
        System.out.println("\n--- DELETE STUDENT ---");
        int studentID = getIntInput("Enter Student ID to delete: ");
        controller.deleteStudent(studentID);
    }

    private int getIntInput(String s) {
        return scanner.nextInt();
    }

    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextDouble();
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }
}