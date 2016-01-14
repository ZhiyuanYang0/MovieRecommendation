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

@WebServlet("/favoritemoviedelete")
public class FavoriteMovieDelete extends HttpServlet {
	protected FavoriteMoviesDao favoriteMoviesDao;
	
	@Override
	public void init() throws ServletException {
		favoriteMoviesDao = FavoriteMoviesDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Favorite Movie");        
        req.getRequestDispatcher("/ProfileUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String favoriteMovieStr = req.getParameter("favoriteMovieId");
        if (favoriteMovieStr == null || favoriteMovieStr.trim().isEmpty()) {
            messages.put("title", "Invalid favorite movie");
            messages.put("disableSubmit", "true");
        } else {
        	int favoriteMovieId = Integer.parseInt(favoriteMovieStr);
        	FavoriteMovies favoriteMovie = new FavoriteMovies(favoriteMovieId);
	        try {
	        	favoriteMovie =favoriteMoviesDao.delete(favoriteMovie);
	        	// Update the message.
		        if (favoriteMovie == null) {
		            messages.put("title", "Successfully deleted " + favoriteMovieId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + favoriteMovieId);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }        
        req.getRequestDispatcher("/ForAjax.jsp").forward(req, resp);
    }
}
