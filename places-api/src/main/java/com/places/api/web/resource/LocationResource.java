package com.places.api.web.resource;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * @author dimpol
 *
 */
public class LocationResource implements Serializable {

	private static final long serialVersionUID = 2511187658149263713L;

	@NotNull
	private double lat;

	@NotNull
	private double lng;
	
	private double radius=0;
	
	private int maxPrice=4;
	
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
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	
}
