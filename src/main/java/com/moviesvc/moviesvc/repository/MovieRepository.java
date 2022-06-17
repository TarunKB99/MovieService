package com.moviesvc.moviesvc.repository;
import java.util.List;
import java.util.UUID;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.moviesvc.moviesvc.entity.Movie; 
@EnableScan
public interface MovieRepository extends CrudRepository<Movie, UUID>  {

	List<Movie> findByRatings(double rating);
}
