package com.places.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dimpol
 *
 */
public class PlacesOfType implements Serializable{
	
	private static final long serialVersionUID = -5218805131577256597L;

	private String type;
	
	private List<MyPlace> places = new ArrayList<MyPlace>();
	
	public PlacesOfType() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<MyPlace> getPlaces() {
		return places;
	}

	public void setPlaces(List<MyPlace> places) {
		this.places = places;
	}

}
