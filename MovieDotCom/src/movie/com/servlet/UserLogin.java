package movie.com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import movie.com.dal.*;
import movie.com.model.Users;

@WebServlet("/login")
public class UserLogin extends HttpServlet {
	protected UsersDao usersDao;
	
	@Override
	public void init() throws ServletException {
		usersDao = UsersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		req.getRequestDispatcher("/Login.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || username.trim().isEmpty()) {
            messages.put("success", "Please enter a valid UserName.");
        } else {
        	try {
        		Users user = usersDao.getUserByUserName(username);
        		if(user != null && user.getPassword().equals(password)) {
        			messages.put("success", "Successfully login.");
        			HttpSession session=req.getSession(); 
        	        session.setAttribute("userid", user.getUserId());
        	        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/home"));
        	        return;
        		} else {
        			messages.put("success", "Invalid username or password.");	
        		}
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        req.getRequestDispatcher("/Login.jsp").forward(req, resp);
	}
}
