package servlets;

import DAO.DataBase;
import DataModels.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/students-average-age")
public class Students_average_age extends HttpServlet {
    
    DataBase cnn = new DataBase();
    private GsonBuilder gsonBuilder;
    private Gson gson;

    @Override
      public void init() {
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
            return;
        } finally {
            out.flush();
        }
        if (listUsers == null || listUsers.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson("error:"+ "No se encontraron alumnos"));
            return;
        }
        int cont;
        int suma = 0;
        for(cont = 0; cont < listUsers.size();cont++){
            suma = listUsers.get(cont).getAge() + suma;
        }
        int promedio = suma/cont;
        List<Student> listResult = new ArrayList();
        for (Student listUser : listUsers) {
            if (listUser.getAge() > promedio) {
                listResult.add(listUser);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("1.message", "Los estudiantes con edad sobre el promedio son los siguientes:");
        result.put("2.students", listResult);
        out.print(gson.toJson(result));
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
