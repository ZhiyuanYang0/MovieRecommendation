
package movie.com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import movie.com.dal.FavoriteMoviesDao;
import movie.com.dal.MoviesDao;
import movie.com.model.FavoriteMovies;
import movie.com.model.Movies;
import movie.com.model.Users;

@WebServlet("/moviesforrating")
public class MoviesForRating extends HttpServlet {

    private MoviesDao moviesDao;
    private FavoriteMoviesDao favoriteMoviesDao;

    @Override
    public void init() throws ServletException {
        moviesDao = MoviesDao.getInstance();
        favoriteMoviesDao = FavoriteMoviesDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getSession().getAttribute("userid") == null) {
            req.getRequestDispatcher("/MoviesForRatingNotLogin.jsp").forward(req, resp);
            return;
        }
        int userId = (int) req.getSession().getAttribute("userid");
        // Retrieve Movies, and store in the request.
        List<Movies> movies = new ArrayList<Movies>();
        try {
            movies = moviesDao.getMoviesForRating();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        List<Integer> ratingList = new ArrayList<Integer>();
        for (Movies m : movies) {
            try {
                FavoriteMovies favor = favoriteMoviesDao.getFavoriteMoviesByUserIdAndMovieId(userId,
                        m.getMovieId());
                if (favor == null) {
                    ratingList.add(0);
                } else {
                    ratingList.add(favor.getRating());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        req.setAttribute("movies", movies);
        req.setAttribute("ratings", ratingList);
        req.getRequestDispatcher("/MoviesForRating.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String userId = req.getParameter("userId");
        String movieId = req.getParameter("movieId");
        String rating = req.getParameter("rating");
        try {
            FavoriteMovies favor = favoriteMoviesDao.getFavoriteMoviesByUserIdAndMovieId(111,
                    Integer.parseInt(movieId));
            Users u = new Users(Integer.parseInt(userId));
            Movies m = new Movies(Integer.parseInt(movieId));
            if (favor == null) {
                favor = new FavoriteMovies(0, u, m, Integer.parseInt(rating));
                favoriteMoviesDao.create(favor);
            } else {
                favoriteMoviesDao.updateRating(u, m, Integer.parseInt(rating));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new IOException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.getRequestDispatcher("/ForAjax.jsp").forward(req, resp);
    }

}
