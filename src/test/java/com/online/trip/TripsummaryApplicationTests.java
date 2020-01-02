package com.online.trip;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.online.trip.model.TripSummary;
import com.online.trip.repository.TripSummaryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TripsummaryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TripsummaryApplicationTests {

	@LocalServerPort
	private int port;

	@MockBean
	private TripSummaryRepository tripSummaryRepository;

	@Autowired
	private TestRestTemplate restTemplate;
	
	
	/*@Test
	public void contextLoads() {
	}
	*/
	@Test
	public void testGetTripSummaryForRider() throws Exception {
		Mockito.when(tripSummaryRepository.findByRiderId(Mockito.anyString()))
				.thenReturn(TripsummaryApplicationTests.riderTrips());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", "rider1");
		headers.set("roles", "RIDER");
		ResponseEntity<String> response = restTemplate.exchange(
				new URL("http://localhost:" + port + "/trip-summary/trip").toString(), HttpMethod.GET,
				new HttpEntity<>(headers), String.class);
		assertTrue(StringUtils.contains(response.getBody(), "riderId1"));
	}
	
	@Test
	public void testGetTripSummaryForDriver() throws Exception {
		Mockito.when(tripSummaryRepository.findByDriverId(Mockito.anyString()))
				.thenReturn(TripsummaryApplicationTests.riderTrips());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", "driver1");
		headers.set("roles", "DRIVER");
		ResponseEntity<String> response = restTemplate.exchange(
				new URL("http://localhost:" + port + "/trip-query/summary").toString(), HttpMethod.GET,
				new HttpEntity<>(headers), String.class);
	//	assertTrue(StringUtils.contains(response.getBody(), "driverId1"));
	}

	@Test
	public void testGetTripSummaryForAdmin() throws Exception {
		Mockito.when(tripSummaryRepository.findAll()).thenReturn(TripsummaryApplicationTests.riderTrips());
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("userId", "admin1");
		headers.set("roles", "ADMIN");
		ResponseEntity<String> response = restTemplate.exchange(
				new URL("http://localhost:" + port + "/trip-query/summary").toString(), HttpMethod.GET,
				new HttpEntity<>(headers), String.class);
		//assertTrue(StringUtils.contains(response.getBody(), "driverId1"));
	}
	public static List<TripSummary> riderTrips() {
		List<TripSummary> trips = new ArrayList<TripSummary>();
		TripSummary trip1 = new TripSummary();
		trip1.setRiderId("riderId1");
		trip1.setDriverId("driverId1");
		trip1.setFromLocation("abc");
		trip1.setToLocation("xyz");
		trip1.setStartTime("01/01/2019 09:00:00");
		trip1.setEndTime("01/01/2019 10:00:00");
		trip1.setStatus("COMPLETE");
		//trip1.setTripId("12345");
		trips.add(trip1);
		return trips;
	}
}
