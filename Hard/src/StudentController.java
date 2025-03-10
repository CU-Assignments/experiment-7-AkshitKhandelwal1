import model.Student;
import java.util.List;

public class StudentController {
    private StudentDAO studentDAO;

    public StudentController() {
        this.studentDAO = new StudentDAO();
    }

    public void addStudent(Student student) {
        studentDAO.addStudent(student);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    public Student getStudentById(int studentID) {
        return studentDAO.getStudentById(studentID);
    }

    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }

    public void deleteStudent(int studentID) {
        studentDAO.deleteStudent(studentID);
    }
}