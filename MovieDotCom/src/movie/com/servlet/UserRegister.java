package movie.com.servlet;

import java.io.IOException;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import movie.com.dal.FavoriteActorsDao;
import movie.com.dal.FavoriteDirectorsDao;
import movie.com.dal.FavoriteGenresDao;
import movie.com.dal.FavoriteMoviesDao;
import movie.com.dal.UsersDao;
import movie.com.model.Users;

@WebServlet("/register")
public class UserRegister extends HttpServlet {
	protected UsersDao usersDao;
	protected FavoriteMoviesDao favoriteMoviesDao;
	protected FavoriteDirectorsDao favoriteDirectorsDao;
	protected FavoriteActorsDao favoriteActorsDao;
	protected FavoriteGenresDao favoriteGenresDao;
	
	@Override
	public void init() throws ServletException {
		usersDao = UsersDao.getInstance();
		favoriteMoviesDao = FavoriteMoviesDao.getInstance();
		favoriteDirectorsDao = FavoriteDirectorsDao.getInstance();
		favoriteActorsDao = FavoriteActorsDao.getInstance();
		favoriteGenresDao = FavoriteGenresDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/UserRegister.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String userName = req.getParameter("username");
        String firstName = req.getParameter("firstname");
    	String lastName = req.getParameter("lastname");
    	String dateString = req.getParameter("date");
    	String password = req.getParameter("password");
    	String email = req.getParameter("email");
    	String profile = req.getParameter("profile");
    	String gender = req.getParameter("gender");
        if (email == null || email.trim().isEmpty() || firstName == null || lastName == null
        		|| password == null) {
            messages.put("success", "Please enter all the field with '*'.");
        } else {
        	try {
        		try  
        		{  
        		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        		    Date date = sdf.parse(dateString);  
        		    Users user = new Users(userName, password, email, firstName, lastName, date, profile, gender);
        		    user = usersDao.create(user);
        		}  
        		catch (ParseException e)  
        		{  
        		    System.out.println(e.getMessage());  
        		}        		
        		int userId = usersDao.getUserByUserName(userName).getUserId();		
        		Users user = usersDao.getUserByUserId(userId);
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
        req.getRequestDispatcher("/UserRegister.jsp").forward(req, resp);
    }
	
}
