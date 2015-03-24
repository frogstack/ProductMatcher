package com.frogstackventures.codingchallenge.data;

public class Listing {
	private String title;
	private String manufacturer;
    private String currency;
	private String price;
	
	public Listing(){}
	
	public Listing(String manufacturer, String title) {
		this.manufacturer = manufacturer;
		this.title = title;
	}
	
	public Listing(String manufacturer, String title, String currency, String price) {
		this.manufacturer = manufacturer;
		this.title = title;
		this.currency = currency;
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}

	public String getManufacturer() {
		return manufacturer;
	}
	
}
