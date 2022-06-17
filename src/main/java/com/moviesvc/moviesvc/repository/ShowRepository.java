package com.moviesvc.moviesvc.repository;

import java.util.List;
import java.util.UUID;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.moviesvc.moviesvc.entity.Show;
@EnableScan
public interface ShowRepository extends CrudRepository<Show, UUID>{
	List<Show> findByCityId(UUID cityId);
	List<Show> findByTheatreId(UUID theatreId);
	List<Show> findByMovieId(UUID movieId);
}
