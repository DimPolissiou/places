package com.places.api.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.places.api.domain.MyPlace;
import com.places.api.domain.PlacesOfType;
import com.places.api.service.GooglePlacesService;

/**
 * @author dimpol
 *
 */
@RequestMapping("place")
@RestController
public class PlaceController {

	private GooglePlacesService google;
	
	public PlaceController(GooglePlacesService google) {
		this.google = google;
	}
	
	@GetMapping("type")
	public List<PlacesOfType> getAllByType(@RequestParam double lat, @RequestParam double lng, @RequestParam(defaultValue = "0") double radius) {
		
		if (radius <= 0 || radius > 50000) {
			return google.getPlacesWithTypes(lat, lng);
		} else {
			return google.getPlacesWithTypes(lat, lng, radius);
		}
		
	}
	
	@GetMapping("type/{type}")
	public List<MyPlace> getType(@PathVariable(value="type") String type,
			@RequestParam double lat, @RequestParam double lng, @RequestParam(defaultValue = "0") double radius) {
		
		if (radius <= 0 || radius > 50000) {
			return google.getPlacesOfType(lat, lng, type).getPlaces();
		} else {
			return google.getPlacesOfType(lat, lng, type, radius).getPlaces();
		}
		
	}
	
	@GetMapping("/{placeId}")
	public MyPlace details(@PathVariable(value="placeId") String placeId) {
		return google.getPlaceDetails(placeId);
	}
	
	@GetMapping
	public List<MyPlace> getAll(@RequestParam double lat, @RequestParam double lng, @RequestParam(defaultValue = "0") double radius) {
		
		if (radius <= 0 || radius > 50000) {
			return google.getPlaces(lat, lng);
		} else {
			return google.getPlaces(lat, lng, radius);
		}
		
	 }

}
