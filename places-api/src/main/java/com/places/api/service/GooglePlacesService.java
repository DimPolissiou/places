package com.places.api.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.places.api.domain.MyPlace;
import com.places.api.domain.PlacesOfType;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Param;
import se.walkercrou.places.Photo;
import se.walkercrou.places.Place;
import se.walkercrou.places.Status;
import se.walkercrou.places.exception.GooglePlacesException;

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
	
	@Value("${google.places-api.base-photo-directory}")
	private String basePhotoDirectory;
	
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
	
	public PlacesOfType getPlacesOfType(double lat, double lng, String type, double radius, int maxPrice) {
		List<Param> params = new ArrayList<Param>(GooglePlacesService.commonParams);
		params.add(Param.name("type").value(type));
		if (ignoreClosed) params.add(Param.name("opennow").value("true"));
		if (maxPrice < 4 && maxPrice>=0) params.add(Param.name("opennow").value(maxPrice));
		Param[] extraParams = params.toArray(new Param[params.size()]);
		PlacesOfType placesOfType = new PlacesOfType();
		placesOfType.setType(type);
		try {
			placesOfType.setPlaces(googlePlaces.getNearbyPlaces(lat, lng, (radius==0)?getSearchRadius():radius, extraParams)
					.stream()
					.filter( place -> place.getRating() >= getMinRating())
					.filter( place -> !ignoreClosed && (place.getStatus() != Status.CLOSED))
					.map( place -> MyPlace.convert(place))
//					.filter( place ->  !matchIconType || place.getIconType().equals(type) 
//														 || (type.equals("nightclub") && place.getIconType().equals("bar")))
					.sorted( (p1, p2) -> Double.compare(p2.getRating(), p1.getRating()))
					.collect(Collectors.toList()));
		} catch (GooglePlacesException e) {
			placesOfType.setPlaces(new ArrayList<MyPlace>());
		} 
		return placesOfType;
	}
	
	public List<PlacesOfType> getPlacesWithTypes(double lat, double lng, double radius, int maxPrice) {
		return acceptedTypes
				.stream().map( (t) -> getPlacesOfType(lat, lng, t, radius, maxPrice))
				.collect(Collectors.toList());
	}
	
	public List<MyPlace> getPlaces(double lat, double lng, double radius, int maxPrice) {
		return acceptedTypes
				.stream()
				.map( (t) -> getPlacesOfType(lat, lng, t, radius, maxPrice).getPlaces())
				.flatMap(List::stream)
				.distinct()
				.sorted( (p1, p2) -> Double.compare(p2.getRating(), p1.getRating()))
				.collect(Collectors.toList());
	}
	
	public MyPlace getPlaceDetails(String placeId) {
		Param[] extraParams = new Param[1];
		extraParams[0] = Param.name("language").value("el");
		Place place = googlePlaces.getPlaceById(placeId, extraParams);
		return MyPlace.convert(place);
	}
	
	public String getPlacePhoto(String placeId) throws IOException {
		String path = getBasePhotoDirectory() + File.separator + placeId + ".jpg";
		File file = new File(path);
		if(file.exists()) { 
		    return path;
		}
		Place place = googlePlaces.getPlaceById(placeId);
		Photo photo = place.getPhotos().get(0);
		RestTemplate restTemplate = new RestTemplate();
		
		URI uri = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/place/photo")
				.queryParam("key", googlePlaces.getApiKey())
				.queryParam("photoreference", photo.getReference())
				.queryParam("maxheight", photo.getHeight())
				.queryParam("maxwidth", photo.getWidth())
				.build().encode().toUri();
		
		byte[] img = restTemplate.getForObject(uri, byte[].class);
		file.createNewFile();
		Files.write(file.toPath(), img);
		return path;
	}
	
	@Bean
	public RestTemplate restTemplate(List<HttpMessageConverter<?>> messageConverters) {
	    return new RestTemplate(messageConverters);
	}

	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
	    return new ByteArrayHttpMessageConverter();
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

	public String getBasePhotoDirectory() {
		return basePhotoDirectory;
	}

	public void setBasePhotoDirectory(String basePhotoDirectory) {
		this.basePhotoDirectory = basePhotoDirectory;
	}

}
