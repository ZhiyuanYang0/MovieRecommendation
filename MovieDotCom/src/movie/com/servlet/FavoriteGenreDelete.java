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

@WebServlet("/favoritegenredelete")
public class FavoriteGenreDelete extends HttpServlet {
	protected FavoriteGenresDao favoriteGenresDao;
	
	@Override
	public void init() throws ServletException {
		favoriteGenresDao = FavoriteGenresDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Favorite Movie Genre");        
        req.getRequestDispatcher("/ProfileUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String favoriteGenreStr = req.getParameter("favoriteGenreId");
        if (favoriteGenreStr == null || favoriteGenreStr.trim().isEmpty()) {
            messages.put("title", "Invalid favorite movie type");
            messages.put("disableSubmit", "true");
        } else {
        	int favoriteGenreId = Integer.parseInt(favoriteGenreStr);
        	FavoriteGenres favoriteGenre = new FavoriteGenres(favoriteGenreId);
	        try {
	        	favoriteGenre =favoriteGenresDao.delete(favoriteGenre);
	        	// Update the message.
		        if (favoriteGenre == null) {
		            messages.put("title", "Successfully deleted " + favoriteGenre);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + favoriteGenre);
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
