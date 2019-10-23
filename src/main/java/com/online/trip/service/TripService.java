package com.online.trip.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.trip.model.TripSummary;
import com.online.trip.repository.TripSummaryRepository;

@Service
public class TripService {
	@Autowired
	private TripSummaryRepository tripSummaryRepository;
	
	public List<TripSummary> getTripSummary(String userId, String roles) {
		if(roles.equalsIgnoreCase("Admin")) {
			return tripSummaryRepository.findAll();
		}else if(roles.equalsIgnoreCase("DRIVER")) {
			return tripSummaryRepository.findByDriverId(userId);
		}else {
			return tripSummaryRepository.findByRiderId(userId);
		}
		
	}
}
