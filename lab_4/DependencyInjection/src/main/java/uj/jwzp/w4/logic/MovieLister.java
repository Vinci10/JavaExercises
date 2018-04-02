package uj.jwzp.w4.logic;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import uj.jwzp.w4.model.Movie;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Lazy(value = true)
public class MovieLister {
    private MovieFinder finder;

    public MovieLister(String path) {
        this.finder = new CSVMovieFinder(path);
    }

    public List<Movie> moviesDirectedBy(String name) {
        List<Movie> allMovies = finder.findAll();
        return allMovies.stream()
                .filter(m -> m.getDirector().equals(name))
                .collect(Collectors.toList());
    }
}
