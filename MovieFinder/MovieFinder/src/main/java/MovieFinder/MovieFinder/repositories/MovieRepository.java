package MovieFinder.MovieFinder.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import MovieFinder.MovieFinder.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT u FROM Movie u WHERE u.name LIKE %:name% AND u.type = :type AND u.year = :year")
    List<Movie> searchMovie(String type, String name, String year);
}
