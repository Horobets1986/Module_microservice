package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entity.Role;
import service.AuthService;
import util.JspHelper;
import util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    AuthService authService = AuthService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());

        req.getRequestDispatcher(JspHelper.getPath(UrlPath.REGISTRATION))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Role role = req.getParameter("role") != null ? Role.valueOf(req.getParameter("role")) : Role.USER;
        authService.registerUser(req.getParameter("name"), req.getParameter("password"), role);
    }
}
