package com.places.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.places.api.domain.MyPlace;
import com.places.api.domain.PlacesOfType;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Param;
import se.walkercrou.places.Place;
import se.walkercrou.places.Status;
import se.walkercrou.places.exception.NoResultsFoundException;

/**
 * @author dimpol
 *
 */
@Service
public class GooglePlacesService {
	
	private static GooglePlaces googlePlaces;
	private static List<Param> commonParams;
	private static List<String> acceptedTypes;
	
	@Value("${google.places-api.search-radius:2000}")
	private double searchRadius;
	
	@Value("${google.places-api.key}")
	private String googlePlacesApiKey;
	
	@Value("${google.places-api.minRating:0}")
	private double minRating;
	
	@Value("${google.places-api.ignore-closed:false}")
	private boolean ignoreClosed;
	
	@Value("${google.places-api.matchIconType:false}")
	private boolean matchIconType;
	
	@Value("${google.places-api.matchOpenNow:true}")
	private boolean matchOpenNow;
	
	public GooglePlacesService() {
		if (GooglePlacesService.commonParams == null) {
			GooglePlacesService.commonParams = new ArrayList<Param>();
			GooglePlacesService.commonParams.add(Param.name("rankby").value("prominence"));
			if (matchOpenNow) GooglePlacesService.commonParams.add(Param.name("opennow").value("true"));
			GooglePlacesService.commonParams.add(Param.name("language").value("el"));
		}
		if (GooglePlacesService.acceptedTypes == null) {
			GooglePlacesService.acceptedTypes = new ArrayList<String>();
			GooglePlacesService.acceptedTypes.add("bar");
			GooglePlacesService.acceptedTypes.add("cafe");
			GooglePlacesService.acceptedTypes.add("nightclub");
			GooglePlacesService.acceptedTypes.add("restaurant");
		}
	}
	
	@PostConstruct
	public void init() {
		GooglePlacesService.googlePlaces = new GooglePlaces(getGooglePlacesApiKey());	
	}
	
	public PlacesOfType getPlacesOfType(double lat, double lng, String type, double radius) {
		List<Param> params = new ArrayList<Param>(GooglePlacesService.commonParams);
		params.add(Param.name("type").value(type));
		Param[] extraParams = params.toArray(new Param[params.size()]);
		PlacesOfType placesOfType = new PlacesOfType();
		placesOfType.setType(type);
		try {
			placesOfType.setPlaces(googlePlaces.getNearbyPlaces(lat, lng, radius, extraParams)
					.stream()
					.filter( place -> place.getRating() >= getMinRating())
					.filter( place -> !ignoreClosed && (place.getStatus() != Status.CLOSED))
					.map( place -> MyPlace.convert(place))
					.filter( place ->  !matchIconType || place.getIconType().equals(type) 
														 || (type.equals("nightclub") && place.getIconType().equals("bar")))
					.sorted( (p1, p2) -> Double.compare(p2.getRating(), p1.getRating()))
					.collect(Collectors.toList()));
		} catch (NoResultsFoundException e) {
			placesOfType.setPlaces(new ArrayList<MyPlace>());
		} 
		return placesOfType;
	}
	
	public PlacesOfType getPlacesOfType(double lat, double lng, String type) {
		return getPlacesOfType(lat, lng, type, getSearchRadius());
	}
	
	public List<PlacesOfType> getPlacesWithTypes(double lat, double lng, double radius) {
		return acceptedTypes
				.stream().map( (t) -> getPlacesOfType(lat, lng, t, radius))
				.collect(Collectors.toList());
	}
	
	public List<PlacesOfType> getPlacesWithTypes(double lat, double lng) {
		return getPlacesWithTypes(lat, lng, getSearchRadius());
	}
	
	public List<MyPlace> getPlaces(double lat, double lng, double radius) {
		return acceptedTypes
				.stream()
				.map( (t) -> getPlacesOfType(lat, lng, t, radius).getPlaces())
				.flatMap(List::stream)
				.distinct()
				.sorted( (p1, p2) -> Double.compare(p2.getRating(), p1.getRating()))
				.collect(Collectors.toList());
	}
	
	public List<MyPlace> getPlaces(double lat, double lng) {
		return getPlaces(lat, lng, getSearchRadius());
	}
	
	public MyPlace getPlaceDetails(String placeId) {
		Param[] extraParams = new Param[1];
		extraParams[0] = Param.name("language").value("el");
		Place place = googlePlaces.getPlaceById(placeId, extraParams);
		return MyPlace.convert(place);
	}

	public String getGooglePlacesApiKey() {
		return googlePlacesApiKey;
	}

	public void setGooglePlacesApiKey(String googlePlacesApiKey) {
		this.googlePlacesApiKey = googlePlacesApiKey;
	}

	public double getSearchRadius() {
		return searchRadius;
	}

	public void setSearchRadius(double searchRadius) {
		this.searchRadius = searchRadius;
	}
	
	public double getMinRating() {
		return minRating;
	}

	public void setMinRating(double minRating) {
		this.minRating = minRating;
	}

	public GooglePlaces getGooglePlaces() {
		return googlePlaces;
	}

	public boolean isMatchIconType() {
		return matchIconType;
	}

	public void setMatchIconType(boolean matchPrimaryType) {
		this.matchIconType = matchPrimaryType;
	}

	public boolean isMatchOpenNow() {
		return matchOpenNow;
	}

	public void setMatchOpenNow(boolean matchOpenNow) {
		this.matchOpenNow = matchOpenNow;
	}

}
