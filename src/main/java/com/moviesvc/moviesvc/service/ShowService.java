package com.moviesvc.moviesvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.moviesvc.moviesvc.entity.Screen;
import com.moviesvc.moviesvc.entity.Seat;
import com.moviesvc.moviesvc.entity.Show;
import com.moviesvc.moviesvc.exceptions.InvalidFieldsException;
import com.moviesvc.moviesvc.exceptions.NotFoundException;
import com.moviesvc.moviesvc.repository.ShowRepository;

@Service
public class ShowService {

	@Autowired
	private ShowRepository showRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${theatresvc}")
	String theatresvc;

	public List<Show> getAllShows() {
		List<Show> shows = new ArrayList<Show>();
		showRepository.findAll().forEach(show -> shows.add(show));
		return shows;
	}

	public Show getShowById(UUID id) {
		return showRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Unable to find the show resource with id: " + id));
	}

	public List<Show> getShowByCity(UUID cityId) {
		return showRepository.findByCityId(cityId);
	}

	public List<Show> getShowByTheatre(UUID theatreId) {
		return showRepository.findByTheatreId(theatreId);
	}

	public List<Show> getShowByMovie(UUID movieId) {
		return showRepository.findByMovieId(movieId);
	}

	public List<Show> addShows(List<Show> shows) {
		List<Show> savedShows = new ArrayList<Show>();
		for (Show s : shows) {
			if (s.getCityId() == null || s.getDate() == null || s.getEndTime() == null || s.getMovieId() == null
					|| s.getTheatreId() == null || s.getStartTime() == null) {
				throw new InvalidFieldsException("Invalid fields value, values can't be empty or null");
			}
			savedShows.add(showRepository.save(s));
		}
		return savedShows;
	}

	public void deleteShow(UUID showId) {
		if (!showRepository.findById(showId).isPresent()) {
			throw new NotFoundException("Unable to find the show resource with id: " + showId);
		}
		showRepository.deleteById(showId);
	}

	public Show updateShow(Show s, UUID showId) {
		if (s.getCityId() == null || s.getDate() == null || s.getEndTime() == null || s.getMovieId() == null
				|| s.getTheatreId() == null || s.getStartTime() == null) {
			if (showRepository.existsById(showId)) {
				Optional<Show> show = showRepository.findById(showId);
				show.get().setCityId(s.getCityId());
				show.get().setDate(s.getDate());
				;
				show.get().setEndTime(s.getEndTime());
				show.get().setMovieId(s.getMovieId());
				show.get().setSeats(s.getSeats());
				show.get().setStartTime(s.getStartTime());
				show.get().setTheatreId(s.getTheatreId());
				return showRepository.save(show.get());
			}
		}
		return showRepository.save(s);
	}
	
	public List<Screen> getScreensByTheatreAndMovie(UUID theatreId,UUID movieId){
		List<Screen> screens=new ArrayList<Screen>();
		List<Show> shows=getAllShows();
		for (Show show : shows) {
			if(show.getMovieId().compareTo(movieId)==0 && show.getTheatreId().compareTo(theatreId)==0) {
				screens.add(restTemplate.getForObject(theatresvc+"screen/"+show.getScreenId(), Screen.class));
			}
		}
		return screens;
	}
	
	public List<Seat> updateSeats(UUID showId,boolean status,List<Seat> seats){
		if (showRepository.existsById(showId)) {
			Show show=getShowById(showId);
			for (Seat seat : show.getSeats()) {
				for (Seat seat2 : seats) {
					if(seat2.getSeatID().compareTo(seat.getSeatID())==0) {
						seat.setStatus(status);
						seat2.setStatus(status);
					}
				}
			}
			showRepository.save(show);
			return seats;
		}
		return null;
	}

}
