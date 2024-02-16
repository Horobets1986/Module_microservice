package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Salaru;
import factory.SalaruServiceFactory;
import service.SalaruService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/salaru/*")
public class SalaruServlet extends HttpServlet {
    private SalaruService salaruService;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        salaruService = SalaruServiceFactory.createSalaruService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try {
            if (request.getPathInfo() != null) {
                String stringId = request.getPathInfo().substring(1);
                Salaru salaru = salaruService.getSalaruById(Integer.parseInt(stringId));
                response.getWriter().println(mapper.writeValueAsString(salaru));
            } else {
                List<Salaru> allSalaru = salaruService.getAllSalaru();
                response.getWriter().println(mapper.writeValueAsString(allSalaru));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal Server Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer sf = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sf.append(line);
            }
            Salaru salaru = mapper.readValue(sf.toString(), Salaru.class);
            salaruService.saveSalaru(salaru);
            out.write(mapper.writeValueAsString(salaru));
            response.setStatus(HttpServletResponse.SC_CREATED);

        } catch (Exception e) {
            out.write("{\"error\" : \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            if (request.getPathInfo() != null) {
                String stringId = request.getPathInfo().substring(1);
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                Salaru salaru = mapper.readValue(sb.toString(), Salaru.class);
                salaruService.update(Integer.parseInt(stringId), salaru);
                response.getWriter().append("salaru is updated");
            }
        } catch (Exception e) {
            out.write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        String contextPath = request.getPathInfo();
        try {
            if (contextPath != null) {
                salaruService.delete(Integer.parseInt(contextPath.substring(1)));
                out.write("{\"report\": \"deleted\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
