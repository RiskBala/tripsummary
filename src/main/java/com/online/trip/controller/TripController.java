package com.online.trip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.online.trip.model.TripSummary;
import com.online.trip.service.TripService;

@Controller
public class TripController {

	@Autowired
	private TripService tripService;
	
	@RequestMapping(value = "trip-summary/v1/trip", method = RequestMethod.GET)
	@ResponseBody
	public List<TripSummary> getTripSummary(@RequestHeader(value = "userId", required = true) String userId,
			@RequestHeader(value = "roles", required = true) String roles) {
		return tripService.getTripSummary(userId, roles);
	}
}
