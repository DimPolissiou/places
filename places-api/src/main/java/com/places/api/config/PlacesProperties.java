package com.places.api.config;

import java.util.Optional;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author dimpol
 *
 */
@Validated
@ConfigurationProperties("google.places-api")
public class PlacesProperties {

	@NotEmpty
	private String key;
	
	private Optional<Double> radius;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Optional<Double> getRadius() {
		return radius;
	}

	public void setRadius(Optional<Double> radius) {
		this.radius = radius;
	}
}
