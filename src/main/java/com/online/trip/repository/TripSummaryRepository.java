package com.online.trip.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.online.trip.model.TripSummary;

public interface TripSummaryRepository extends MongoRepository<TripSummary, String> {

	List<TripSummary> findByDriverId(String userId);

	List<TripSummary> findByRiderId(String userId);

}
