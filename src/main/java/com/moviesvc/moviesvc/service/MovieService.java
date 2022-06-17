package com.moviesvc.moviesvc.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviesvc.moviesvc.entity.Movie;
import com.moviesvc.moviesvc.exceptions.InvalidFieldsException;
import com.moviesvc.moviesvc.exceptions.NotFoundException;
import com.moviesvc.moviesvc.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	public List<Movie> getAllMovies() {
		List<Movie> movies = new ArrayList<Movie>();
		movieRepository.findAll().forEach(movie -> movies.add(movie));
		return movies;
	}

	public Movie getMovieById(UUID id) {
		return movieRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to find the movie resource with id: " + id));
	}

	public List<Movie> getMovieByRatings(double rating) {
		return movieRepository.findByRatings(rating);
	}

	public List<Movie> addMovies(List<Movie> movies) {
		List<Movie> savedMovies=new ArrayList<Movie>();
		for(Movie  m:movies) {
			if(m.getName().isEmpty() || m.getName()==null) {
				throw new InvalidFieldsException("Invalid fields value, values can't be empty or null");
			}
			Movie movie=movieRepository.save(m);
			savedMovies.add(movie);
		}
		return savedMovies;
	}

	public void delete(UUID id) {
		if(!movieRepository.findById(id).isPresent()){
			throw new NotFoundException("Unable to find the movie resource with id: " + id);
		}
		movieRepository.deleteById(id);
	}

	public Movie updateMovie(Movie movie, UUID movieId) {
		if(movieId==null || movie.getName().isEmpty())
			throw new InvalidFieldsException("Invalid fields value, values can't be empty or null");
		if(movieRepository.existsById(movieId)) {
			Optional<Movie> c = movieRepository.findById(movieId);
			c.get().setDuration(movie.getDuration());
			c.get().setDescription(movie.getDescription());
			c.get().setGenre(movie.getGenre());
			c.get().setLanguage(movie.getLanguage());
			c.get().setRatings(movie.getRatings());
			c.get().setName(movie.getName());
			return movieRepository.save(c.get());
		}
		return movieRepository.save(movie);
	}
}
