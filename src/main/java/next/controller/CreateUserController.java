package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.dao.UserDao;
import next.model.User;

@SuppressWarnings("serial")
@WebServlet("/users/create")
public class CreateUserController extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User(
                req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email")
                );
        
        UserDao userDao = new UserDao();
        userDao.insert(user);
        
        resp.sendRedirect("/");
    }
}
