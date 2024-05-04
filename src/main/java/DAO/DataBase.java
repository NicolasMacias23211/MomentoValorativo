package DAO;

import DataModels.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase{

    private String user;
    private String password;
    private int port;
    private String host;
    private String nameDatabase;
    private Connection cnn;
    
    public DataBase(){
        user = "root";
        password = "admin";
        port = 3306;
        host = "127.0.0.1";
        nameDatabase = "school";
   }
    
  private boolean createConexion() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.cnn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.nameDatabase + "?useSSL=false", this.user, this.password);
        System.out.println("Successful connection");
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("An error occurred during the connection");
        return false;
    }
}

    public List<Student> getStudents() throws SQLException {
    createConexion();
    String sql = "SELECT document, name, lastname, age FROM Students";
    List<Student> list = new ArrayList<>();
    try {
        Statement stmt = cnn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            Student stu = new Student();
            stu.setDocument(result.getInt("document"));
            stu.setName(result.getString("name"));
            stu.setLastname(result.getString("lastname"));
            stu.setAge(result.getInt("age"));
            list.add(stu);
        }
        stmt.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } finally {
       if (cnn != null) {
            cnn.close();
        }
    }
    return list;
}
    
    public List<Map<String, Object>> getCursesByEstudenDocument(int studentDocument) throws SQLException{
        List<Map<String, Object>> courses = new ArrayList<>();
        createConexion();
        String sql = "SELECT c.* FROM courses c JOIN studentsXCurses sxc ON c.courseCode = sxc.courses_courseCode WHERE sxc.students_document = ?;";
        try{
            PreparedStatement stmt = cnn.prepareStatement(sql);
            stmt.setInt(1, studentDocument);
            try{
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Map<String, Object> courseData = new HashMap<>();
                    courseData.put("courseCode", rs.getInt("courseCode"));
                    courseData.put("courseName", rs.getString("courseName"));
                    courseData.put("courseDescription", rs.getString("couserDescription"));
                    courses.add(courseData);
                }
            }catch(SQLException e) {
            e.printStackTrace();
            }
        } catch (SQLException e) {
        e.printStackTrace();
    }finally {
       if (cnn != null) {
            cnn.close();
        }
    }
    return courses;
}



}