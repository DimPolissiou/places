package com.places.api.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.places.api.domain.MyPlace;
import com.places.api.domain.PlacesOfType;
import com.places.api.service.GooglePlacesService;
import com.places.api.web.resource.LocationResource;
import com.places.api.web.resource.PhotoResponse;

/**
 * @author dimpol
 *
 */
@CrossOrigin
@RequestMapping("place")
@RestController
public class PlaceController {

	private GooglePlacesService google;
	
	public PlaceController(GooglePlacesService google) {
		this.google = google;
	}
	
	@PostMapping("type")
	public List<PlacesOfType> getAllByType(@RequestBody @Valid LocationResource location) {
		
		double radius = location.getRadius();
		if (radius <= 0 || radius > 50000) radius = 0;
		return google.getPlacesWithTypes(location.getLat(), location.getLng(), radius, location.getMaxPrice());
		
	}
	
	@PostMapping("type/{type}")
	public List<MyPlace> getType(@PathVariable(value="type") String type,
			@RequestBody @Valid LocationResource location) {
		
		double radius = location.getRadius();
		if (radius <= 0 || radius > 50000) radius = 0;
			return google.getPlacesOfType(location.getLat(), location.getLng(), type, radius, location.getMaxPrice()).getPlaces();
		
	}

	@PostMapping
	public List<MyPlace> getAll(@RequestBody @Valid LocationResource location) {
		
		double radius = location.getRadius();
		if (radius <= 0 || radius > 50000) radius = 0;
			return google.getPlaces(location.getLat(), location.getLng(), radius, location.getMaxPrice());
		
	}
	
	@GetMapping(value = "/photo/{placeId}")
	public ResponseEntity<PhotoResponse> photo(@PathVariable(value="placeId") String placeId) throws IOException {
		Optional<String> filename = google.getPlacePhoto(placeId);
		if (filename.isPresent())
			return ResponseEntity.ok().body(new PhotoResponse(filename.get()));
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{placeId}")
	public MyPlace details(@PathVariable(value="placeId") String placeId) {
		return google.getPlaceDetails(placeId);
	}

}
