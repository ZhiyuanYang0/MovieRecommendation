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

@WebServlet("/favoriteactordelete")
public class FavoriteActorDelete extends HttpServlet {
	protected FavoriteActorsDao favoriteActorsDao;
	
	@Override
	public void init() throws ServletException {
		favoriteActorsDao = FavoriteActorsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Favorite Actor");        
        req.getRequestDispatcher("/ProfileUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String favoriteActorStr = req.getParameter("favoriteActorId");
        if (favoriteActorStr == null || favoriteActorStr.trim().isEmpty()) {
            messages.put("title", "Invalid favorite actor");
            messages.put("disableSubmit", "true");
        } else {
        	int favoriteActorId = Integer.parseInt(favoriteActorStr);
        	FavoriteActors favoriteActor = new FavoriteActors(favoriteActorId);
	        try {
	        	favoriteActor =favoriteActorsDao.delete(favoriteActor);
	        	// Update the message.
		        if (favoriteActor == null) {
		            messages.put("title", "Successfully deleted " + favoriteActor);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + favoriteActor);
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
