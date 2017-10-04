package com.places.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import se.walkercrou.places.AltId;
import se.walkercrou.places.Hours;
import se.walkercrou.places.Place;
import se.walkercrou.places.Price;
import se.walkercrou.places.Review;
import se.walkercrou.places.Status;

/**
 * @author dimpol
 *
 */
public class MyPlace implements Serializable {

	private static final long serialVersionUID = 255931508910464965L;

	private List<String> types = new ArrayList<>();
	private List<Review> reviews = new ArrayList<>();
	private List<AltId> altIds = new ArrayList<>();
	
    private String placeId;
    private double lat = -1, lng = -1;
    private String name;
    private String addr;
    private String vicinity;
    private double rating = -1;
    private Status status = Status.NONE;
    private Price price = Price.NONE;
    private String phone, internationalPhone;
    private String googleUrl, website;
    private int utcOffset;
    private int accuracy;
    private String lang;
    
    private String iconUrl;
    private Hours hours;
    
    private String iconType;
    
    public MyPlace() {
    	
    }
    
    public static MyPlace convert(Place place) {
    	MyPlace myPlace = new MyPlace();
    	myPlace.setAccuracy(place.getAccuracy());
    	myPlace.setAddr(place.getAddress());
    	myPlace.setAltIds(place.getAltIds());
    	myPlace.setGoogleUrl(place.getGoogleUrl());
    	myPlace.setHours(place.getHours());
    	myPlace.setIconUrl(place.getIconUrl());
    	myPlace.setInternationalPhone(place.getInternationalPhoneNumber());
    	myPlace.setLang(place.getLanguage());
    	myPlace.setLat(place.getLatitude());
    	myPlace.setLng(place.getLongitude());
    	myPlace.setName(place.getName());
    	myPlace.setPhone(place.getPhoneNumber());
    	myPlace.setPlaceId(place.getPlaceId());
    	myPlace.setPrice(place.getPrice());
    	myPlace.setRating(place.getRating());
    	myPlace.setReviews(place.getReviews());
    	myPlace.setStatus(place.getStatus());
    	myPlace.setTypes(place.getTypes());
    	myPlace.setUtcOffset(place.getUtcOffset());
    	myPlace.setVicinity(place.getVicinity());
    	myPlace.setWebsite(place.getWebsite());
    	return myPlace;
    }

	/**
	 * @return the types
	 */
	public List<String> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(List<String> types) {
		this.types = types;
	}

	/**
	 * @return the placeId
	 */
	public String getPlaceId() {
		return placeId;
	}

	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return the vicinity
	 */
	public String getVicinity() {
		return vicinity;
	}

	/**
	 * @param vicinity the vicinity to set
	 */
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the price
	 */
	public Price getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Price price) {
		this.price = price;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the internationalPhone
	 */
	public String getInternationalPhone() {
		return internationalPhone;
	}

	/**
	 * @param internationalPhone the internationalPhone to set
	 */
	public void setInternationalPhone(String internationalPhone) {
		this.internationalPhone = internationalPhone;
	}

	/**
	 * @return the googleUrl
	 */
	public String getGoogleUrl() {
		return googleUrl;
	}

	/**
	 * @param googleUrl the googleUrl to set
	 */
	public void setGoogleUrl(String googleUrl) {
		this.googleUrl = googleUrl;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return the utcOffset
	 */
	public int getUtcOffset() {
		return utcOffset;
	}

	/**
	 * @param utcOffset the utcOffset to set
	 */
	public void setUtcOffset(int utcOffset) {
		this.utcOffset = utcOffset;
	}

	/**
	 * @return the accuracy
	 */
	public int getAccuracy() {
		return accuracy;
	}

	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * @param iconUrl the iconUrl to set
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/**
	 * @return the hours
	 */
	public Hours getHours() {
		return hours;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(Hours hours) {
		this.hours = hours;
	}

	/**
	 * @return the reviews
	 */
	public List<Review> getReviews() {
		return reviews;
	}

	/**
	 * @return the altIds
	 */
	public List<AltId> getAltIds() {
		return altIds;
	}

	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * @param altIds the altIds to set
	 */
	public void setAltIds(List<AltId> altIds) {
		this.altIds = altIds;
	}

	public String getIconType() {
		if (iconType == null)
			iconType = getIconUrl().substring(50).split("-",0)[0];
		return iconType;
	}

	public void setIconType(String primaryType) {
		this.iconType = primaryType;
	}
}
