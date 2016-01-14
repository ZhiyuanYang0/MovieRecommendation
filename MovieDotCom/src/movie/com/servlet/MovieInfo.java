
package movie.com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import movie.com.dal.ActorsDao;
import movie.com.dal.DirectedMoviesDao;
import movie.com.dal.DirectorsDao;
import movie.com.dal.MoviesDao;
import movie.com.dal.PerformedMoviesDao;
import movie.com.model.Actors;
import movie.com.model.DirectedMovies;
import movie.com.model.Directors;
import movie.com.model.Movies;
import movie.com.model.PerformedMovies;

@WebServlet("/movieinfo")
public class MovieInfo extends HttpServlet {
    protected MoviesDao moviesDao;
    protected PerformedMoviesDao performedMoviesDao;
    protected DirectedMoviesDao directedMoviesDao;

    @Override
    public void init() throws ServletException {
        moviesDao = moviesDao.getInstance();
        performedMoviesDao = performedMoviesDao.getInstance();
        directedMoviesDao = directedMoviesDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        Movies movie = null;
        List<PerformedMovies> performedMovies = new ArrayList<PerformedMovies>();
        List<DirectedMovies> directedMovies = new ArrayList<DirectedMovies>();
        List<Actors> actors = new ArrayList<Actors>();
        List<Directors> directors = new ArrayList<Directors>();

        // Retrieve and validate name.
        // firstname is retrieved from the URL query string.
        String movieId = req.getParameter("movieid");
        String from = req.getParameter("from");
        if (movieId == null || movieId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
            if (from == null) {
                try {
                    int movieIdInt = Integer.parseInt(movieId);
                    movie = moviesDao.getMovieById(movieIdInt);
                    performedMovies = performedMoviesDao.getPerformedMovieByMovieId(movieIdInt);
                    directedMovies = directedMoviesDao.getMoviesByMovieId(movieIdInt);
                    req.setAttribute("movie", movie);
                    req.setAttribute("performedmovies", performedMovies);
                    req.setAttribute("directedmovies", directedMovies);
                    req.getRequestDispatcher("/MovieInfo.jsp").forward(req, resp);
                    return;
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new IOException(e);
                }
            } else {
                // entered from recommendation page
                try {
                    int movieIdInt = Integer.parseInt(movieId);
                    movie = moviesDao.getRecomMovieById(movieIdInt);
                    req.setAttribute("movie", movie);
                    req.getRequestDispatcher("/RecomMovieInfo.jsp").forward(req, resp);
                    return;
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new IOException(e);
                }
            }
        }

    }

}
