package com.places.api.web.resource;

import java.io.Serializable;

/**
 * @author dimpol
 *
 */
public class PhotoResponse implements Serializable {

	private static final long serialVersionUID = 5263473624849246943L;

	private String filename;
	
	public PhotoResponse(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		return filename;
	}

	public void setPath(String filename) {
		this.filename = filename;
	}
	
}
