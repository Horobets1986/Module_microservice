package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Token;
import service.AuthService;
import util.JspHelper;
import util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final AuthService authService = AuthService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath(UrlPath.LOGIN)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean authenticateUser = authService.isAuthenticateUser(req.getParameter("name"),
                req.getParameter("password"));
        if(authenticateUser){
            Token authToken = new Token(authService.getTokenFromName(req.getParameter("name")));
            String json = mapper.writeValueAsString(authToken);
            resp.setContentType("application/json");
            resp.getWriter().write(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Invalid credentials");
        }
    }
}
