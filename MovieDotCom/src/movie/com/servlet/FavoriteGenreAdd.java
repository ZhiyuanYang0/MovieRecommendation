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

@WebServlet("/favoritegenreadd")
public class FavoriteGenreAdd extends HttpServlet {
	protected FavoriteGenresDao favoriteGenresDao;
	protected UsersDao usersDao;
	protected GenresDao genresDao;
	
	@Override
	public void init() throws ServletException {
		favoriteGenresDao = FavoriteGenresDao.getInstance();
		usersDao = UsersDao.getInstance();
		genresDao = GenresDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/FavoriteGenreAdd.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String userId = req.getParameter("userid");
    	String genreId = req.getParameter("genreid");
        if (userId == null || userId.trim().isEmpty() || genreId == null || genreId.trim().isEmpty()) {
            messages.put("success", "Invalid User");
        } else {
	        try {
	        	Users user = usersDao.getUserByUserId(Integer.parseInt(userId));
	        	Genres genre = genresDao.getGenreById(Integer.parseInt(genreId));
	        	if (user != null && genre != null) {
	        		FavoriteGenres favoriteGenre = new FavoriteGenres(user, genre);
	        		favoriteGenre = favoriteGenresDao.create(favoriteGenre);
	        		if (favoriteGenre != null) {
	        			messages.put("title", "Successfully added favourite genre: " + genre.getGenreType().name());
	        			messages.put("disableSubmit", "true");
	        			resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/profileupdate"));
	        	        return;
	        		} else {
	        			messages.put("title", "Failed to add " + genre.getGenreType().name());
	        			messages.put("disableSubmit", "false");
	        		}
	        	} else {
        			messages.put("title", "Failed to add " + genre.getGenreType().name());
        			messages.put("disableSubmit", "false");
        		}        	
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }        
        req.getRequestDispatcher("/FavoriteGenreAdd.jsp").forward(req, resp);
    }
}
