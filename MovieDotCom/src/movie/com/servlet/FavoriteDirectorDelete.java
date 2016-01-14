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

@WebServlet("/favoritedirectordelete")
public class FavoriteDirectorDelete extends HttpServlet {
	protected FavoriteDirectorsDao favoriteDirectorsDao;
	
	@Override
	public void init() throws ServletException {
		favoriteDirectorsDao = FavoriteDirectorsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Favorite Director");        
        req.getRequestDispatcher("/ProfileUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String favoriteDirectorStr = req.getParameter("favoriteDirectorId");
        if (favoriteDirectorStr == null || favoriteDirectorStr.trim().isEmpty()) {
            messages.put("title", "Invalid favorite director");
            messages.put("disableSubmit", "true");
        } else {
        	int favoriteDirectorId = Integer.parseInt(favoriteDirectorStr);
        	FavoriteDirectors favoriteDirector = new FavoriteDirectors(favoriteDirectorId);
	        try {
	        	favoriteDirector =favoriteDirectorsDao.delete(favoriteDirector);
	        	// Update the message.
		        if (favoriteDirector == null) {
		            messages.put("title", "Successfully deleted " + favoriteDirector);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + favoriteDirector);
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
