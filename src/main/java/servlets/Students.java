package servlets;

import DAO.DataBase;
import DataModels.Courses;
import DataModels.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/students")
public class Students extends HttpServlet {
    
    DataBase cnn = new DataBase();
    private GsonBuilder gsonBuilder;
    private Gson gson;

    @Override
      public void init() {
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        List<Student> listUsers = new ArrayList();
        try {
            listUsers = cnn.getStudents();
        } catch (SQLException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson("error: " + ex.getMessage()));
        } finally {
            out.flush();
        }
        if (listUsers == null || listUsers.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson("error:"+ "No se encontraron alumnos"));
        }
        int cont = 0;
        for (Student listUser : listUsers) {
            List<Map<String, Object>> courseMaps = new ArrayList<>();
            try {
                courseMaps = cnn.getCursesByEstudenDocument(listUser.getDocument());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            List<Courses> courses = new ArrayList<>();
            for (Map<String, Object> courseMap : courseMaps) {
                Courses course = new Courses();
                course.setCourseCode((Integer) courseMap.get("courseCode"));
                course.setCourseName((String) courseMap.get("courseName"));
                course.setCourseDescription((String) courseMap.get("courseDescription"));
                courses.add(course);
            }
            listUser.setCourses(courses);
            }
        out.print(gson.toJson(listUsers));
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
