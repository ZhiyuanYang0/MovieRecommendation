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

import java.util.*;
import movie.com.dal.*;
import movie.com.model.*;

@WebServlet("/profileupdate")
public class ProfileUpdate extends HttpServlet {
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

        // Retrieve user and validate.
        if (req.getSession().getAttribute("userid") == null) {
        	resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/home"));
	        return;
        }
        int userId = (int) req.getSession().getAttribute("userid");
     
    	try {
    		Users user = usersDao.getUserByUserId(userId);
    		if(user == null) {
    			messages.put("success", "User does not exist.");
    		}
    		req.setAttribute("user", user);
    		List<FavoriteMovies> favoriteMovies = favoriteMoviesDao.getFavoriteMoviesByUserId(userId);
    		req.setAttribute("favoriteMovies", favoriteMovies);
    		List<FavoriteDirectors> favoriteDirectors = favoriteDirectorsDao.getFavoriteDirectorsByUserId(userId);
    		req.setAttribute("favoriteDirectors", favoriteDirectors);
    		List<FavoriteActors> favoriteActors = favoriteActorsDao.getFavoriteActorByUserId(userId);
    		req.setAttribute("favoriteActors", favoriteActors);
    		List<FavoriteGenres> favoriteGenres = favoriteGenresDao.getFavoriteGenresByUserId(userId);
    		req.setAttribute("favoriteGenres", favoriteGenres);
    	} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }      
        req.getRequestDispatcher("/ProfileUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String userIdStr = req.getParameter("userid");
        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid UserName.");
        } else {
        	try {
        		int userId = Integer.parseInt(userIdStr);       		
        		Users user = usersDao.getUserByUserId(userId);
        		if(user == null) {
        			messages.put("success", "UserName does not exist. No update to perform.");
        		} else {
        			String newFirstName = req.getParameter("firstname");
        			if (newFirstName != null && !newFirstName.trim().isEmpty() && !newFirstName.equals(user.getFirstName())) {
        				user = usersDao.updateLastName(user, newFirstName);
        			}
        			String newLastName = req.getParameter("lastname");
        			if (newLastName != null && !newLastName.trim().isEmpty() && !newLastName.equals(user.getLastName())) {
        				user = usersDao.updateLastName(user, newLastName);
        			}
        			String newPassword = req.getParameter("password");
        			if (newPassword != null && !newPassword.isEmpty() && !newPassword.equals(user.getPassword())) {
        				user = usersDao.updatePassword(user, newPassword);
        			}
        			String newEmail = req.getParameter("email");
        			if (newEmail != null && !newEmail.trim().isEmpty() && !newEmail.equals(user.getEmail())) {
        				user = usersDao.updateEmail(user, newEmail);
        			}
        			String newProfile = req.getParameter("profile");
        			if (newProfile != null && !newProfile.trim().isEmpty() && !newProfile.equals(user.getProfile())) {
        				user = usersDao.updateProfile(user, newProfile);
        			}
        			messages.put("success", "Successfully updated " + user.getUserName());
        		}
        		req.setAttribute("user", user);
        		List<FavoriteMovies> favoriteMovies = favoriteMoviesDao.getFavoriteMoviesByUserId(userId);
        		req.setAttribute("favoriteMovies", favoriteMovies);
        		List<FavoriteDirectors> favoriteDirectors = favoriteDirectorsDao.getFavoriteDirectorsByUserId(userId);
        		req.setAttribute("favoriteDirectors", favoriteDirectors);
        		List<FavoriteActors> favoriteActors = favoriteActorsDao.getFavoriteActorByUserId(userId);
        		req.setAttribute("favoriteActors", favoriteActors);
        		List<FavoriteGenres> favoriteGenres = favoriteGenresDao.getFavoriteGenresByUserId(userId);
        		req.setAttribute("favoriteGenres", favoriteGenres);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }        
        req.getRequestDispatcher("/ProfileUpdate.jsp").forward(req, resp);
    }
}
