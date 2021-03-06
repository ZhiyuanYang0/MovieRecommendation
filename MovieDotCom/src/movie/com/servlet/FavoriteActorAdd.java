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

@WebServlet("/favoriteactoradd")
public class FavoriteActorAdd extends HttpServlet {
	protected FavoriteActorsDao favoriteActorsDao;
	protected UsersDao usersDao;
	protected ActorsDao actorsDao;
	
	@Override
	public void init() throws ServletException {
		favoriteActorsDao = FavoriteActorsDao.getInstance();
		usersDao = UsersDao.getInstance();
		actorsDao = ActorsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/FavoriteActorAdd.jsp").forward(req, resp);
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
	        	Actors actor = actorsDao.getActorByName(firstName, lastName);
	        	if (user != null && actor != null) {
	        		FavoriteActors favoriteActor = new FavoriteActors(user, actor);
	        		favoriteActor = favoriteActorsDao.create(favoriteActor);
	        		if (favoriteActor != null) {
	        			messages.put("title", "Successfully added favourite actor: " + firstName + " " + lastName);
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
        req.getRequestDispatcher("/FavoriteActorAdd.jsp").forward(req, resp);
    }
}
