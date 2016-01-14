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

import movie.com.dal.*;
import movie.com.model.*;

@WebServlet("/favoritedirectoradd")
public class FavoriteDirectorAdd extends HttpServlet {
	protected FavoriteDirectorsDao favoriteDirectorsDao;
	protected UsersDao usersDao;
	protected DirectorsDao directorsDao;
	
	@Override
	public void init() throws ServletException {
		favoriteDirectorsDao = FavoriteDirectorsDao.getInstance();
		usersDao = UsersDao.getInstance();
		directorsDao = DirectorsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/FavoriteDirectorAdd.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String userId = req.getParameter("userid");
        String firstName = req.getParameter("fn");
    	String lastName = req.getParameter("ln");
        if (userId == null || userId.trim().isEmpty() || firstName == null || lastName == null) {
            messages.put("success", "Invalid User");
        } else {
	        try {
	        	Users user = usersDao.getUserByUserId(Integer.parseInt(userId));
	        	Directors director = directorsDao.getDirectorByName(firstName, lastName);
	        	if (user != null && director != null) {
	        		FavoriteDirectors favoriteDirector = new FavoriteDirectors(user, director);
	        		favoriteDirector = favoriteDirectorsDao.create(favoriteDirector);
	        		if (favoriteDirector != null) {
	        			messages.put("title", "Successfully added favourite director: " + firstName + " " + lastName);
	        			messages.put("disableSubmit", "true");
	        			resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/profileupdate"));
	        	        return;
	        		} else {
	        			messages.put("title", "Failed to add " + firstName + " " + lastName);
	        			messages.put("disableSubmit", "false");
	        		}
	        	} else {
        			messages.put("title", "Failed to add " + firstName + " " + lastName);
        			messages.put("disableSubmit", "false");
        		}        	
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }        
        req.getRequestDispatcher("/FavoriteDirectorAdd.jsp").forward(req, resp);
    }
}
