import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    public StudentDAO() {
        connection = util.DatabaseConnection.getConnection();
    }

    public void addStudent(model.Student student) {
        String sql = "INSERT INTO Students (Name, Department, Marks) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getDepartment());
            statement.setDouble(3, student.getMarks());
            statement.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    public List<model.Student> getAllStudents() {
        List<model.Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int studentID = resultSet.getInt("StudentID");
                String name = resultSet.getString("Name");
                String department = resultSet.getString("Department");
                double marks = resultSet.getDouble("Marks");
                students.add(new model.Student(studentID, name, department, marks));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }
        return students;
    }

    public model.Student getStudentById(int studentID) {
        String sql = "SELECT * FROM Students WHERE StudentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet .next()) {
                String name = resultSet.getString("Name");
                String department = resultSet.getString("Department");
                double marks = resultSet.getDouble("Marks");
                return new model.Student(studentID, name, department, marks);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving student: " + e.getMessage());
        }
        return null;
    }

    public void updateStudent(model.Student student) {
        String sql = "UPDATE Students SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getDepartment());
            statement.setDouble(3, student.getMarks());
            statement.setInt(4, student.getStudentID());
            statement.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
    }

    public void deleteStudent(int studentID) {
        String sql = "DELETE FROM Students WHERE StudentID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentID);
            statement.executeUpdate();
            System.out.println("Student deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }
}
