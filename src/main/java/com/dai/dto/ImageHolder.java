package com.dai.dto;

import java.io.InputStream;

public class ImageHolder {

	private String imageName;
	private InputStream image;
	public ImageHolder(String imageName, InputStream image) {
		super();
		this.imageName = imageName;
		this.image = image;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public InputStream getImage() {
		return image;
	}
	public void setImage(InputStream image) {
		this.image = image;
	}
	
	
}
