package com.moviesvc.moviesvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moviesvc.moviesvc.entity.Screen;
import com.moviesvc.moviesvc.entity.Seat;
import com.moviesvc.moviesvc.entity.Show;
import com.moviesvc.moviesvc.service.ShowService;

@RestController
public class ShowController {

	@Autowired
	ShowService showService;

	@GetMapping("/getAllShows")
	public ResponseEntity<List<Show>> getShows() {
		return new ResponseEntity<List<Show>>(showService.getAllShows(), HttpStatus.OK);
	}

	@GetMapping("/getShowById/{id}")
	public ResponseEntity<Show> getMovieById(@PathVariable(required = true) UUID id) {
		return new ResponseEntity<Show>(showService.getShowById(id), HttpStatus.OK);
	}

	@GetMapping("/getShowsByMovie/{movieId}")
	public List<Show> getShowsByMovie(@PathVariable(required = true) UUID movieId) {
		return showService.getShowByMovie(movieId);
	}

	@GetMapping("/getShowsByCity/{cityId}")
	public List<Show> getShowsByCity(@PathVariable(required = true) UUID cityId) {
		return showService.getShowByCity(cityId);
	}

	@GetMapping("/getShowsByTheatre/{theatreId}")
	public List<Show> getShowsByTheatre(@PathVariable(required = true) UUID theatreId) {
		return showService.getShowByTheatre(theatreId);
	}

	@PostMapping("/shows")
	public ResponseEntity<Void> addShows(@RequestBody List<Show> shows) {
		showService.addShows(shows);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/show/{id}")
	public ResponseEntity<Show> updateShow(@PathVariable UUID id, @RequestBody Show show) {
		return new ResponseEntity<Show>(showService.updateShow(show, id), HttpStatus.OK);
	}

	@DeleteMapping("/show/{id}")
	public ResponseEntity<Void> deleteMovie(@PathVariable UUID id) {
		showService.deleteShow(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("getScreensByTheatreAndMovie/{theatreId}/{movieId}")
	public ResponseEntity<List<Screen>> getScreensByTheatreAndMovie(@PathVariable UUID theatreId,@PathVariable UUID movieId) {
		return new ResponseEntity<List<Screen>>(showService.getScreensByTheatreAndMovie(theatreId,movieId), HttpStatus.OK);
	}
	
	@PutMapping("updateSeatsByShow/{showId}/{status}")
	public ResponseEntity<List<Seat>> updateSeats(@PathVariable UUID showId,@PathVariable boolean status,@RequestBody List<Seat> seats){
		return new ResponseEntity<List<Seat>>(showService.updateSeats(showId,status,seats), HttpStatus.OK);
	}
	
}
