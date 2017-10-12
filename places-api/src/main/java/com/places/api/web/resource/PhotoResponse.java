package com.places.api.web.resource;

import java.io.Serializable;

/**
 * @author dimpol
 *
 */
public class PhotoResponse implements Serializable {

	private static final long serialVersionUID = 5263473624849246943L;

	private String path;
	
	public PhotoResponse(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
