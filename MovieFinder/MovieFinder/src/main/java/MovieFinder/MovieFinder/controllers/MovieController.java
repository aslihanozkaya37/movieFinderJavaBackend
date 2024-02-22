package MovieFinder.MovieFinder.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import MovieFinder.MovieFinder.entities.Movie;
import MovieFinder.MovieFinder.model.*;
import MovieFinder.MovieFinder.repositories.MovieRepository;

@RestController
@RequestMapping("movies")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class MovieController {
	private MovieRepository movieRepository;
	
	public MovieController(MovieRepository movieRepository)
	{
		this.movieRepository = movieRepository;
	}
	
	@GetMapping
	public List<Movie> getAllMovies()
	{
		if(movieRepository.count()<1)
		{
			ArrayList<Movie> movieList = new ArrayList<Movie>();
			RestTemplate restTemplate = new RestTemplate();
			String endpoint = "http://www.omdbapi.com/?apikey=65f8056f&s=kill";
			ResponseEntity<Root> data =  restTemplate.getForEntity(endpoint, Root.class);
			
			if(data.getStatusCode()==HttpStatusCode.valueOf(200))
			{			
			    for(Search item : data.getBody().search){
			           Movie m = new Movie();
			           m.setName(item.title);
			           m.setYear(item.year);
			           m.setType(item.type);
			           m.setImagePath(item.poster);
			           
			           movieRepository.save(m);
			           movieList.add(m);
			    }
			}
			return movieList;
		}
		return movieRepository.findAll();
	}
	
	@PostMapping
	public Movie createMovie(@RequestBody Movie newMovie)
	{
		return movieRepository.save(newMovie);
	}
	
	@GetMapping("/{movieId}")
	public Movie GetMovieById (@PathVariable Long movieId)
	{
		return movieRepository.findById(movieId).orElse(null);
	}
	
	@PutMapping("/{movieId}")
	public Boolean UpdateMovie(@PathVariable Long movieId, @RequestBody Movie newMovie)
	{
		Optional<Movie> movie = movieRepository.findById(movieId);
		if(movie.isPresent())
		{
			Movie foundMovie = movie.get();
			foundMovie.setActors(newMovie.getActors());
			foundMovie.setImagePath(newMovie.getImagePath());
			foundMovie.setName(newMovie.getName());
			foundMovie.setType(newMovie.getType());
			foundMovie.setYear(newMovie.getYear());
			
			movieRepository.save(foundMovie);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@DeleteMapping("/{movieId}")
	public Boolean deleteMovie(@PathVariable Long movieId)
	{
		try {
			movieRepository.deleteById(movieId);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	@PostMapping("filterData")
	public List<Movie> filterData(@RequestBody FilterMovieRequest request)
	{
		return movieRepository.searchMovie(request.type, request.name, request.year);
	}
	
	@GetMapping("/searchMovieFromImdb/{searchString}")
	public Object searchMovieOmdb(@PathVariable String searchString)
	{
		RestTemplate restTemplate = new RestTemplate();
		String endpoint = "http://www.omdbapi.com/?apikey=65f8056f&s="+searchString;
		ResponseEntity<Root> data =  restTemplate.getForEntity(endpoint, Root.class);
		
		if(data.getStatusCode()==HttpStatusCode.valueOf(200))
		{			
			return data.getBody();
		}
		else
		{
			return null;
		}
	}
}
