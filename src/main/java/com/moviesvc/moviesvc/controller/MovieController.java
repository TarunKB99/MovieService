package com.moviesvc.moviesvc.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.moviesvc.moviesvc.entity.Movie;
import com.moviesvc.moviesvc.service.MovieService;

@RestController
public class MovieController {

	@Autowired
	MovieService movieService;

	@GetMapping("/getAllMovies")
	public ResponseEntity<List<Movie>> getMovies() {
		return new ResponseEntity<List<Movie>>(movieService.getAllMovies(),HttpStatus.OK);
	}

	@GetMapping("/getMovieById/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable(required = true) UUID id) {
		return new ResponseEntity<Movie>(movieService.getMovieById(id),HttpStatus.OK);
	}

	@GetMapping("/getMovieByRatings/{ratings}")
	public List<Movie> getMovieByRatings(@PathVariable(required = true) double ratings) {
		return movieService.getMovieByRatings(ratings);
	}

	@PostMapping("/movie")
	public ResponseEntity<Void> addMovie(@RequestBody List<Movie> movies) {
		movieService.addMovies(movies);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/movie/{id}")
	public ResponseEntity<Movie> updateMovie(@PathVariable UUID id,@RequestBody Movie movie) {
		return new ResponseEntity<Movie>(movieService.updateMovie(movie,id),HttpStatus.OK) ;
	}

	@DeleteMapping("/movie/{id}")
	public ResponseEntity<Void> deleteMovie(@PathVariable UUID id) {
		movieService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
